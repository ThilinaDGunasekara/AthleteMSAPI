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
                athleteDTOList.add(new AthleteDTO(ath.getAthleteId(),ath.getFirstName(),ath.getLastName(),ath.getGender(),ath.getDateOfBirth(),ath.getCountry(),eDto,blobToByte(ath.getProfileImage())));
            }
            return athleteDTOList;
        }catch (Exception e){
            throw e;
        }
    }

//    @Override
//    public List<AthleteDTO> getAthleteByCustomData(String Name, String gender, String country, String event) throws Exception {
//        List<AthleteDTO> athleteDTOList = new ArrayList<>();
//        try {
//            List<Athlete> allAthlete = athleteDAO.findAll();
//            for (Athlete ath:allAthlete) {
//                athleteDTOList.add(new AthleteDTO(ath.getAthleteId(),ath.getFirstName(),ath.getLastName(),ath.getGender(),ath.getDateOfBirth(),ath.getCountry(),ath.getProfileImage()));
//            }
//            return athleteDTOList;
//        }catch (Exception e){
//            throw e;
//        }
//    }

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



    public static String getAthleteFilterType(String Name, String gender, String country, String event){
        return null;
    }

    public static byte[] blobToByte(Blob image) throws Exception {
        try {
            int blobLength = (int) image.length();
            byte[] blobAsBytes = image.getBytes(1, blobLength);
            return decompressBytes(blobAsBytes);
        }catch (Exception e){
            throw e;
        }
    }

    public static byte[] decompressBytes(byte[] data) throws IOException, DataFormatException {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
            return outputStream.toByteArray();
        } catch (IOException ioe) {
            throw ioe;
        } catch (DataFormatException e) {
            throw e;
        }
    }

    public static byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
        }
        System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);

        return outputStream.toByteArray();
    }
}
