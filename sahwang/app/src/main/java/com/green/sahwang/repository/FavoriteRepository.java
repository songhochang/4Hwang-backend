package com.green.sahwang.repository;

import com.green.sahwang.entity.Favorite;
import com.green.sahwang.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    List<Favorite> findAllByReviewIn(List<Review> reviewList);

}