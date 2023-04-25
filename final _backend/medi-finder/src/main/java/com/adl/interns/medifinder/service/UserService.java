package com.adl.interns.medifinder.service;

import com.adl.interns.medifinder.entity.User;
import com.adl.interns.medifinder.entity.UserMailToken;
import com.adl.interns.medifinder.repository.CustomerEmailTokenRepository;
import com.adl.interns.medifinder.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;

    private CustomerEmailTokenRepository customerEmailTokenRepository;

    private EmailVerificationService emailVerificationService;

    @Autowired
    public UserService(CustomerEmailTokenRepository customerEmailTokenRepository
            , UserRepository userRepository, EmailVerificationService emailVerificationService){
        this.customerEmailTokenRepository=customerEmailTokenRepository;
        this.userRepository = userRepository;
        this.emailVerificationService=emailVerificationService;
    }


    public String userRegisration(User user)  {


        System.out.println(user.toString());
        User user1 = userRepository.findByEmail(user.getEmail());

        if (user1 != null)
            return "user exists";
        else {

            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

            String psswrd = bCryptPasswordEncoder.encode(user.getPassword());
            user.setPassword(psswrd);

            userRepository.save(user);

            UserMailToken userMailToken = new UserMailToken(user);
            customerEmailTokenRepository.save(userMailToken);

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(user.getEmail());
            mailMessage.setSubject("Complete Registration!");
            mailMessage.setFrom("medifinder2021@gmail.com");
            mailMessage.setText("To confirm your account, please click here : "
                    + "http://localhost:8080/user/confirm?token=" + userMailToken.getToken());

            emailVerificationService.sendEmail(mailMessage);
            return "Successful";
        }
    }

    public String verifyUser(String confirmationToken){
        UserMailToken userMailToken =customerEmailTokenRepository.findByToken(confirmationToken);

        if(userMailToken !=null){
            User user = userRepository.findByEmail(userMailToken.getCustomer().getEmail());
            user.setVerified(true);
            userRepository.save(user);
            return "verified";
        }

        return "verified fail";
    }

    public User findCustomerByEmail(String email){
        return userRepository.findByEmail(email);

    }
}
