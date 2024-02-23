package orders.split.services;

import orders.split.models.IndividualPix;
import orders.split.models.Lunch;

import java.util.ArrayList;
import java.util.List;

public class GeneratePaymentLinkService implements IGeneratePaymentLinkService {


  @Override
  public List<IndividualPix> execute(final Lunch lunch) {
    this.calculateProportionalPayments(lunch);

    List<IndividualPix> individualPix = new ArrayList<>();

    for (String key : lunch.getIndividualPrice().keySet()) {
      if ("Myself".equals(key)) continue;
      individualPix.add(IndividualPix.create(key, lunch.getIndividualPrice().get(key)));
    }

    return individualPix;
  }

  private void calculateProportionalPayments(final Lunch lunch) {
    for (String key : lunch.getIndividualPrice().keySet()) {
      final Double individual = lunch.getIndividualPrice().get(key);

      final double proportionalShipping = (individual / lunch.getTotalPrice()) * lunch.getShippingPrice();
      final double proportionalDiscount = (individual / lunch.getTotalPrice()) * lunch.getDiscount();

      final double finalProportionalPrice = individual - proportionalDiscount + proportionalShipping;

      lunch.getIndividualPrice().put(key, finalProportionalPrice);
    }
  }
}
