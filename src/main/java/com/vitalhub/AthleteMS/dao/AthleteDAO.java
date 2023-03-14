package com.vitalhub.AthleteMS.dao;

import com.vitalhub.AthleteMS.entity.Athlete;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AthleteDAO extends JpaRepository<Athlete,String> {
}
