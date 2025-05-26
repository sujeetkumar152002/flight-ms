package com.UserAUTH.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.UserAUTH.Entities.UserCredential;

import java.util.Optional;


public interface UserCredentialRepository  extends JpaRepository<UserCredential,Integer> {
    Optional<UserCredential> findByName(String username);
}