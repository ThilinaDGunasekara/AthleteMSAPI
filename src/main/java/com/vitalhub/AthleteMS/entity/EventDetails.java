package com.vitalhub.AthleteMS.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "event_detail")
public class EventDetails {
    @EmbeddedId
    private EventDetailsPK eventDetailsPK;

    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.DETACH,CascadeType.MERGE,CascadeType.REMOVE,CascadeType.REFRESH})
    @JoinColumn(name = "eventId",referencedColumnName = "event_id",updatable = false,insertable = false)
    private Event event;

    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.DETACH,CascadeType.MERGE,CascadeType.REMOVE,CascadeType.REFRESH})
    @JoinColumn(name = "athleteId",referencedColumnName = "athlete_id",updatable = false,insertable = false)
    private Athlete athlete;
}
