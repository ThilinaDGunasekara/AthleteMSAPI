package com.vitalhub.AthleteMS.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Blob;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ATHLETE")
public class Athlete {
    @Id
    @Column(name = "athlete_id")
    private String athleteId;
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Column(name = "LAST_NAME")
    private String lastName;
    @Column(name = "GENDER")
    private String gender;
    @Column(name = "DATE_OF_BIRTH")
    private Date dateOfBirth;
    @Column(name = "COUNTRY")
    private String country;
    @Column(name = "PROFILE_IMAGE")
    private Blob profileImage;

    @OneToMany(mappedBy = "athlete",cascade = {CascadeType.PERSIST,CascadeType.DETACH,CascadeType.MERGE,CascadeType.REMOVE,CascadeType.REFRESH})
    private List<Event> eventsToParticipate = new ArrayList<>();


    public Athlete(String athleteId, String firstName, String lastName, String gender, Date dateOfBirth, String country, Blob profileImage) {
        this.athleteId = athleteId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.country = country;
        this.profileImage = profileImage;
    }
}
