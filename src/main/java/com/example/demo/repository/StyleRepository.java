package com.example.demo.repository;

import com.example.demo.model.entity.Style;
import com.example.demo.model.entity.enums.StyleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StyleRepository extends JpaRepository<Style, String> {
    Optional<Style> findByStyleName(StyleName name);
}
