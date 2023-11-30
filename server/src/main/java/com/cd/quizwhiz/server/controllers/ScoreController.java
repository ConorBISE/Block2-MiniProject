package com.cd.quizwhiz.server.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cd.quizwhiz.server.NewScoreRequest;
import com.cd.quizwhiz.server.auth.User;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = "*")
public class ScoreController {
    @PostMapping("/score/new")
    public String newScore(HttpServletRequest request, @RequestBody NewScoreRequest newScoreRequest) {
        User user = new User((String) request.getAttribute("username"));
        user.finalScore(newScoreRequest.score);
        return "{}";
    }
}
