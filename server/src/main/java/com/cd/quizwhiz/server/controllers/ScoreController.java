package com.cd.quizwhiz.server.controllers;

import java.io.IOException;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cd.quizwhiz.server.NewScoreRequest;
import com.cd.quizwhiz.server.auth.User;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = "*")
public class ScoreController {
    @PostMapping("/score/new")
    public String newScore(HttpServletRequest request, @RequestBody NewScoreRequest newScoreRequest) throws StreamReadException, DatabindException, IOException {
        User user = User.readUserFromFile((String) request.getAttribute("username"));
        user.appendFinalScore(newScoreRequest.score);
        user.save();
        return "{}";
    }
}
