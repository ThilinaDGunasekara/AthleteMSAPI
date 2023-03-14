package com.vitalhub.AthleteMS.business;

import com.vitalhub.AthleteMS.dto.AthleteDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AthleteBO {
    List<AthleteDTO> getAllAthlete() throws Exception;
    //List<AthleteDTO> getAthleteByCustomData(String Name,String gender,String country,String event) throws Exception;

    boolean saveAthlete(AthleteDTO athlete) throws Exception;
}
