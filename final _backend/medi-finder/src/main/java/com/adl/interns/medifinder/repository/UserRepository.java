package com.adl.interns.medifinder.repository;

import com.adl.interns.medifinder.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface UserRepository extends JpaRepository<User,Long> {

    User findByEmail(String email);

    User findByUserId(long id);

}
