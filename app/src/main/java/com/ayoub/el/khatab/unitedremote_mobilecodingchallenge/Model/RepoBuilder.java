package com.ayoub.el.khatab.unitedremote_mobilecodingchallenge.Model;

public class RepoBuilder {
    private int id;
    private String name;
    private String description;
    private int stars;
    private String ownerName;
    private String ownerAvatar;

    public RepoBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public RepoBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public RepoBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public RepoBuilder setStars(int stars) {
        this.stars = stars;
        return this;
    }

    public RepoBuilder setOwnerName(String ownerName) {
        this.ownerName = ownerName;
        return this;
    }

    public RepoBuilder setOwnerAvatar(String ownerAvatar) {
        this.ownerAvatar = ownerAvatar;
        return this;
    }

    public Repo buildRepo() {
        return new Repo(id, name, description, stars, ownerName, ownerAvatar);
    }
}