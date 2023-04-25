package com.adl.interns.medifinder.repository;


import com.adl.interns.medifinder.entity.SearchCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchCountRepository extends JpaRepository<SearchCount,Long> {

    SearchCount getByName(String name);
    SearchCount findByName(String name);
    Boolean existsByName(String name);



}
