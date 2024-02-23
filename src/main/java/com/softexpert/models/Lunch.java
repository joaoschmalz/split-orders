package com.softexpert.models;

import com.softexpert.http.views.LunchView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor(staticName = "create")
public class Lunch {

  private Map<String, Double> individualPrice;
  private double totalPrice;
  private double shippingPrice;
  private double discount;

  public static Lunch from(LunchView view) {
    final Map<String, Double> orders = new HashMap<>();

    for (IndividualOrder order : view.getIndividualOrder()) {
      orders.put(order.getName(), order.getOrderValue());
    }
    return Lunch.create(orders, view.getTotalPrice(), view.getShippingPrice(), view.getDiscount());
  }
}
