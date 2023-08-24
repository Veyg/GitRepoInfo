package com.rekrutacja.gitapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/github")
public class GitHubController {

    @Autowired
    private GitHubService gitHubService;

    @GetMapping("/repositories/{username}")
    public ResponseEntity<?> getRepositories(@PathVariable String username, 
                                             @RequestHeader("Accept") String acceptHeader) {
        if("application/xml".equals(acceptHeader)) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            return new ResponseEntity<>(Map.of("status", 406, "Message", "XML format is not supported"), headers, HttpStatus.NOT_ACCEPTABLE);
        }
        
        try {
            List<RepoInfo> repositories = gitHubService.getRepositories(username);
            return ResponseEntity.ok(repositories);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("status", 404, "Message", "User not exists"));
        }
    }
    
}
