package com.cd.quizwhiz.server.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cd.quizwhiz.server.stats.Leaderboard;

@RestController
@CrossOrigin(origins = "*")
public class LeaderboardController {
    @GetMapping("/leaderboard")
    public Map<String, Object> leaderboard() throws IOException {
        Map<String, Object> ret = new HashMap<>();
        ret.put("leaderboard", Leaderboard.getLeaderboard());
        return ret;
    }
}
