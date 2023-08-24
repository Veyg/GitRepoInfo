package com.rekrutacja.gitapp;

import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GitHubService {

    private final String GITHUB_API_URL = "https://api.github.com";

    public List<RepoInfo> getRepositories(String username) throws UserNotFoundException {
        RestTemplate restTemplate = new RestTemplate();
        String url = GITHUB_API_URL + "/users/" + username + "/repos";
        
        try {
            ResponseEntity<RepoInfo[]> response = restTemplate.getForEntity(url, RepoInfo[].class);
            
            List<RepoInfo> repositories = Arrays.stream(response.getBody())
            .filter(repo -> !repo.isFork())
            .map(repo -> {
                List<BranchInfo> branches = getBranchesForRepo(username, repo.getName());
                return new RepoInfo(repo.getName(), repo.getOwner(), repo.isFork(), branches);
            })
            .collect(Collectors.toList());
            
        
            return repositories;
        } catch (HttpClientErrorException.NotFound e) {
            throw new UserNotFoundException("User not exists");
        }
    }

    private List<BranchInfo> getBranchesForRepo(String username, String repoName) {
        RestTemplate restTemplate = new RestTemplate();
        String branchesUrl = GITHUB_API_URL + "/repos/" + username + "/" + repoName + "/branches";
        
        ResponseEntity<BranchInfo[]> branchesResponse = restTemplate.getForEntity(branchesUrl, BranchInfo[].class);
        List<BranchInfo> branches = Arrays.asList(branchesResponse.getBody());
    
        for (BranchInfo branch : branches) {
            String branchDetailsUrl = GITHUB_API_URL + "/repos/" + username + "/" + repoName + "/branches/" + branch.getName();
            ResponseEntity<BranchDetails> branchDetailsResponse = restTemplate.getForEntity(branchDetailsUrl, BranchDetails.class);
            branch.setLastCommitSha(branchDetailsResponse.getBody().getCommit().getSha());
        }
    
        return branches;
    }
}
