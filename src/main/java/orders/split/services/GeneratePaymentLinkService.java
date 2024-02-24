package orders.split.services;

import orders.split.exceptions.ValidationException;
import orders.split.models.Pix;
import orders.split.models.Order;

import java.util.ArrayList;
import java.util.List;

public class GeneratePaymentLinkService implements IGeneratePaymentLinkService {


  @Override
  public List<Pix> execute(final Order order) throws ValidationException {
    this.calculateProportionalPayments(order);

    List<Pix> pixes = new ArrayList<>();

    for (String key : order.getIndividualPrice().keySet()) {
      if ("Myself".equals(key)) continue;
      pixes.add(Pix.create(key, order.getIndividualPrice().get(key)));
    }

    return pixes;
  }

  private void calculateProportionalPayments(final Order order) {
    for (String key : order.getIndividualPrice().keySet()) {
      final Double individual = order.getIndividualPrice().get(key);

      final double proportionalShipping = (individual / order.getTotalPrice()) * order.getShippingPrice();
      final double proportionalDiscount = (individual / order.getTotalPrice()) * order.getDiscount();

      final double finalProportionalPrice = individual - proportionalDiscount + proportionalShipping;

      order.getIndividualPrice().put(key, finalProportionalPrice);
    }
  }
}
