package com.adl.interns.medifinder.repository;

import com.adl.interns.medifinder.entity.RequestProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RequestProductRepository extends JpaRepository<RequestProduct, Long> {
    List<RequestProduct> findAllByDrugNameAndNotificationIsSent(String drugName,boolean notificationISSent);
}
