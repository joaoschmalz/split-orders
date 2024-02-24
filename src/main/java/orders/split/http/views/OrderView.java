package orders.split.http.views;

import orders.split.models.IndividualOrder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderView {

  private List<IndividualOrder> individualOrder;
  private double shippingPrice;
  private double discount;
  private double additions;
  private boolean shouldAddWaiterPercentage;

  public double getTotalPrice() {
    return this.isShouldAddWaiterPercentage() ?
        this.getIndividualOrder().stream().mapToDouble(IndividualOrder::getOrderValue).sum() + this.getAdditions() :
        this.getIndividualOrder().stream().mapToDouble(IndividualOrder::getOrderValue).sum() + this.getAdditions() * 0.1;
  }
}
