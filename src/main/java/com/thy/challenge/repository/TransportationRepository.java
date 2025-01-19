package com.thy.challenge.repository;

import com.thy.challenge.entity.Location;
import com.thy.challenge.entity.Transportation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransportationRepository extends JpaRepository<Transportation, Long> {
    List<Transportation> findByOriginAndDestination(Location origin, Location destination);
}

