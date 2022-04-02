package com.azubike.ellipsis.threads;

import com.azubike.ellipsis.domain.Product;
import com.azubike.ellipsis.domain.ProductInfo;
import com.azubike.ellipsis.domain.Review;
import com.azubike.ellipsis.services.ProductInfoService;
import com.azubike.ellipsis.services.ReviewService;

import static com.azubike.ellipsis.utils.CommonUtil.stopWatch;
import static com.azubike.ellipsis.utils.LoggerUtil.log;

public class ProductServiceUsingThreads {
  private ProductInfoService productInfoService;
  private ReviewService reviewService;

  public ProductServiceUsingThreads(
      ProductInfoService productInfoService, ReviewService reviewService) {
    this.productInfoService = productInfoService;
    this.reviewService = reviewService;
  }

  public Product retrieveProductDetails(String productId) throws InterruptedException {
    stopWatch.start();

    ProductInfoRunnable productInfoRunnable = new ProductInfoRunnable(productId);

    ReviewServiceRunnable reviewServiceRunnable = new ReviewServiceRunnable(productId);

    Thread productServiceThread = new Thread(productInfoRunnable);
    Thread reviewServiceThread = new Thread(reviewServiceRunnable);

    productServiceThread.start();

    reviewServiceThread.start();

    productServiceThread.join();

    reviewServiceThread.join();

    ProductInfo productInfo = productInfoRunnable.getProductInfo(); // Non-blocking call
    Review review = reviewServiceRunnable.getReview(); // Non-blocking call

    stopWatch.stop();
    log("Total Time Taken : " + stopWatch.getTime());
    return new Product(productId, productInfo, review);
  }

  public static void main(String[] args) throws InterruptedException {

    ProductInfoService productInfoService = new ProductInfoService();
    ReviewService reviewService = new ReviewService();
    ProductServiceUsingThreads productService =
        new ProductServiceUsingThreads(productInfoService, reviewService);
    String productId = "ABC123";
    Product product = productService.retrieveProductDetails(productId);
    log("Product is " + product);
  }

  private class ProductInfoRunnable implements Runnable {
    private ProductInfo productInfo;
    private String productId;

    public ProductInfoRunnable(String productId) {
      this.productId = productId;
    }

    @Override
    public void run() {
      productInfo = productInfoService.retrieveProductInfo(productId);
    }

    public ProductInfo getProductInfo() {
      return productInfo;
    }
  }

  private class ReviewServiceRunnable implements Runnable {
    private String productId;
    private Review review;

    public ReviewServiceRunnable(String productId) {
      this.productId = productId;
    }

    @Override
    public void run() {
      review = reviewService.retrieveReviews(productId);
    }

    public Review getReview() {
      return review;
    }
  }
}
