package br.com.j38.springdataredis.repository;

import br.com.j38.springdataredis.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, String> {
    List<Vehicle> findByStore(String store);
}

