package com.second_hand_trading_platform.model;

import java.time.LocalDateTime;

public class Item {
    private int id;
    private String title;
    private String description;
    private String category;
    private String status;
    private double price;
    private String location;
    private LocalDateTime foundDate;
    private LocalDateTime postDate;
    private int userId;

    // Constructors
    public Item() {}

    public Item(String title, String description, String category,
                double price, String location, int userId) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.status = "待交易";
        this.price = price;
        this.location = location;
        this.foundDate = LocalDateTime.now();
        this.postDate = LocalDateTime.now();
        this.userId = userId;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public LocalDateTime getFoundDate() { return foundDate; }
    public void setFoundDate(LocalDateTime foundDate) { this.foundDate = foundDate; }

    public LocalDateTime getPostDate() { return postDate; }
    public void setPostDate(LocalDateTime postDate) { this.postDate = postDate; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", category='" + category + '\'' +
                ", status='" + status + '\'' +
                ", price=" + price +
                ", location='" + location + '\'' +
                ", userId=" + userId +
                '}';
    }
}