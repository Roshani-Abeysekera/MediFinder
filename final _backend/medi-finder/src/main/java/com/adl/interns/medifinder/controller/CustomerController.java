package com.adl.interns.medifinder.controller;

import com.adl.interns.medifinder.entity.RequestProduct;
import com.adl.interns.medifinder.entity.User;
import com.adl.interns.medifinder.repository.CustomerEmailTokenRepository;
import com.adl.interns.medifinder.repository.UserRepository;
import com.adl.interns.medifinder.service.EmailVerificationService;
import com.adl.interns.medifinder.service.SearchService;
import com.adl.interns.medifinder.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/user")
public class CustomerController {

    private Logger log= LoggerFactory.getLogger(CustomerController.class);

    private UserRepository userRepository;

    private CustomerEmailTokenRepository customerEmailTokenRepository;

    private EmailVerificationService emailVerificationService;

    private UserService userService;

    private SearchService searchService;

    @Autowired
    public CustomerController(UserRepository userRepository
            , CustomerEmailTokenRepository customerEmailTokenRepository
            , EmailVerificationService emailVerificationService, UserService userService
            , SearchService searchService) {
        this.userRepository = userRepository;
        this.customerEmailTokenRepository = customerEmailTokenRepository;
        this.emailVerificationService = emailVerificationService;
        this.userService = userService;
        this.searchService=searchService;
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/helo")
    public Principal helo(Principal principal){
        return principal;
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/home")
    public String custLogin(){
        return "hello world";
    }

    @PostMapping("/registration")
    public String customerRegistration(@RequestBody User user){
        log.info("customer registration endpoint");
        return userService.userRegisration(user);
    }

    @RequestMapping(value = "/confirm", method= {RequestMethod.GET, RequestMethod.POST})
    public String verifyCustomer(@RequestParam("token")String confirmationToken){
        log.info("customer email verification endpoint");
        return userService.verifyUser(confirmationToken);
    }

    @PostMapping("/notification")
    public String gettingMailNotification(Principal principal,@RequestParam String requestProduct
            , @RequestParam double latitude, @RequestParam double longitude){
        String email=principal.getName();
        searchService.requestMailNotification(email, requestProduct, latitude, longitude);
        return "SUCCESS";

    }
}
