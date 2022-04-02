package com.azubike.ellipsis.executors;

import com.azubike.ellipsis.domain.Product;
import com.azubike.ellipsis.domain.ProductInfo;
import com.azubike.ellipsis.domain.Review;
import com.azubike.ellipsis.services.ProductInfoService;
import com.azubike.ellipsis.services.ReviewService;

import java.util.concurrent.*;

import static com.azubike.ellipsis.utils.CommonUtil.stopWatch;
import static com.azubike.ellipsis.utils.LoggerUtil.log;

public class ProductServiceUsingExecutors {
  static ExecutorService executorService =
      Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
  private ProductInfoService productInfoService;
  private ReviewService reviewService;

  public ProductServiceUsingExecutors(
      ProductInfoService productInfoService, ReviewService reviewService) {
    this.productInfoService = productInfoService;
    this.reviewService = reviewService;
  }

  public Product retrieveProductDetails(String productId)
      throws ExecutionException, InterruptedException, TimeoutException {
    stopWatch.start();
    final Future<ProductInfo> productInfoFuture =
        executorService.submit(() -> productInfoService.retrieveProductInfo(productId));
    final Future<Review> reviewFuture =
        executorService.submit(() -> reviewService.retrieveReviews(productId));
    final ProductInfo productInfo = productInfoFuture.get(2, TimeUnit.SECONDS);
    final Review review = reviewFuture.get(2, TimeUnit.SECONDS);
    stopWatch.stop();
    log("Total Time Taken : " + stopWatch.getTime());
    return new Product(productId, productInfo, review);
  }

  public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {

    ProductInfoService productInfoService = new ProductInfoService();
    ReviewService reviewService = new ReviewService();
    ProductServiceUsingExecutors productService =
        new ProductServiceUsingExecutors(productInfoService, reviewService);
    String productId = "ABC123";
    Product product = productService.retrieveProductDetails(productId);
    log("Product is " + product);
    executorService.shutdown();
  }
}
