package com.space.repository;

import com.space.model.Ship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.math.BigInteger;

public interface ShipRepository extends JpaRepository<Ship, Long>, JpaSpecificationExecutor<Ship> {
}
