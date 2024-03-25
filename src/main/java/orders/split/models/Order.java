package orders.split.models;

import lombok.NoArgsConstructor;
import orders.split.exceptions.ValidationException;
import orders.split.http.views.OrderView;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import orders.split.validations.Checker;

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

  public static Order from(OrderView view) throws ValidationException {
    Checker.check(view);

    final Map<String, Double> orders = new HashMap<>();

    for (IndividualOrder order : view.getIndividualOrders()) {
      orders.put(order.getName(), order.getOrderValue());
    }
    return Order.create(orders, view.getTotalPrice(), view.getDeliveryFee(), view.getDiscount());
  }
}
