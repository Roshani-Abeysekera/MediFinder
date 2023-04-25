package com.adl.interns.medifinder.service;

import com.adl.interns.medifinder.entity.RequestProduct;
import com.adl.interns.medifinder.entity.User;
import com.adl.interns.medifinder.entity.Drug;
import com.adl.interns.medifinder.entity.SearchCount;
import com.adl.interns.medifinder.repository.RequestProductRepository;
import com.adl.interns.medifinder.repository.UserRepository;
import com.adl.interns.medifinder.repository.DrugRepository;
import com.adl.interns.medifinder.repository.SearchCountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.*;



@Service
public class SearchService {

    private Logger log=LoggerFactory.getLogger(SearchService.class);

    public SearchCountRepository searchCountRepository;


    public DrugRepository drugRepository;


    public UserRepository userRepository;

    public RequestProductRepository requestProductRepository;

    @Autowired
    public SearchService(SearchCountRepository searchCountRepository,
                         DrugRepository drugRepository, UserRepository userRepository,
                         RequestProductRepository requestProductRepository) {
        this.searchCountRepository = searchCountRepository;
        this.drugRepository = drugRepository;
        this.userRepository = userRepository;
        this.requestProductRepository=requestProductRepository;
    }


    public void increaseSearchCount(String name) {

        SearchCount searchCount = new SearchCount();

        if (searchCountRepository.existsByName(name)) {
            searchCount = searchCountRepository.getByName(name);
            searchCount.setCount(searchCount.getCount() + 1);
        } else {
            searchCount.setName(name);
            searchCount.setCount(1);

        }
        searchCountRepository.save(searchCount);
       /* catch(NullPointerException e) {
            System.out.println("NullPointerException thrown!");
        }


        //System.out.println(searchCount.toString());

       /* if (searchCount == null) {
            searchCount.setName(name);
            searchCount.setCount(1);
        }

        else{
            searchCount.setCount(searchCount.getCount()+1);
        }*/


    }


    public List<User> getlocations(String name, double latitude, double longitude) {

        List<User> userResults = new ArrayList<>();     //To pass the selected drug objects to the controller

        List<Drug> drugResults = new ArrayList<>();

        drugResults=(drugRepository.findAllByDrugNameAndAvailability(name, Boolean.TRUE));


        if (drugRepository.existsAllByGeneticNameAndAvailability(name, Boolean.TRUE)) {

            drugResults=(drugRepository.findAllByGeneticNameAndAvailability(name, Boolean.TRUE));
        } else {
        }

        /*System.out.println(drugResults);
        drugResults.forEach(drug -> {
            System.out.println(drug.getDrugName());
        });*/
        if (userResults == null || drugResults == null || drugResults.isEmpty() || drugResults.size() < 1) {
            System.out.println("results null");
            return userResults;
        } else {
            List<User> finalUserResults = userResults;
            System.out.println("Test");
            //drugResults.forEach(Drug -> userResults.add(Drug.getUser()));

            for (Drug drug : drugResults) {
                if (drug != null) {
                    double lat1 = drug.getUser().getLatitude();
                    double lon1 = drug.getUser().getLongitude();
                    System.out.println(lat1);
                    System.out.println(lon1);
                    System.out.println(latitude);
                    System.out.println(longitude);
                    double distanceValue = distance(lat1, lon1, latitude, longitude);
                    System.out.println("ditance"+distanceValue);
                    if (distanceValue < 10) {
                        userResults.add(drug.getUser());
                    } else {
                    }
                    System.out.println("nuuuuuul");
                }
            }

            return userResults;
        }

    }
    public double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        dist = dist * 1.609344;
        return dist;
    }
    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }
    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    public void requestMailNotification(String email,String product, double latitude, double longitude){
        RequestProduct requestProduct = new RequestProduct();

        User user=userRepository.findByEmail(email);
        log.info("user : {}", user);
        requestProduct.setEmail(email);
        requestProduct.setDrugName(product);
        requestProduct.setLatitude(latitude);
        requestProduct.setLongitude(longitude);
        log.info("save request product : {}",requestProduct);
        requestProductRepository.save(requestProduct);
    }
}
