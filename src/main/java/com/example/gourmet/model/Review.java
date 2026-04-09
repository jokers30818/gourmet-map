package com.example.gourmet.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id", nullable = false)
    private Shop shop;

    @NotBlank(message = "投稿者名は必須です")
    @Size(max = 50, message = "投稿者名は50文字以内で入力してください")
    private String posterName;

    @NotBlank(message = "コメントは必須です")
    @Size(max = 500, message = "コメントは500文字以内で入力してください")
    private String comment;

    private LocalDateTime createdAt = LocalDateTime.now();

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Shop getShop() { return shop; }
    public void setShop(Shop shop) { this.shop = shop; }

    public String getPosterName() { return posterName; }
    public void setPosterName(String posterName) { this.posterName = posterName; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
