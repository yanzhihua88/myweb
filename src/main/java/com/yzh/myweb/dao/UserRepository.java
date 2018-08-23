package com.yzh.myweb.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.yzh.myweb.entity.User2;

public interface UserRepository extends JpaRepository<User2, Long> {

    User2 findByName(String name);

    User2 findByNameAndAge(String name, Integer age);

    @Query("from User u where u.name=:name")
    User2 findUser(@Param("name") String name);

}