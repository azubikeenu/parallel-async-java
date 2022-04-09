package com.azubike.ellipsis.services;

import com.azubike.ellipsis.domain.Inventory;
import com.azubike.ellipsis.domain.ProductOption;

import java.util.concurrent.CompletableFuture;

import static com.azubike.ellipsis.utils.CommonUtil.delay;

public class InventoryService {
  public Inventory addInventory(ProductOption productOption) {
    delay(500);
    return Inventory.builder().count(2).build();
  }

  public CompletableFuture<Inventory> addInventory_CF(ProductOption productOption) {
    return CompletableFuture.supplyAsync(
        () -> {
          delay(500);
          return Inventory.builder().count(2).build();
        });
  }
}
