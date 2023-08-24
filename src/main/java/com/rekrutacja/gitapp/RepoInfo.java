package com.rekrutacja.gitapp;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.IOException;
import java.util.List;

public class RepoInfo {
    private String name; 

    @JsonSerialize(using = OwnerSerializer.class)
    private Owner owner;

    private List<BranchInfo> branches; 
    private boolean fork;

    public RepoInfo() {}

    public RepoInfo(String name, Owner owner, boolean fork, List<BranchInfo> branches) {
        this.name = name;
        this.owner = owner;
        this.fork = fork;
        this.branches = branches;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public List<BranchInfo> getBranches() {
        return branches;
    }

    public void setBranches(List<BranchInfo> branches) {
        this.branches = branches;
    }

    public boolean isFork() {
        return fork;
    }

    public void setFork(boolean fork) {
        this.fork = fork;
    }

    public static class Owner {
        private String login;

        public Owner() {}

        public Owner(String login) {
            this.login = login;
        }

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }
    }

    public static class OwnerSerializer extends JsonSerializer<Owner> {
        @Override
        public void serialize(Owner value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeString(value.getLogin());
        }
    }
}
