package com.example.quizapp.service;

import com.example.quizapp.Question;
import com.example.quizapp.dao.QuestionsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class QuestionService {

    @Autowired
    QuestionsDao questionsDao;
    public List<Question> getAllQuestions() {
        return questionsDao.findAll();
    }

    public List<Question> getQuestionsByCategory(String category) {
        return questionsDao.findByCategory(category);
    }

    public String addQuestion(Question question) {
        questionsDao.save(question);
        return "success";
    }
}
