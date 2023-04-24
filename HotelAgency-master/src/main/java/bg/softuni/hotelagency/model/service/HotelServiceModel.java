package bg.softuni.hotelagency.model.service;

import bg.softuni.hotelagency.model.entity.Picture;
import bg.softuni.hotelagency.model.entity.User;
import bg.softuni.hotelagency.model.entity.enums.StarEnum;


public class HotelServiceModel {
    private Long id;
    private String name;
    private String address;
    private StarEnum stars;
    private String email;
    private String description;
    private String videoUrl;
    private User owner;

    public HotelServiceModel() {
    }

    public Long getId() {
        return id;
    }

    public HotelServiceModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public HotelServiceModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getName() {
        return name;
    }

    public HotelServiceModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public HotelServiceModel setAddress(String address) {
        this.address = address;
        return this;
    }

    public StarEnum getStars() {
        return stars;
    }

    public HotelServiceModel setStars(StarEnum stars) {
        this.stars = stars;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public HotelServiceModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public User getOwner() {
        return owner;
    }

    public HotelServiceModel setOwner(User owner) {
        this.owner = owner;
        return this;
    }
}
