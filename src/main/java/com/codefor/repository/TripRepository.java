package com.codefor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codefor.entity.Trip;
import com.codefor.entity.User;

public interface TripRepository extends JpaRepository<Trip, Long> {

	
    List<Trip> findByUser(User user);

}
