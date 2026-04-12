package com.example.homekeydoor.controllers;

import com.example.homekeydoor.dataservices.RoleDataService;
import com.example.homekeydoor.dtos.AuthDTO;
import com.example.homekeydoor.entities.User;
import com.example.homekeydoor.dtos.UserDTO;
import com.example.homekeydoor.entities.Role;
import com.example.homekeydoor.exceotions.RestUnauthorizedAccessException;
import com.example.homekeydoor.mappers.UserMapper;
import com.example.homekeydoor.security.RoleType;
import com.example.homekeydoor.security.SecureUser;
import com.example.homekeydoor.security.TokenUtils;
import com.example.homekeydoor.security.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseUserAuthController { //extends BaseAuthController {

    @Value("${security.token.header}")
    protected String accessTokenHeader;

    @Value("${security.token.secret}")
    protected String accessTokenSecret;

    @Value("${security.token.expiration}")
    protected long accessTokenExpiration;

    @Value("${security.refreshtoken.header}")
    protected String refreshTokenHeader;

    @Value("${security.refreshtoken.secret}")
    protected String refreshTokenSecret;

    @Value("${security.refreshtoken.expiration}")
    protected long refreshTokenExpiration;

    @Autowired
    protected UserMapper userMapper;

    @Autowired
    protected TokenUtils tokenUtils;

    @Autowired
    protected RoleDataService roleDataService;

    protected ResponseEntity<?> login(User userEntity){
        UserDetails userDetails = new SecureUser(userEntity, null);
        Role roleAdmin = roleDataService.findByName(RoleType.ROLE_ADMIN);
        Role roleHomeOwner = roleDataService.findByName(RoleType.ROLE_HOME_OWNER);
        Role roleHomeUser = roleDataService.findByName(RoleType.ROLE_HOME_USER);

        UserType userType = null;
        if(userEntity.getRoles().contains(roleAdmin)){
            userType = UserType.ADMIN;
        }
        else if (userEntity.getRoles().contains(roleHomeOwner)){
            userType = UserType.HOME_OWNER;
        }
        else if (userEntity.getRoles().contains(roleHomeUser)) {
            userType = UserType.HOME_USER;
        }
        else{
            throw new RestUnauthorizedAccessException();
        }

        String accessToken = TokenUtils.generateToken(userDetails, userType, accessTokenSecret, accessTokenExpiration);
        String refreshToken = TokenUtils.generateToken(userDetails, userType, refreshTokenSecret, refreshTokenExpiration);

        UserDTO userDTO = userMapper.userDTOFromUserEntity(userEntity);

        AuthDTO authDTO = new AuthDTO();
        authDTO.setUser(userDTO);
        authDTO.setAccessToken(accessToken);
        authDTO.setRefreshToken(refreshToken);

        return ResponseEntity.ok(authDTO);
    }
}
