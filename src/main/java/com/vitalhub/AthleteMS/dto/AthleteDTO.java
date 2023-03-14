package com.vitalhub.AthleteMS.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Blob;
import java.sql.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AthleteDTO {
    private String athleteId;
    private String firstName;
    private String lastName;
    private String gender;
    private Date dateOfBirth;
    private String country;
    private List<EventDTO> events;
    private byte[] profileImage;
    //private MultipartFile imageFile;


//    public AthleteDTO(String athleteId, String firstName, String lastName, String gender, Date dateOfBirth, String country, byte[] profileImage) {
//        this.athleteId = athleteId;
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.gender = gender;
//        this.dateOfBirth = dateOfBirth;
//        this.country = country;
//        this.profileImage = profileImage;
//    }
}
