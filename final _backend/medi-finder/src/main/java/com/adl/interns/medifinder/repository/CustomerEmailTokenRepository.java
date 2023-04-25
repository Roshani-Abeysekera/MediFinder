package com.adl.interns.medifinder.repository;

import com.adl.interns.medifinder.entity.UserMailToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerEmailTokenRepository extends JpaRepository<UserMailToken, String> {

    UserMailToken findByToken(String token);
}
