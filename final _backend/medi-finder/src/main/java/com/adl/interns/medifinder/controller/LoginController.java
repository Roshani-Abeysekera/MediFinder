package com.adl.interns.medifinder.controller;

import com.adl.interns.medifinder.entity.User;
import com.adl.interns.medifinder.entity.Role;
import com.adl.interns.medifinder.entity.RoleType;
import com.adl.interns.medifinder.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

@RestController
public class LoginController {


    private User user;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/getUserRole")
    public String login(Principal principal){
        System.out.println(principal.getName());
        user = userRepository.findByEmail(principal.getName());
        Set<Role> r=new HashSet<>();
        r= user.getRoles();
        System.out.println(r.getClass().getCanonicalName());
        for (Role role:r) {
            System.out.println("get role" + role.getRole());
            if ((role.getRole())== RoleType.USER) {
                return "USER";
            } else {
                return "AGENT";
            }
        }

        return  "not found role";
    }
}
