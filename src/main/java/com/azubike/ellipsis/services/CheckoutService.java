package com.azubike.ellipsis.services;

import com.azubike.ellipsis.domain.checkout.Cart;
import com.azubike.ellipsis.domain.checkout.CartItem;
import com.azubike.ellipsis.domain.checkout.CheckoutResponse;
import com.azubike.ellipsis.domain.checkout.CheckoutStatus;

import java.util.List;
import java.util.stream.Collectors;

import static com.azubike.ellipsis.utils.CommonUtil.startTimer;
import static com.azubike.ellipsis.utils.CommonUtil.timeTaken;

public class CheckoutService {
  private PriceValidatorService priceValidatorService;

  public CheckoutService(PriceValidatorService priceValidatorService) {
    this.priceValidatorService = priceValidatorService;
  }

  public CheckoutResponse checkout(Cart cart) {
    startTimer();
    final List<CartItem> invalidCartItems =
        cart.getCartItemList().parallelStream()
            .map(
                cartItem -> {
                  final boolean cartItemInvalid = priceValidatorService.isCartItemInvalid(cartItem);
                  cartItem.setExpired(cartItemInvalid);
                  return cartItem;
                })
            .filter(CartItem::isExpired)
            .collect(Collectors.toList());

    timeTaken();

    if (invalidCartItems.size() > 0) {
      return new CheckoutResponse(CheckoutStatus.FAILURE, invalidCartItems);
    }

    return new CheckoutResponse(CheckoutStatus.SUCCESS);
  }
}
