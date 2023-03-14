# AthleteMSAPI

Please run the following queries before starting.

create database ATHLETE_DB;
commit;

use ATHLETE_DB;
CREATE TABLE ATHLETE (
    ATHLETE_ID varchar(20) NOT NULL,
    FIRST_NAME varchar(100) NOT NULL,
    LAST_NAME varchar(100) NOT NULL,
    GENDER varchar(20) NOT NULL,
    DATE_OF_BIRTH date NOT NULL,
    COUNTRY varchar(200) NOT NULL,
    PROFILE_IMAGE blob NOT NULL,
    PRIMARY KEY (`ATHLETE_ID`)
); 
commit;


use ATHLETE_DB;
CREATE TABLE ATH_EVENT (
    EVENT_ID varchar(20) NOT NULL,
    EVENT_NAME varchar(100) NOT NULL,
    ATHLETE_ID varchar(20) NOT NULL,
    PRIMARY KEY (`EVENT_ID`),
    CONSTRAINT FOREIGN KEY (`ATHLETE_ID`) REFERENCES `ATHLETE` (`ATHLETE_ID`)
);


use ATHLETE_DB;
CREATE TABLE event_detail (
  EVENT_ID varchar(20)  not null,
  ATHLETE_ID varchar(20)  not null,
  PRIMARY KEY (EVENT_ID,ATHLETE_ID),
  CONSTRAINT FOREIGN KEY (EVENT_ID) REFERENCES ATH_EVENT (EVENT_ID),
  CONSTRAINT FOREIGN KEY (ATHLETE_ID) REFERENCES ATHLETE (ATHLETE_ID)
) ; 
commit;


 
