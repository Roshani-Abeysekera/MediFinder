package com.adl.interns.medifinder.controller;

import com.adl.interns.medifinder.entity.User;
import com.adl.interns.medifinder.service.AgentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/agent")
public class AgentController {

    private Logger log= LoggerFactory.getLogger(CustomerController.class);

    private AgentService agentService;

    @Autowired
    public AgentController(AgentService agentService){
        this.agentService=agentService;
    }

    @PreAuthorize("hasRole('AGENT')")
    @GetMapping("/helo")
    public String helo(){
        return "hello agent";
    }

    @PostMapping("/registration")
    public String agentRegistration(@RequestBody User user){
        log.info("agent registration endpoint");
        return agentService.agentRegisration(user);
    }

    @RequestMapping(value = "/confirm", method= {RequestMethod.GET, RequestMethod.POST})
    public String verifyCustomer(@RequestParam("token")String confirmationToken) {
        log.info("agent mail verification endpoint");
        return agentService.verifyAgent(confirmationToken);
    }

}
