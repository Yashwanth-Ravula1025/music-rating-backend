package com.example.userservice.repository;

import com.example.userservice.model.Music;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MusicRepository extends JpaRepository<Music,Long> {
    Music findByMusicName(String name);

    List<Music> findAllByArtistName(String artistName);
}
