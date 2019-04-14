package com.projekat.eObrazovanje.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projekat.eObrazovanje.model.QuestionAnswerPair;
import com.projekat.eObrazovanje.model.QuestionAnswerPairKey;

@Repository
public interface QuestionAnswerPairRepository extends JpaRepository<QuestionAnswerPair, QuestionAnswerPairKey> {

}
