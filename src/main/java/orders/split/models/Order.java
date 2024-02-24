package orders.split.models;

import lombok.NoArgsConstructor;
import orders.split.http.views.OrderView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class Order {

  private Map<String, Double> individualOrders;
  private double totalPrice;
  private double deliveryFee;
  private double discount;

  public static Order from(OrderView view) {
    final Map<String, Double> orders = new HashMap<>();

    for (IndividualOrder order : view.getIndividualOrder()) {
      orders.put(order.getName(), order.getOrderValue());
    }
    return Order.create(orders, view.getTotalPrice(), view.getDeliveryFee(), view.getDiscount());
  }
}
