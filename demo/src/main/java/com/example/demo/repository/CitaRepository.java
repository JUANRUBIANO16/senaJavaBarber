package com.example.demo.repository;

import com.example.demo.entities.Cita;
import com.example.demo.entities.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Integer> {
}

