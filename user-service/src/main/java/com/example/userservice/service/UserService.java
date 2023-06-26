package com.example.userservice.service;

import com.example.userservice.dto.RatingDto;
import com.example.userservice.exception.MusicNotFoundException;
import com.example.userservice.model.Music;
import com.example.userservice.model.Rating;
import com.example.userservice.repository.MusicRepository;
import com.example.userservice.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class UserService {

    @Autowired
    MusicRepository musicRepository;
    @Autowired
    RatingRepository ratingRepository;


    public List<Music> fetchAllMusic() throws MusicNotFoundException {
        List<Music> music = musicRepository.findAll();
        if (music.isEmpty()) {
            throw new MusicNotFoundException("No music found");
        }
        return music;
    }


    public Music fetchMusicByName(String name) throws MusicNotFoundException {
        Music music = musicRepository.findByMusicName(name);
        if(music !=null) {
            return music;
        } else {
            throw new MusicNotFoundException("Music with name " + name + " not found");
        }
    }

    public List<Music> fetchMusicByArtistName(String artistName) throws MusicNotFoundException {
        List<Music> musics = musicRepository.findAllByArtistName(artistName);
        if (musics.isEmpty()) {
            throw new MusicNotFoundException("No musics found with the release artistName: " + artistName);
        }
        return musics;
    }


    public boolean addMusicRating(RatingDto ratingDto, Long musicId, String userName) {
        Optional<Music> op = musicRepository.findById(musicId);

        if(op.isPresent()) {

            Optional<List<Rating>> op2 =  ratingRepository.findByUserName(userName);
            List<Rating> ratingObjList = op2.get();
            int flag=0;
            for(Rating obj:ratingObjList) {
                if(obj.getMusicId()==musicId) {
                    obj.setRating(ratingDto.getRating());
                   // obj.setMessage(ratingDto.getMessage());
                    flag=1;
                    ratingRepository.save(obj);
                }
            }

            if(flag==0) {
                Rating ratingObj = new Rating();
                ratingObj.setMusicId(musicId);
                ratingObj.setUserName(userName);
                ratingObj.setRating(ratingDto.getRating());
               // ratingObj.setMessage(ratingDto.getMessage());
                ratingRepository.save(ratingObj);
            }

        } else {
            return false;
        }

        Music music = op.get();
        List<Rating> list = ratingRepository.findAllByMusicId(musicId);
        AtomicReference<Double> overallRate = new AtomicReference<>(0.0);
        list.forEach((e) -> overallRate.updateAndGet(currentRate -> currentRate + e.getRating()));
        overallRate.set(overallRate.get() / list.size());
        music.setOverallRate(overallRate.get());
        musicRepository.save(music);
        return true;
    }

}



