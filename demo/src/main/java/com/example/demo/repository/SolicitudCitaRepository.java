package com.example.demo.repository;

import com.example.demo.entities.SolicitudCita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolicitudCitaRepository extends JpaRepository<SolicitudCita, Integer> {

}