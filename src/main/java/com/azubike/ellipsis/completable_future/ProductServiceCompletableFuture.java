package com.azubike.ellipsis.completable_future;

import com.azubike.ellipsis.domain.*;
import com.azubike.ellipsis.services.InventoryService;
import com.azubike.ellipsis.services.ProductInfoService;
import com.azubike.ellipsis.services.ReviewService;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static com.azubike.ellipsis.utils.CommonUtil.stopWatch;
import static com.azubike.ellipsis.utils.CommonUtil.timeTaken;
import static com.azubike.ellipsis.utils.LoggerUtil.log;

public class ProductServiceCompletableFuture {
  private ProductInfoService productInfoService;
  private ReviewService reviewService;
  private InventoryService inventoryService;

  public ProductServiceCompletableFuture(
      ProductInfoService productInfoService, ReviewService reviewService) {
    this.productInfoService = productInfoService;
    this.reviewService = reviewService;
  }

  public ProductServiceCompletableFuture(
      ProductInfoService productInfoService,
      ReviewService reviewService,
      InventoryService inventoryService) {
    this.productInfoService = productInfoService;
    this.reviewService = reviewService;
    this.inventoryService = inventoryService;
  }

  public Product retrieveProductDetails(String productId) {
    stopWatch.start();
    final CompletableFuture<ProductInfo> productInfoCompletableFuture =
        CompletableFuture.supplyAsync(() -> productInfoService.retrieveProductInfo(productId));
    final CompletableFuture<Review> reviewCompletableFuture =
        CompletableFuture.supplyAsync(() -> reviewService.retrieveReviews(productId));
    final Product product =
        productInfoCompletableFuture
            .thenCombine(
                reviewCompletableFuture,
                (productInfo, review) -> new Product(productId, productInfo, review))
            .join();
    stopWatch.stop();
    log("Total Time Taken : " + stopWatch.getTime());
    return product;
  }

  public CompletableFuture<Product> retrieveProductDetails_approach2(String productId) {
    stopWatch.start();
    final CompletableFuture<ProductInfo> productInfoCompletableFuture =
        CompletableFuture.supplyAsync(() -> productInfoService.retrieveProductInfo(productId));

    final CompletableFuture<Review> reviewCompletableFuture =
        CompletableFuture.supplyAsync(() -> reviewService.retrieveReviews(productId));

    final CompletableFuture<Product> productCompletableFuture =
        productInfoCompletableFuture.thenCombine(
            reviewCompletableFuture,
            (productInfo, review) -> new Product(productId, productInfo, review));
    stopWatch.stop();
    log("Total Time Taken : " + stopWatch.getTime());
    return productCompletableFuture;
  }

  public Product retrieveProductDetails_withInventory(String productId) {
    stopWatch.start();
    final CompletableFuture<ProductInfo> productInfoCompletableFuture =
        CompletableFuture.supplyAsync(() -> productInfoService.retrieveProductInfo(productId))
            .thenApply(
                productInfo -> {
                  final List<ProductOption> productOptionList = updateProductInfo(productInfo);
                  productInfo.setProductOptions(productOptionList);
                  return productInfo;
                });
    final CompletableFuture<Review> reviewCompletableFuture =
        CompletableFuture.supplyAsync(() -> reviewService.retrieveReviews(productId));

    final Product product =
        productInfoCompletableFuture
            .thenCombine(
                reviewCompletableFuture,
                (productInfo, review) -> new Product(productId, productInfo, review))
            .join();
    timeTaken();
    return product;
  }

  public Product retrieveProductDetails_withInventory_approach_2(String productId) {
    stopWatch.start();
    final CompletableFuture<ProductInfo> productInfoCompletableFuture =
            CompletableFuture.supplyAsync(() -> productInfoService.retrieveProductInfo(productId))
                    .thenApply(
                            productInfo -> {
                                final List<ProductOption> productOptionList =
                                        updateProductInfo_Approach_2(productInfo);
                                productInfo.setProductOptions(productOptionList);
                                return productInfo;
                            });
      final CompletableFuture<Review> reviewCompletableFuture =
              CompletableFuture.supplyAsync(() -> reviewService.retrieveReviews(productId))
                      .exceptionally(
                              ex -> {
                                  log("Handled Exception in the reviewService " + ex.getMessage());
                                  return Review.builder().noOfReviews(0).overallRating(0.0).build();
                              });

    final Product product =
            productInfoCompletableFuture
                    .thenCombine(
                            reviewCompletableFuture,
                            (productInfo, review) -> new Product(productId, productInfo, review))
                    .whenComplete(
                            (res, ex) -> {
                                if (ex != null) {
                                    log("When complete Exception " + ex.getMessage());
                                }
                            })
            .join();
    timeTaken();
    return product;
  }

  private List<ProductOption> updateProductInfo(ProductInfo productInfo) {
    return productInfo.getProductOptions().stream()
        .peek(
            productOption -> {
              final Inventory inventory = inventoryService.addInventory(productOption);
              productOption.setInventory(inventory);
            })
        .parallel()
        .collect(Collectors.toList());
  }

  private List<ProductOption> updateProductInfo_Approach_2(ProductInfo productInfo) {
    final List<CompletableFuture<ProductOption>> productOptions =
        productInfo.getProductOptions().stream()
            .map(
                productOption ->
                    CompletableFuture.supplyAsync(
                            () -> inventoryService.addInventory(productOption))
                        .thenApply(
                            inventory -> {
                              productOption.setInventory(inventory);
                              return productOption;
                            }))
            .collect(Collectors.toList());
    return productOptions.stream().map(CompletableFuture::join).collect(Collectors.toList());
  }

  public static void main(String[] args) {

    ProductInfoService productInfoService = new ProductInfoService();
    ReviewService reviewService = new ReviewService();
    ProductServiceCompletableFuture productService =
        new ProductServiceCompletableFuture(productInfoService, reviewService);
    String productId = "ABC123";
    Product product = productService.retrieveProductDetails(productId);
    log("Product is " + product);
  }
}
