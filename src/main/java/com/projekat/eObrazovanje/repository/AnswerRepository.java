package com.projekat.eObrazovanje.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projekat.eObrazovanje.model.Answer;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Integer> {

}
