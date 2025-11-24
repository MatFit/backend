package com.aftermath.backend.service;

import com.aftermath.backend.model.MarketEntity;
import com.aftermath.backend.model.QuizEntity;
import com.aftermath.backend.model.User;
import com.aftermath.backend.service.serviceInterface.QuizServiceImpl;
import org.springframework.stereotype.Service;

// Quiz generator service that'll work with quiz generation functionality
@Service
public class QuizService implements QuizServiceImpl {
    public QuizService () {

    }
    public QuizEntity generateQuiz (User user, MarketEntity market){
        // It'll call on fastapi service to return appropriate QuizJSON
        // Quiz, questions, and question choices will be stored persistently when we need to fetch
        // past data for the user (like when plotting performance)
        // Some call this excessive abstraction i call this under the hood
        return null;
    }
}
