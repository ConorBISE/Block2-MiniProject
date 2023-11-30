package com.cd.quizwhiz.server;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTCreationException;

import com.cd.quizwhiz.server.auth.Auth;
import com.cd.quizwhiz.server.auth.KeyManagement;
import com.cd.quizwhiz.server.auth.User;
import com.cd.quizwhiz.server.stats.Leaderboard;

import jakarta.servlet.http.HttpServletRequest;

@SpringBootApplication
@RestController
@CrossOrigin(origins = "*")
public class ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRegisterRequest loginRequest) {
        Map<String, Object> ret = new HashMap<>();
        ret.put("message", null);
        ret.put("token", null);

        if (Auth.login(loginRequest.username, loginRequest.password)) {
            // Yay! All good!
            try {
                Map<String, String> payload = new HashMap<>();
                payload.put("name", loginRequest.username);

                String token = JWT.create()
                        .withIssuer("quizwhiz")
                        .withPayload(payload)
                        .sign(KeyManagement.getAlgorithm());

                ret.put("token", token);

                return ResponseEntity.status(200).body(ret);

            } catch (JWTCreationException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ret);
            }
        }

        ret.put("message", "Invalid username or password.");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ret);
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody LoginRegisterRequest registerRequest) {
        Map<String, Object> ret = new HashMap<>();
        ret.put("message", null);
        ret.put("token", null);

        String registrationMessage = Auth.register(registerRequest.username, registerRequest.password);
        if (registrationMessage.equals(registerRequest.username)) {
            // Yay! All good!
            try {
                Map<String, String> payload = new HashMap<>();
                payload.put("name", registerRequest.username);

                String token = JWT.create()
                        .withIssuer("quizwhiz")
                        .withPayload(payload)
                        .sign(KeyManagement.getAlgorithm());

                ret.put("message", registrationMessage);
                ret.put("token", token);

                return ResponseEntity.status(200).body(ret);

            } catch (JWTCreationException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ret);
            }
        }

        ret.put("message", registrationMessage);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ret);
    }

    @PostMapping("/score/new")
    public String newScore(HttpServletRequest request, @RequestBody NewScoreRequest newScoreRequest) {
        User user = new User((String) request.getAttribute("username"));
        user.finalScore(newScoreRequest.score);
        return "{}";
    }

    @GetMapping("/stats")
    public Map<String, Object> stats(HttpServletRequest request) {
        Map<String, Object> ret = new HashMap<>();
        
        User user = new User((String) request.getAttribute("username"));
        ret.put("mean", user.getMean());
        ret.put("median", user.getMedian());
        ret.put("deviation", user.getDeviation());
        
        return ret;
    }

    @GetMapping("/leaderboard")
    public Map<String, Object> leaderboard() throws IOException {
        Map<String, Object> ret = new HashMap<>();
        ret.put("leaderboard", Leaderboard.getLeaderboard());
        return ret;
    }
}
