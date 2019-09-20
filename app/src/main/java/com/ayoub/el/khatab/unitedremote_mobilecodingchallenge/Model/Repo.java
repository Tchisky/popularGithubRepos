package com.ayoub.el.khatab.unitedremote_mobilecodingchallenge.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "repository")
public class Repo {

    @PrimaryKey()
    private int id;

    private String name;

    private String description;

    private int stars;

    private String ownerName;

    private String ownerAvatar;

    public Repo(int id,
                String name,
                String description,
                int stars,
                String ownerName,
                String ownerAvatar) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.ownerName = ownerName;
        this.stars = stars;
        this.ownerAvatar = ownerAvatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public int getStars() {
        return stars;
    }

    public String getOwnerAvatar() {
        return ownerAvatar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
