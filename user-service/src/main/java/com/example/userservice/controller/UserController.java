package com.example.userservice.controller;

import com.example.userservice.dto.RatingDto;
import com.example.userservice.exception.InvalidRatingException;

import com.example.userservice.exception.MusicNotFoundException;
import com.example.userservice.model.Music;
import com.example.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api/users")
public class UserController {


    @Autowired
    private UserService userService;


    @GetMapping("/viewAllMusics")
    public ResponseEntity<List<Music>> getAllMusics() throws MusicNotFoundException {
        return ResponseEntity.ok().body(userService.fetchAllMusic());
    }


    @GetMapping("/search/music/name/{name}")
    public ResponseEntity<Music> getMusicByName(@PathVariable String name) throws MusicNotFoundException {
        return ResponseEntity.ok().body(userService.fetchMusicByName(name));

    }


    @GetMapping("/search/music/artistName/{artistName}")
    public ResponseEntity<List<Music>> getMusicByArtistName(@PathVariable String artistName) throws MusicNotFoundException {
        return ResponseEntity.ok().body(userService.fetchMusicByArtistName(artistName));
    }


    @PostMapping("add/rating/music/{musicId}/{userName}")
    public ResponseEntity<String> addRatingMusic(@Valid @RequestBody RatingDto ratingdto, @PathVariable Long musicId, @PathVariable String userName) throws InvalidRatingException, MusicNotFoundException {
        if (ratingdto.getRating() < 1 || ratingdto.getRating() > 5) {
            throw new InvalidRatingException("Invalid rating. Please provide a rating between 1 and 5");
        }

        boolean ratingAdded = userService.addMusicRating(ratingdto, musicId, userName);

        if (ratingAdded) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Rating added successfully.");
        } else {
            throw new MusicNotFoundException("Failed to add rating, Music not found");
        }

    }

}
