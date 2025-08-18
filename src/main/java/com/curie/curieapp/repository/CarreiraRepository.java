package com.curie.curieapp.repository;

import com.curie.curieapp.entities.Carreira;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarreiraRepository extends JpaRepository<Carreira, Long> {
}
