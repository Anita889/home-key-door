package com.example.homekeydoor.controllers;


import com.example.homekeydoor.dataservices.UserDataService;
import com.example.homekeydoor.dtos.AuthDTO;
import com.example.homekeydoor.dtos.ValueDTO;
import com.example.homekeydoor.entities.User;
import com.example.homekeydoor.exceptions.RestConflictException;
import com.example.homekeydoor.mappers.UserMapper;
import com.example.homekeydoor.repositories.RoleRepository;
import com.example.homekeydoor.consts.RegistrationType;
import com.example.homekeydoor.security.SecureUser;
import com.example.homekeydoor.security.TokenUser;
import com.example.homekeydoor.security.TokenUtils;
import com.example.homekeydoor.services.EmailService;
import org.springframework.security.core.userdetails.UserDetails;
import com.example.homekeydoor.consts.UserType;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user/authentication")
public class UserRestAuthController extends BaseUserAuthController {

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Autowired
    private UserDataService userDataService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserMapper userMapper;

//    @Value("${userauth.changepassword.expiration}")
//    protected long tempKeyExpiration;

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody AuthDTO authDTO) throws AuthenticationException {
        String email = authDTO.getEmail();
        String password = authDTO.getPassword();

        User userEntity = userDataService.findByEmailAndRegistrationType(email, RegistrationType.REGISTERED);
        if(userEntity == null){
            throw new EntityNotFoundException("Email or password was entered incorrect, please try again");
        }

        if(!passwordEncoder.matches(password, userEntity.getPassword())){
            throw new RestConflictException("Email or password was entered incorrect, please try again");
        }

        if(userEntity.isLocked()){
            throw new RestConflictException("Account is locked, please contact with your organisation admin");
        }

        return login(userEntity);
    }

    @RequestMapping(value = "password/change", method = RequestMethod.GET)
    public ResponseEntity passwordChange(@RequestParam String email) {
        // URL Encoder replaces '+' with space
        email = email.replace(" ", "+");
        //
        User userEntity = userDataService.findByEmailAndRegistrationType(email, RegistrationType.REGISTERED);
        if(userEntity == null) {
            throw new EntityNotFoundException("User with that email address does not exist");
        }
        String key = UUID.randomUUID().toString();
        userEntity.setKey(key);
        //userEntity.setTempKeyExpireDatetime(LocalDateTime.now().plusSeconds(tempKeyExpiration));

        userEntity = userDataService.save(userEntity);
       // emailService.sendUserPasswordChange(userEntity);
        return ResponseEntity.ok(new ValueDTO<>(true));
    }

    @RequestMapping(value = "password/change/{email}/{key}", method = RequestMethod.PUT)
    public ResponseEntity passwordChange(@PathVariable String email, @PathVariable String key, @RequestBody AuthDTO authDTO) {
        User userEntity = userDataService.findByKey(key);
        if(userEntity == null || !userEntity.getEmail().equals(email) || userEntity.getTempKeyExpireDatetime() == null || userEntity.getTempKeyExpireDatetime().isBefore(LocalDateTime.now()) ) {
            throw new EntityNotFoundException("The activation key is not found or expired");
        }

        userEntity.setPassword(passwordEncoder.encode(authDTO.getPassword()));
        userEntity.setRegistrationType(RegistrationType.REGISTERED);
        userDataService.save(userEntity);
        return ResponseEntity.ok(new ValueDTO<>(true));
    }

    @RequestMapping(value = "password/check/{email}/{key}", method = RequestMethod.GET)
    public ResponseEntity checkKey(@PathVariable String email, @PathVariable String key) {
        User userEntity = userDataService.findByKey(key);
        if(userEntity == null || !userEntity.getEmail().equals(email) || userEntity.getTempKeyExpireDatetime() == null || userEntity.getTempKeyExpireDatetime().isBefore(LocalDateTime.now()) ) {
            throw new EntityNotFoundException("The activation key is not found or expired");
        }

        return ResponseEntity.ok(new ValueDTO<>(true));
    }

    @RequestMapping(value = "refreshToken", method = RequestMethod.GET)
    public ResponseEntity<?> refreshTokenRequest(@RequestHeader(value="REFRESH-TOKEN") String refreshTokenHeader) throws AuthenticationException {
        TokenUser tokenUser = tokenUtils.getTokenUser(refreshTokenHeader, refreshTokenSecret);
        if(tokenUser != null){
            String username = tokenUser.getUsername();
            UserType userType = tokenUser.getUserType();
            if( (userType == UserType.HOME_USER && tokenUser.validToken(UserType.HOME_USER)) ||
                    (userType == UserType.HOME_OWNER && tokenUser.validToken(UserType.HOME_OWNER)) ||
                    (userType == UserType.ADMIN && tokenUser.validToken(UserType.ADMIN))){
                User userEntity = userDataService.findByEmailAndRegistrationType(username, RegistrationType.REGISTERED);
                if(userEntity == null){
                    throw new EntityNotFoundException("User with that email address does not exist");
                }
                UserDetails userDetails = new SecureUser(userEntity, null);
                String accessToken = TokenUtils.generateToken(userDetails, userType, accessTokenSecret, accessTokenExpiration);
                AuthDTO authDTO = new AuthDTO();
                authDTO.setAccessToken(accessToken);
                return ResponseEntity.ok(authDTO);
            }

        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Not Authorized");
    }
}

