package com.azubike.ellipsis.services;

import com.azubike.ellipsis.domain.checkout.Cart;
import com.azubike.ellipsis.domain.checkout.CartItem;
import com.azubike.ellipsis.domain.checkout.CheckoutResponse;
import com.azubike.ellipsis.domain.checkout.CheckoutStatus;

import java.util.List;
import java.util.stream.Collectors;

import static com.azubike.ellipsis.utils.CommonUtil.startTimer;
import static com.azubike.ellipsis.utils.CommonUtil.timeTaken;
import static com.azubike.ellipsis.utils.LoggerUtil.log;

public class CheckoutService {
  private PriceValidatorService priceValidatorService;

  public CheckoutService(PriceValidatorService priceValidatorService) {
    this.priceValidatorService = priceValidatorService;
  }

  public CheckoutResponse checkout(Cart cart) {
    startTimer();
    final List<CartItem> invalidCartItems =
        cart.getCartItemList()
            .parallelStream()
            .peek(
                cartItem -> {
                  final boolean cartItemInvalid = priceValidatorService.isCartItemInvalid(cartItem);
                  cartItem.setExpired(cartItemInvalid);
                })
            .filter(CartItem::isExpired)
            .collect(Collectors.toList());
    //double price = calculateFinalPrice(cart);
    final double price = calculateFinalPrice_Reduce(cart);
    log("Checkout final price is " + price);

    timeTaken();

    if (invalidCartItems.size() > 0) {
      return new CheckoutResponse(CheckoutStatus.FAILURE, invalidCartItems);
    }

    return new CheckoutResponse(CheckoutStatus.SUCCESS, price);
  }

  private double calculateFinalPrice(Cart cart) {
    return cart.getCartItemList()
        .parallelStream()
        .map(item -> item.getRate() * item.getQuantity())
        .mapToDouble(Double::doubleValue)
        .sum();
  }

  private double calculateFinalPrice_Reduce(Cart cart) {
    return cart.getCartItemList()
        .parallelStream()
        .map(item -> item.getRate() * item.getQuantity())
        .reduce(0.0, Double::sum);
  }
}
