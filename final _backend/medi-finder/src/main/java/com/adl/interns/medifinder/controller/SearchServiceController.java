package com.adl.interns.medifinder.controller;

import com.adl.interns.medifinder.entity.User;
import com.adl.interns.medifinder.service.SearchService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController

public class SearchServiceController {

        public Logger log = LoggerFactory.getLogger(SearchServiceController.class);

    private SearchService searchService;

    @Autowired
    public SearchServiceController(SearchService searchService) {
        this.searchService = searchService;
    }


//    @GetMapping ("/searchCounts/{name}")
//    public List<User> useSearchServices(@PathVariable String name){
//        log.info("get method called"+name);
//
//        List<User> userResults= new ArrayList<>();
//        searchService.increaseSearchCount(name);
//        userResults=searchService.getlocations(name);
//
//        return userResults;
//
//    }

    @GetMapping ("/searchcounts/{name}/{latitude}/{longitude}")
    public List<User> useSearchServices(@PathVariable String name,@PathVariable double latitude, @PathVariable double longitude){

        List<User> userResults= new ArrayList<>();
        searchService.increaseSearchCount(name);
        userResults= searchService.getlocations(name,latitude,longitude);

        return userResults;

    }
}
