package com.vitalhub.AthleteMS.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ATH_EVENT")
    public class Event {
    @Id
    @Column(name = "EVENT_ID")
    private String eventId;
    @Column(name = "EVENT_NAME")
    private String eventName;
    @JoinColumn(name = "athleteId",referencedColumnName = "athlete_id",nullable = false)
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.DETACH,CascadeType.MERGE,CascadeType.REMOVE,CascadeType.REFRESH})
    private Athlete athlete;
    @OneToMany(mappedBy = "event",cascade = CascadeType.PERSIST)
    private List<EventDetails> eventDetails=new ArrayList<>();


    public Event(String eventId, String eventName, Athlete athlete) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.athlete = athlete;
    }
}
