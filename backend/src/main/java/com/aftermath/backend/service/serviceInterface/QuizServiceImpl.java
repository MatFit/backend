package com.aftermath.backend.service.serviceInterface;

import com.aftermath.backend.model.MarketEntity;
import com.aftermath.backend.model.QuizEntity;
import com.aftermath.backend.model.User;

public interface QuizServiceImpl {
    public QuizEntity generateQuiz(User user, MarketEntity market);
    //    fetchUserMarketInterests(UUID userID, Market market);

}
