package com.azubike.ellipsis.completable_future;

import com.azubike.ellipsis.domain.Product;
import com.azubike.ellipsis.services.InventoryService;
import com.azubike.ellipsis.services.ProductInfoService;
import com.azubike.ellipsis.services.ReviewService;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProductServiceCompletableFutureTest {
  private final String PRODUCT_ID = "ABC123";
  ReviewService reviewService = new ReviewService();
  ProductInfoService productInfoService = new ProductInfoService();
  InventoryService inventoryService = new InventoryService();
  ProductServiceCompletableFuture productServiceCompletableFuture =
      new ProductServiceCompletableFuture(productInfoService, reviewService);

  ProductServiceCompletableFuture productServiceCompletableFutureWithInventory =
      new ProductServiceCompletableFuture(productInfoService, reviewService, inventoryService);

  @Test
  void retrieveProductDetails() {

    final Product product = productServiceCompletableFuture.retrieveProductDetails(PRODUCT_ID);
    assertNotNull(product);
    assertTrue(product.getProductInfo().getProductOptions().size() > 0);
    assertNotNull(product.getReview());
  }

  @Test
  void retrieveProductDetails_approach2() {
    final CompletableFuture<Product> productCompletableFuture =
        productServiceCompletableFuture.retrieveProductDetails_approach2(PRODUCT_ID);
    productCompletableFuture
        .thenAccept(
            product -> {
              assertNotNull(product);
              assertTrue(product.getProductInfo().getProductOptions().size() > 0);
              assertNotNull(product.getReview());
            })
        .join();
  }

  @Test
  void retrieveProductDetails_withInventory() {
    final Product product =
        productServiceCompletableFutureWithInventory.retrieveProductDetails_withInventory(
            PRODUCT_ID);
    assertNotNull(product);
    assertTrue(product.getProductInfo().getProductOptions().size() > 0);
    product
        .getProductInfo()
        .getProductOptions()
        .forEach(
            productOption -> {
              assertNotNull(productOption.getInventory());
            });
    assertNotNull(product.getReview());
  }

  @Test
  void retrieveProductDetails_withInventory_approach_2() {
    final Product product =
            productServiceCompletableFutureWithInventory.retrieveProductDetails_withInventory_approach_2(
                    PRODUCT_ID);
    assertNotNull(product);
    assertTrue(product.getProductInfo().getProductOptions().size() > 0);
    product
            .getProductInfo()
            .getProductOptions()
            .forEach(
                    productOption -> {
                      assertNotNull(productOption.getInventory());
                    });
    assertNotNull(product.getReview());

  }
}
