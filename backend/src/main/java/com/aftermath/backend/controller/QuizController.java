package com.aftermath.backend.controller;

import com.aftermath.backend.dto.ApiResponseDTO;
import com.aftermath.backend.dto.QuizRequest;
import com.aftermath.backend.model.QuizEntity;
import com.aftermath.backend.service.QuizService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

// Will expose endpoints and act as a middleware to work with quiz service
@Controller
public class QuizController {
    private QuizService quizService;

    @PostMapping("/generate")
    public ResponseEntity<ApiResponseDTO<QuizEntity>> generateQuiz(@RequestBody QuizRequest request) {
        try {
            QuizEntity quiz = quizService.generateQuiz(null, null);
            return ApiResponseDTO.success(quiz).toResponseEntity();
        } catch (Exception e) {
            return ApiResponseDTO.<QuizEntity>error("Failed to generate quiz", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR).toResponseEntity();
        }
    }
}
