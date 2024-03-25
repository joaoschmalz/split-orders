package orders.split;

import orders.split.exceptions.ValidationException;
import orders.split.http.views.OrderView;
import orders.split.models.IndividualOrder;
import orders.split.models.Order;
import orders.split.models.Pix;
import orders.split.services.ISplitOrderService;
import orders.split.services.SplitOrderService;
import orders.split.utils.Utils;
import orders.split.validations.Checker;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@SpringBootTest
public class OrdersSplitApplicationTests {

  private final Order order = new Order();
  private final ISplitOrderService splitOrderService = new SplitOrderService();

  @Before
  public void init() {
    final Map<String, Double> individualOrders = new HashMap<>();
    individualOrders.put("Myself", 42.00);
    individualOrders.put("Bro", 8.00);

    this.order.setIndividualOrders(individualOrders);
    this.order.setTotalPrice(50.00);
    this.order.setDiscount(20.00);
    this.order.setDeliveryFee(8.00);
  }

  @Test(expected = ValidationException.class)
  public void ensureWontRunIfHasOnlyOneIndividualOrder() throws ValidationException {
    final OrderView view = this.createOrderView();
    view.getIndividualOrders().remove(0);

    Checker.check(view);
  }

  @Test
  public void ensureDataMatchesGivenExample() throws ValidationException {
    final List<Pix> pix = this.splitOrderService.execute(this.order);
    final String crc16Signature = pix.get(0).getEmv().substring(133);

    final String pixKey = Utils.getVariableValueFromConfig("pixKey");
    final String keyType = Utils.getVariableValueFromConfig("keyType");

    assertEquals("6,08", pix.get(0).getValue());
    assertEquals("C69C", crc16Signature);
    assertEquals("ALEATORY", keyType);
    assertEquals("fe259c42-f654-4804-ba19-ab65350bf7d6", pixKey);
  }

  @Test(expected = ValidationException.class)
  public void ensureWontRunWhenDiscountIsGreaterThanTotalPrice() throws ValidationException {
    final OrderView view = this.createOrderView();
    view.setDiscount(150);

    Checker.check(view);
  }

  @Test(expected = ValidationException.class)
  public void ensureOnlyPositiveValuesAreAllowedForDiscount() throws ValidationException {
    final OrderView view = this.createOrderView();
    view.setDiscount(-10);

    Checker.check(view);
  }

  @Test(expected = ValidationException.class)
  public void ensureOnlyPositiveValuesAreAllowedForDeliveryFee() throws ValidationException {
    final OrderView view = this.createOrderView();
    view.setDeliveryFee(-10);

    Checker.check(view);
  }

  @Test(expected = ValidationException.class)
  public void ensureOnlyPositiveValuesAreAllowedForIndividualOrders() throws ValidationException {
    final OrderView view = this.createOrderView();
    view.getIndividualOrders().add(IndividualOrder.create("Negative Guy", -10));

    Checker.check(view);
  }

  @Test(expected = ValidationException.class)
  public void ensureExistOnlyOneMyselfOccurrenceInIndividualOrders() throws ValidationException {
    final OrderView view = this.createOrderView();
    view.getIndividualOrders().add(IndividualOrder.create("Myself", 30));

    Checker.check(view);
  }

  private OrderView createOrderView() {
    final OrderView view = new OrderView();
    final List<IndividualOrder> individualOrders = new ArrayList<>();

    individualOrders.add(IndividualOrder.create("Myself", 42));
    individualOrders.add(IndividualOrder.create("Bro", 8));

    view.setIndividualOrders(individualOrders);
    view.setDeliveryFee(8);
    view.setDiscount(20);

    return view;
  }

}
