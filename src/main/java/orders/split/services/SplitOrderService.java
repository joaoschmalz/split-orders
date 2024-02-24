package orders.split.services;

import orders.split.exceptions.ValidationException;
import orders.split.models.Pix;
import orders.split.models.Order;
import orders.split.validations.Checker;

import java.util.ArrayList;
import java.util.List;

public class SplitOrderService implements ISplitOrderService {


  @Override
  public List<Pix> execute(final Order order) throws ValidationException {
    Checker.check(order);

    this.calculateProportionalPayments(order);

    List<Pix> pixes = new ArrayList<>();

    for (String key : order.getIndividualOrders().keySet()) {
      if ("Myself".equals(key)) continue;
      pixes.add(Pix.create(key, order.getIndividualOrders().get(key)));
    }

    return pixes;
  }

  private void calculateProportionalPayments(final Order order) {
    for (String key : order.getIndividualOrders().keySet()) {
      final Double individual = order.getIndividualOrders().get(key);

      final double proportionalShipping = (individual / order.getTotalPrice()) * order.getDeliveryFee();
      final double proportionalDiscount = (individual / order.getTotalPrice()) * order.getDiscount();

      final double finalProportionalPrice = individual - proportionalDiscount + proportionalShipping;

      order.getIndividualOrders().put(key, finalProportionalPrice);
    }
  }
}
