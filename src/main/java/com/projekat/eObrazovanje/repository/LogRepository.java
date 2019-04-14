package com.projekat.eObrazovanje.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projekat.eObrazovanje.model.Log;

@Repository
public interface LogRepository extends JpaRepository<Log, Integer> {

}
