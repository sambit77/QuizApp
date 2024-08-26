package com.example.quizapp.service;

import com.example.quizapp.dao.QuestionsDao;
import com.example.quizapp.dao.QuizDao;
import com.example.quizapp.model.Question;
import com.example.quizapp.model.QuestionWrapper;
import com.example.quizapp.model.Quiz;
import com.example.quizapp.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    QuizDao quizDao;

    @Autowired
    QuestionsDao questionsDao;

    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {

        List<Question> questions = questionsDao.findRandomQuestionsByCategory(category.toLowerCase(),numQ);
        System.out.println("Questions from db" + questions.toString());
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);
        quizDao.save(quiz);

        return new ResponseEntity<>("success", HttpStatus.CREATED);

    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {

        Optional<Quiz> quiz = quizDao.findById(id);

        List<Question> quesFromDB = quiz.get().getQuestions();
        List<QuestionWrapper> quesForUsers = new ArrayList<>();
        for(Question q : quesFromDB)
        {
            QuestionWrapper qw = new QuestionWrapper(q.getId(),q.getQuestionTitle(),q.getOption1(),q.getOption2(),q.getOption3(),q.getOption4());
            quesForUsers.add(qw);
        }
        return  new ResponseEntity<>(quesForUsers,HttpStatus.OK);
    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {

        Quiz quiz = quizDao.findById(id).get();

        List<Question> questions = quiz.getQuestions();

        int i = 0;
        int right = 0;
        for(Response response : responses)
        {
            if(response.getResponse().equalsIgnoreCase(questions.get(i).getRightAnswer()))
            {
                right++;
            }
            i++;
        }

        return new ResponseEntity<>(right,HttpStatus.OK);
    }
}
