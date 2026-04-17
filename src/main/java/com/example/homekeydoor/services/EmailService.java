package com.example.homekeydoor.services;

import com.example.homekeydoor.entities.User;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.*;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceAsync;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceAsyncClientBuilder;
import com.amazonaws.services.simpleemail.model.*;

@Service
public class EmailService {
//
//
//    @Value("${aws.key}")
//    private String key;
//
//    @Value("${aws.secret}")
//    private String secret;
//
//    @Value("${aws.ses.returnPath}")
//    private String returnPath;
//
//    @Value("#{'${aws.ses.replyToAddressessses}'.split(',')}")
//    private List<String> replyAddresses;
//
//    @Value("${aws.ses.source}")
//    private String source;
//
//    @Value("${user.account.creation.uri}")
//    private String userAccountCreationUri;
//
//    @Value("${user.password.change.uri}")
//    private String userPasswordChangeUri;
//
//    @Value("${user.password.change.sign.up}")
//    private String userPasswordChangeUriSignUp;
//
//    private AmazonSimpleEmailServiceAsync ses;
//
//
//    public void sendUserPasswordChange(User userEntity) {
//        String email = userEntity.getEmail();
//
//        String name = userEntity.getFirstName();
//        if (name == null) {
//            name = "User";
//        }
//
//        String html = fileReader("email/user_password_change.html");
//        html = html.replace("%s1", name).replace("%s2", userPasswordChangeUri + userEntity.getEmail() + "/" + userEntity.getKey());
//
//        List<String> emails = new ArrayList<>();
//        emails.add(email);
//        sendEmail(source, emails, "Reset Password", html, true, false);
//    }
//
//    public void sendStaffAccountCreation(User userEntity) {
//        String email = userEntity.getEmail();
//
//        String name = userEntity.getFirstName();
//        if (name == null) {
//            name = "User";
//        }
//
//        String html = fileReader("email/user_password_change_sign_up.html");
//        html = html.replace("%s1", name).replace("%s2", userPasswordChangeUriSignUp + userEntity.getEmail() + "/" + userEntity.getKey());
//
//
//        List<String> emails = new ArrayList<>();
//        emails.add(email);
//        sendEmail(source, emails, "Account Creation Request", html, true, false);
//    }
//
//    public void sendCompanySuspension(User userEntity) {
//        String email = userEntity.getEmail();
//
//        String name = userEntity.getFirstName();
//        if (name == null) {
//            name = "User";
//        }
//
//        String html = fileReader("email/company_suspension.html");
//        html = html.replace("%s1", name);
//
//        List<String> emails = new ArrayList<>();
//        emails.add(email);
//        sendEmail(source, emails, "Company is suspended", html, true, false);
//    }
//
//    public void sendCompanyReactivation(User userEntity) {
//        String email = userEntity.getEmail();
//
//        String name = userEntity.getFirstName();
//        if (name == null) {
//            name = "User";
//        }
//
//        String html = fileReader("email/company_reactivation.html");
//        html = html.replace("%s1", name);
//
//        List<String> emails = new ArrayList<>();
//        emails.add(email);
//        sendEmail(source, emails, "Company is reactivated", html, true, false);
//    }
//
//    private void sendEmail(String source, List<String> toAddresses, String subject,
//                           String content, boolean isHtml, boolean async) throws AmazonClientException {
//        SendEmailRequest request = new SendEmailRequest();
//        Destination destination = new Destination(toAddresses);
//
//        Body body = new Body();
//        if (isHtml) {
//            body.setHtml(new Content(content));
//        } else {
//            body.setText(new Content(content));
//        }
//        request.setSource(source);
//        request.setReturnPath(returnPath);
//        request.setReplyToAddresses(replyAddresses);
//        request.setDestination(destination);
//        request.setMessage(new Message(new Content(subject), body));
//        if (async) {
//            ses().sendEmailAsync(request);
//        } else {
//            ses().sendEmail(request);
//        }
//    }
//
//    private AmazonSimpleEmailServiceAsync ses() {
//        if (ses == null) {
//            if (key.equals("IAM")) {
//                InstanceProfileCredentialsProvider provider = new InstanceProfileCredentialsProvider(false);
//                ses = AmazonSimpleEmailServiceAsyncClientBuilder.standard().withRegion(Regions.US_WEST_2).withCredentials(provider).build();
//            } else {
//
//                AWSCredentials credentials = new BasicAWSCredentials(this.key, this.secret);
//                AWSCredentialsProvider provider = new AWSStaticCredentialsProvider(credentials);
//
//                ses = AmazonSimpleEmailServiceAsyncClientBuilder.standard().withRegion(Regions.US_WEST_2).withCredentials(provider).build();
//
//            }
//        }
//
//        return ses;
//    }
//
//    private String fileReader(String filePath) {
//        StringBuilder contentBuilder = new StringBuilder();
//        try {
//            InputStream loggerStream = EmailService.class.getClassLoader().getResourceAsStream(filePath);
//            BufferedReader in = new BufferedReader(new InputStreamReader(loggerStream, "UTF-8"));
//            String str;
//            while ((str = in.readLine()) != null) {
//                contentBuilder.append(str);
//            }
//            in.close();
//        } catch (IOException e) {
//        }
//        String content = contentBuilder.toString();
//        return content;
//    }
}
