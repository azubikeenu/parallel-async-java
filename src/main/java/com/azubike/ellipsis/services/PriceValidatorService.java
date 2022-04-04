package com.azubike.ellipsis.services;


import com.azubike.ellipsis.domain.checkout.CartItem;

import static com.azubike.ellipsis.utils.CommonUtil.delay;

public class PriceValidatorService {

    public boolean isCartItemInvalid(CartItem cartItem){
        int cartId = cartItem.getItemId();
        delay(500);
        if (cartId == 7 || cartId == 9 || cartId == 11) {
            return true;
        }
        return false;
    }
}
