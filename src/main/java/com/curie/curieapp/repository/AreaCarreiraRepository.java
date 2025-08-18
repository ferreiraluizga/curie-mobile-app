package com.curie.curieapp.repository;

import com.curie.curieapp.entities.AreaCarreira;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AreaCarreiraRepository extends JpaRepository<AreaCarreira, Long> {
}
