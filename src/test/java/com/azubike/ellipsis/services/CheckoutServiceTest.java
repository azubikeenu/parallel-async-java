package com.azubike.ellipsis.services;

import com.azubike.ellipsis.domain.checkout.Cart;
import com.azubike.ellipsis.domain.checkout.CheckoutResponse;
import com.azubike.ellipsis.domain.checkout.CheckoutStatus;
import com.azubike.ellipsis.utils.DataSet;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CheckoutServiceTest {
  PriceValidatorService priceValidatorService = new PriceValidatorService();
  final CheckoutService checkoutService = new CheckoutService(priceValidatorService);

  @Test
  void numberOfCores() {
    System.out.println("number of cores " + Runtime.getRuntime().availableProcessors());
  }

  @Test
  void checkout_6_items() {
    final Cart cart = DataSet.createCart(6);
    final CheckoutResponse checkout = checkoutService.checkout(cart);
    assertEquals(CheckoutStatus.SUCCESS, checkout.getCheckoutStatus());
    assertTrue(checkout.getFinalRate() > 0);
  }

  @Test
  void checkout_13_items() {
    final Cart cart = DataSet.createCart(13);
    final CheckoutResponse checkout = checkoutService.checkout(cart);
    assertEquals(CheckoutStatus.FAILURE, checkout.getCheckoutStatus());
  }

  @Test
  void checkout_25_items() {
    final Cart cart = DataSet.createCart(25);
    final CheckoutResponse checkout = checkoutService.checkout(cart);
    assertEquals(CheckoutStatus.FAILURE, checkout.getCheckoutStatus());
  }
}
