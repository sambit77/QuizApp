package com.example.quizapp.controller;

import com.example.quizapp.model.Question;
import com.example.quizapp.model.QuestionWrapper;
import com.example.quizapp.model.Response;
import com.example.quizapp.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quiz")
public class QuizController {

    @Autowired
    QuizService quizService;

    //http://localhost:8080/quiz/create?category=java&numQ=1&title=javaquiz2

    @PostMapping("/create")
    public ResponseEntity<String> createQuiz(
            @RequestParam  String category,
            @RequestParam  int numQ,
            @RequestParam  String title
    )
    {
        return quizService.createQuiz(category,numQ,title);
    }

    //http://localhost:8080/quiz/get/4
    @GetMapping("/get/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable Integer id)
    {
        return quizService.getQuizQuestions(id);
    }

    @PostMapping("/submit/{id}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable Integer id, @RequestBody List<Response> response)
    {
        return quizService.calculateResult(id,response);
    }

}
