package com.example.userservice.model;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "RATING_TABLE")
@Entity
public class Rating {

    @Id
    @GeneratedValue
    private long ratingId;

    private String userName;

    private long musicId;

    private double rating;

    // private String message;

}

