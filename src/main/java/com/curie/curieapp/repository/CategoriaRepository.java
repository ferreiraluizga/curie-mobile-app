package com.curie.curieapp.repository;

import com.curie.curieapp.entities.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long>  {
}
