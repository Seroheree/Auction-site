package com.example.demo.models;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Entity
@Table(name="auctions")
public class Auction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;

    @Column(nullable = false)
        private String title;

    @NotBlank(message = "description cant be null")
    @Column(nullable = false)
        private String description;

    @Column(nullable = false)
        private String photos;

        private int price;
        private LocalDate time;

    @ManyToOne
    @JoinColumn(name="user_id")
        private User user;

    public Auction(){}

    public Auction(String title, String description, int price, User user){
        this.title=title;
        this.description=description;
        this.price=price;
        this.user=user;
    }
    @PrePersist
    void setAuctionStartTime(){
        setTime(LocalDate.now());
    }

    @Transient
        public String getPhotosImagePath(){
        if(photos==null){
            return null;
        }
        return "/auction-photos/" + id + "/" + photos;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public User getUser() {
        return user;
    }

    public void setTime(LocalDate time) {
        this.time = time;
    }

    public LocalDate getTime() {
        return time;
    }
}
