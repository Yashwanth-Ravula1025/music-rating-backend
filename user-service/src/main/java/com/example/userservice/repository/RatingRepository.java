package com.example.userservice.repository;

import com.example.userservice.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating,Long> {

    List<Rating> findAllByMusicId(Long musicId);


    Optional<List<Rating>> findByUserName(String userName);
}
