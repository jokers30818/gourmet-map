package com.example.gourmet.controller;

import com.example.gourmet.model.Shop;
import com.example.gourmet.model.Review;
import com.example.gourmet.repository.ShopRepository;
import com.example.gourmet.repository.ReviewRepository;
import com.example.gourmet.service.GeocodingService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

@Controller
public class ShopController {

    private final ShopRepository shopRepository;
    private final ReviewRepository reviewRepository;
    private final GeocodingService geocodingService;

    public ShopController(ShopRepository shopRepository, ReviewRepository reviewRepository, GeocodingService geocodingService) {
        this.shopRepository = shopRepository;
        this.reviewRepository = reviewRepository;
        this.geocodingService = geocodingService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/")
    public String list(
            @RequestParam(required = false) String area,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Double rating,
            @RequestParam(required = false) String budget,
            @RequestParam(required = false) String tags,
            @RequestParam(required = false) String sortField,
            Model model) {
        
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        if ("rating".equals(sortField)) {
            sort = Sort.by(Sort.Direction.DESC, "rating");
        } else if ("reactions".equals(sortField)) {
            sort = Sort.by(Sort.Direction.DESC, "reactionCount");
        } else if ("wanttogo".equals(sortField)) {
            sort = Sort.by(Sort.Direction.DESC, "wantToGoCount");
        }

        model.addAttribute("shops", shopRepository.searchShops(category, area, rating, budget, tags, sort));
        
        // Keep search parameters in the form
        model.addAttribute("selectedArea", area);
        model.addAttribute("selectedCategory", category);
        model.addAttribute("selectedRating", rating);
        model.addAttribute("selectedBudget", budget);
        model.addAttribute("selectedTags", tags);
        model.addAttribute("selectedSort", sortField);
        return "list";
    }

    @GetMapping("/form")
    public String showForm(Model model) {
        model.addAttribute("shop", new Shop());
        return "form";
    }

    @PostMapping("/form")
    public String submitForm(@Valid @ModelAttribute Shop shop, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "form";
        }
        
        if (shop.getLatitude() == null || shop.getLongitude() == null) {
            String addressToSearch = shop.getArea() + " " + shop.getName();
            double[] coords = geocodingService.getCoordinates(addressToSearch);
            if (coords != null) {
                shop.setLatitude(coords[0]);
                shop.setLongitude(coords[1]);
            }
        }
        
        shopRepository.save(shop);
        return "redirect:/";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Long id, Model model) {
        Shop shop = shopRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid shop id: " + id));
        model.addAttribute("shop", shop);
        return "detail";
    }

    @PostMapping("/reaction/{id}")
    public String addReaction(@PathVariable Long id) {
        Shop shop = shopRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid shop id: " + id));
        shop.setReactionCount(shop.getReactionCount() + 1);
        shopRepository.save(shop);
        return "redirect:/detail/" + id;
    }

    @PostMapping("/wanttogo/{id}")
    public String addWantToGo(@PathVariable Long id) {
        Shop shop = shopRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid shop id: " + id));
        shop.setWantToGoCount(shop.getWantToGoCount() + 1);
        shopRepository.save(shop);
        return "redirect:/detail/" + id;
    }

    @PostMapping("/review/{id}")
    public String addReview(@PathVariable Long id, @RequestParam String posterName, @RequestParam String comment) {
        Shop shop = shopRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid shop id: " + id));
        Review review = new Review();
        review.setShop(shop);
        review.setPosterName(posterName);
        review.setComment(comment);
        reviewRepository.save(review);
        return "redirect:/detail/" + id;
    }

    @GetMapping("/map")
    public String map() {
        return "map";
    }

    @GetMapping("/api/shops")
    @ResponseBody
    public List<Map<String, Object>> apiShops() {
        return shopRepository.findAll().stream()
                .filter(s -> s.getLatitude() != null && s.getLongitude() != null)
                .map(s -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", s.getId());
                    map.put("name", s.getName());
                    map.put("area", s.getArea());
                    map.put("category", s.getCategory());
                    map.put("rating", s.getRating());
                    map.put("latitude", s.getLatitude());
                    map.put("longitude", s.getLongitude());
                    map.put("imageUrl", s.getImageUrl1() != null ? s.getImageUrl1() : "");
                    return map;
                })
                .collect(Collectors.toList());
    }
}
