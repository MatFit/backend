package com.aftermath.backend.repository;

import com.aftermath.backend.model.QuestionChoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionChoiceRepository extends JpaRepository<QuestionChoiceEntity, Long> {
    List<QuestionChoiceEntity> findByQuestionId(Long questionId);
}