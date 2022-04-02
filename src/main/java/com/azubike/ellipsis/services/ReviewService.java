package com.azubike.ellipsis.services;

import com.azubike.ellipsis.domain.Review;

import static com.azubike.ellipsis.utils.CommonUtil.delay;

public class ReviewService {

    public Review retrieveReviews(String productId) {
        delay(1000);
        return new Review(200, 4.5);
    }
}