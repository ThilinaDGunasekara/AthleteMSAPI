package com.vitalhub.AthleteMS.dao;

import com.vitalhub.AthleteMS.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventDAO extends JpaRepository<Event,String> {
}
