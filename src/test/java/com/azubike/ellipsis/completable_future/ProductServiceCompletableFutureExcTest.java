package com.azubike.ellipsis.completable_future;

import com.azubike.ellipsis.domain.Product;
import com.azubike.ellipsis.services.InventoryService;
import com.azubike.ellipsis.services.ProductInfoService;
import com.azubike.ellipsis.services.ReviewService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceCompletableFutureExcTest {
  private final String PRODUCT_ID = "ABC123";
  @Mock
  ReviewService reviewService = mock(ReviewService.class);
  @Mock
  ProductInfoService productInfoService = mock(ProductInfoService.class);
  @Mock
  InventoryService inventoryService = mock(InventoryService.class);

  @InjectMocks
  ProductServiceCompletableFuture productServiceCompletableFuture;

  @Test
  void retrieveProductDetails_withInventory_approach_2() {
    when(productInfoService.retrieveProductInfo(PRODUCT_ID)).thenCallRealMethod();
    when(inventoryService.addInventory(any())).thenCallRealMethod();
    when(reviewService.retrieveReviews(PRODUCT_ID))
            .thenThrow(new RuntimeException("An Error Occurred"));

    final Product product =
            productServiceCompletableFuture.retrieveProductDetails_withInventory_approach_2(PRODUCT_ID);
    assertNotNull(product);
    assertEquals(0, product.getReview().getNoOfReviews());
  }

  @Test
  void retrieveProductDetails_withInventory_approach_3() {
    when(productInfoService.retrieveProductInfo(PRODUCT_ID))
            .thenThrow(new RuntimeException("An Exception occurred"));

    when(reviewService.retrieveReviews(PRODUCT_ID))
            .thenThrow(new RuntimeException("An Error Occurred"));

    assertThrows(
            RuntimeException.class,
            () ->
                    productServiceCompletableFuture.retrieveProductDetails_withInventory_approach_2(
                            PRODUCT_ID));
  }
}
