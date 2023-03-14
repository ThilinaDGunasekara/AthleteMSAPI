package com.vitalhub.AthleteMS.controller;

import com.vitalhub.AthleteMS.business.AthleteBO;
import com.vitalhub.AthleteMS.dto.AthleteDTO;
import com.vitalhub.AthleteMS.dto.ResponseDTO;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@Slf4j
@RequestMapping("api/v1/athlete")
public class AthleteController {

    private AthleteBO athleteBO;

    @Autowired
    public void setAthleteBO(AthleteBO athleteBO) {
        this.athleteBO = athleteBO;
    }

    @GetMapping(value = "/getAllAthlete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getAllAthlete() {
        ResponseDTO response = new ResponseDTO();
        try {
            List<AthleteDTO> athleteData = athleteBO.getAllAthlete();
            if (athleteData.size()>0) {
                response.setMessage("SUCCESS");
                response.setStatusCode("0000");
                response.setSuccess(true);
                response.setResult(athleteData);
            } else {
                response.setMessage("No data found.");
                response.setStatusCode("0001");
                response.setSuccess(false);
                response.setResult(new ArrayList<AthleteDTO>());
            }
        } catch (Exception e) {
            log.error("ERROR FOUND IN :---->>>> api/v1/athlete/getAllAthlete ::",e);
            response.setMessage("FAIL");
            response.setStatusCode("0001");
            response.setSuccess(false);
            response.setResult(new ArrayList<AthleteDTO>());
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/saveAthlete", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Object> insertToSlipsCustomer(@RequestBody AthleteDTO athleteDTO) throws Exception {


        System.out.println(" athleteDTO :::: " + athleteDTO.toString());

        ResponseDTO response = new ResponseDTO();
        try {
            boolean status = athleteBO.saveAthlete(athleteDTO);
            if (status) {
                response.setMessage("SUCCESS");
                response.setStatusCode("201");
                response.setSuccess(true);
                response.setResult(null);
            } else {
                response.setMessage("false");
                response.setStatusCode("0001");
                response.setSuccess(false);
                response.setResult(null);
            }
        } catch (Exception e) {
            log.error("ERROR FOUND IN :---->>>> api/v1/athlete/saveAthlete ::",e);
            response.setMessage("false");
            response.setStatusCode("5001");
            response.setSuccess(false);
            response.setResult(null);
        }
        return ResponseEntity.ok(response);
    }
}
