package com.adl.interns.medifinder.repository;


import com.adl.interns.medifinder.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.adl.interns.medifinder.entity.Drug;

import java.util.List;

@Repository
public interface DrugRepository extends JpaRepository<Drug, Long>{

    List<Drug> findAllByUser(User user);
    Drug findByDrugNameAndGeneticNameAndUser(String name, String geneticName,User user);

    List<Drug> findAllByDrugNameAndAvailability(String name,Boolean availability);
    Boolean existsAllByGeneticNameAndAvailability(String name,Boolean availability);
    List<Drug> findAllByGeneticNameAndAvailability(String name,Boolean availability);


    Drug findByDrugName(String name);

    @Query(value="SELECT DISTINCT brand_name FROM drugs  WHERE genetic_name = ( SELECT genetic_name FROM drugs WHERE drugs.brand_name LIKE ?1% FETCH FIRST ROW ONLY ) AND NOT brand_name LIKE ?1% GROUP BY brand_name"
            ,nativeQuery = true)        
    public List<Object []> search(String keyword);



}
