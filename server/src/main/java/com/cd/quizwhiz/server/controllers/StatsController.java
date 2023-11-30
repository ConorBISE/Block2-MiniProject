package com.cd.quizwhiz.server.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cd.quizwhiz.server.auth.User;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = "*")
public class StatsController {
    @GetMapping("/stats")
    public Map<String, Object> stats(HttpServletRequest request) throws StreamReadException, DatabindException, IOException {
        Map<String, Object> ret = new HashMap<>();

        User user = User.readUserFromFile((String) request.getAttribute("username"));
        boolean hasStats = user.returnScores().length != 0;

        ret.put("hasStats", hasStats);

        if (hasStats) {   
            ret.put("mean", user.getMean());
            ret.put("median", user.getMedian());
            ret.put("deviation", user.getDeviation());
        }
        
        return ret;
    }
}
