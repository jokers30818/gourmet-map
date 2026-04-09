package com.example.gourmet.repository;

import com.example.gourmet.model.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ShopRepository extends JpaRepository<Shop, Long> {
    
    // Custom queries for filtering
    List<Shop> findByCategory(String category);
    
    List<Shop> findByBudget(String budget);
    
    @Query("SELECT s FROM Shop s WHERE " +
           "(:category IS NULL OR :category = '' OR s.category = :category) AND " +
           "(:area IS NULL OR :area = '' OR s.area = :area) AND " +
           "(:rating IS NULL OR s.rating >= :rating) AND " +
           "(:budget IS NULL OR :budget = '' OR s.budget = :budget) AND " +
           "(:tags IS NULL OR :tags = '' OR s.tags LIKE %:tags% OR s.name LIKE %:tags%)")
    List<Shop> searchShops(@Param("category") String category, 
                           @Param("area") String area, 
                           @Param("rating") Double rating, 
                           @Param("budget") String budget,
                           @Param("tags") String tags,
                           org.springframework.data.domain.Sort sort);
}
