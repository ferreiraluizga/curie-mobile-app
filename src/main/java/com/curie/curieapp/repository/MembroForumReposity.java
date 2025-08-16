package com.curie.curieapp.repository;

import com.curie.curieapp.entities.MembroForum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MembroForumReposity extends JpaRepository<MembroForum, Long> {
}
