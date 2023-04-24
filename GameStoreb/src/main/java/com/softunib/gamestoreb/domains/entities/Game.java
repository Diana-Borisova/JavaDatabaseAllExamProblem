package com.softunib.gamestoreb.domains.entities;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Entity;
import java.math.BigDecimal;
import java.time.LocalDate;


@Table(name = "games ")
@Entity
public class Game extends BaseEntity {
    @Column(nullable = false)
    private String title;
    @Column(name = "trailer_id")
    private String trailerId;
    @Column(name = "url_Image")
    private String urlImage;
    @Column
    private float size;
    @Column(nullable = false)
    private BigDecimal price;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private LocalDate releaseDate;




    public Game(String title, String trailerId,
                String urlImage, float size,
                BigDecimal price, String description, LocalDate releaseDate) {
        this.title = title;
        this.trailerId = trailerId;

        this.urlImage = urlImage;
        this.size = size;
        this.price = price;
        this.description = description;
        this.releaseDate = releaseDate;
    }

    public Game() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTrailerId() {
        return trailerId;
    }

    public void setTrailerId(String trailerId) {
        this.trailerId = trailerId;
    }

    public String getImageThumbnail() {
        return urlImage;
    }

    public void setImageThumbnail(String imageThumbnail) {
        this.urlImage = imageThumbnail;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public BigDecimal getPrize() {
        return price;
    }

    public void setPrize(BigDecimal prize) {
        this.price = prize;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }
}
