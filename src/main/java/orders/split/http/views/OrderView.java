package orders.split.http.views;

import orders.split.models.IndividualOrder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderView {

  private List<IndividualOrder> individualOrders;
  private double deliveryFee;
  private double discount;
  private double additions;
  private boolean shouldAddWaiterPercentage;

  public double getTotalPrice() {
    double total = this.getIndividualOrders().stream().mapToDouble(IndividualOrder::getOrderValue).sum() + this.getAdditions();

    return this.isShouldAddWaiterPercentage() ? total * 0.1 : total;
  }
}
