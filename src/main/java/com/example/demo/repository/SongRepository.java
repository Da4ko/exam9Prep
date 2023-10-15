package com.example.demo.repository;

import com.example.demo.model.entity.Song;
import com.example.demo.model.entity.enums.StyleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongRepository extends JpaRepository<Song, String> {
    List<Song> findAllByStyle_StyleName(StyleName styleName);
}
