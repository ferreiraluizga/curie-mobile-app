package com.curie.curieapp.repository;

import com.curie.curieapp.entities.Meta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MetaRepository extends JpaRepository<Meta, Long> {
}
