package com.vitalhub.AthleteMS.business.impl;

import com.vitalhub.AthleteMS.business.AthleteBO;
import com.vitalhub.AthleteMS.dao.AthleteDAO;
import com.vitalhub.AthleteMS.dao.EventDAO;
import com.vitalhub.AthleteMS.dto.AthleteDTO;
import com.vitalhub.AthleteMS.dto.EventDTO;
import com.vitalhub.AthleteMS.entity.Athlete;
import com.vitalhub.AthleteMS.entity.Event;
import jakarta.transaction.Transactional;
import org.hibernate.annotations.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Component
public class AthleteBOImpl implements AthleteBO {

    private AthleteDAO athleteDAO;
    private EventDAO eventDAO;

    @Autowired
    public void setAthleteDAO(AthleteDAO athleteDAO){
        this.athleteDAO = athleteDAO;
    }

    @Autowired
    public void setEventDAO(EventDAO eventDAO){
        this.eventDAO = eventDAO;
    }
    @Override
    public List<AthleteDTO> getAllAthlete() throws Exception {

        List<AthleteDTO> athleteDTOList = new ArrayList<>();
        try {
            List<Athlete> allAthlete = athleteDAO.findAll();

            for (Athlete ath:allAthlete) {
                List<EventDTO> eDto = new ArrayList<>();
                for (Event e:ath.getEventsToParticipate()) {
                    eDto.add(new EventDTO(e.getEventId(),e.getEventName()));
                }
                athleteDTOList.add(new AthleteDTO(ath.getAthleteId(),ath.getFirstName(),ath.getLastName(),ath.getGender(),ath.getDateOfBirth(),ath.getCountry(),eDto,ath.getProfileImage().toString().getBytes()));
            }
            return athleteDTOList;
        }catch (Exception e){
            throw e;
        }
    }

    @Override
    @Transactional
    public boolean saveAthlete(AthleteDTO athlete) throws Exception {
        Athlete saved = null;
        Event savedEvent = null;
        try {

            byte[] decodedByte = Base64.getDecoder().decode(athlete.getProfileImage());

            Blob b = new SerialBlob(decodedByte);

            saved = athleteDAO.save(new Athlete(athlete.getAthleteId(), athlete.getFirstName(), athlete.getLastName(), athlete.getGender(), athlete.getDateOfBirth(), athlete.getCountry(), b));

            List<EventDTO> events = athlete.getEvents();
            List<Event> eventList = new ArrayList<>();

            for (EventDTO e:events) {
                eventList.add(new Event(e.getEventId(),e.getEventName(),new Athlete(athlete.getAthleteId(), athlete.getFirstName(), athlete.getLastName(), athlete.getGender(), athlete.getDateOfBirth(), athlete.getCountry(), b)));
            }
            eventDAO.saveAll(eventList);
            return saved.getAthleteId() != null;
        }catch (Exception e){
            throw e;
        }
    }
}
