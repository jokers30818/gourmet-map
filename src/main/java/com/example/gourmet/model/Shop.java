package com.example.gourmet.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;

@Entity
@Table(name = "shops")
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "店名は必須です")
    @Size(max = 100, message = "店名は100文字以内で入力してください")
    private String name;

    @NotBlank(message = "エリアは必須です")
    private String area;

    @NotBlank(message = "カテゴリは必須です")
    private String category;

    @NotNull(message = "評価は必須です")
    @DecimalMin(value = "1.0", message = "評価は1.0以上で入力してください")
    @DecimalMax(value = "5.0", message = "評価は5.0以下で入力してください")
    private Double rating;

    @NotBlank(message = "予算感は必須です")
    private String budget;

    private String hideawayLevel;

    private String recommendedMenu;

    @NotBlank(message = "コメントは必須です")
    @Size(max = 500, message = "コメントは500文字以内で入力してください")
    private String comment;

    private String imageUrl1;
    private String imageUrl2;
    private String imageUrl3;
    private String imageUrl4;
    private String imageUrl5;

    private Integer reactionCount = 0;
    private Integer wantToGoCount = 0;

    private String posterName;
    private String tags;

    private Double latitude;
    private Double longitude;

    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    private LocalDateTime createdAt = LocalDateTime.now();

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getArea() { return area; }
    public void setArea(String area) { this.area = area; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public Double getRating() { return rating; }
    public void setRating(Double rating) { this.rating = rating; }

    public String getBudget() { return budget; }
    public void setBudget(String budget) { this.budget = budget; }

    public String getHideawayLevel() { return hideawayLevel; }
    public void setHideawayLevel(String hideawayLevel) { this.hideawayLevel = hideawayLevel; }

    public String getRecommendedMenu() { return recommendedMenu; }
    public void setRecommendedMenu(String recommendedMenu) { this.recommendedMenu = recommendedMenu; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public String getImageUrl1() { return imageUrl1; }
    public void setImageUrl1(String imageUrl1) { this.imageUrl1 = imageUrl1; }

    public String getImageUrl2() { return imageUrl2; }
    public void setImageUrl2(String imageUrl2) { this.imageUrl2 = imageUrl2; }

    public String getImageUrl3() { return imageUrl3; }
    public void setImageUrl3(String imageUrl3) { this.imageUrl3 = imageUrl3; }

    public String getImageUrl4() { return imageUrl4; }
    public void setImageUrl4(String imageUrl4) { this.imageUrl4 = imageUrl4; }

    public String getImageUrl5() { return imageUrl5; }
    public void setImageUrl5(String imageUrl5) { this.imageUrl5 = imageUrl5; }

    public Integer getReactionCount() { return reactionCount; }
    public void setReactionCount(Integer reactionCount) { this.reactionCount = reactionCount; }

    public Integer getWantToGoCount() { return wantToGoCount; }
    public void setWantToGoCount(Integer wantToGoCount) { this.wantToGoCount = wantToGoCount; }

    public String getPosterName() { return posterName; }
    public void setPosterName(String posterName) { this.posterName = posterName; }

    public String getTags() { return tags; }
    public void setTags(String tags) { this.tags = tags; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public List<Review> getReviews() { return reviews; }
    public void setReviews(List<Review> reviews) { this.reviews = reviews; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
