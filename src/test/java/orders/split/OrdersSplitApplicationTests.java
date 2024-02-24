package orders.split;

import orders.split.exceptions.ValidationException;
import orders.split.models.Order;
import orders.split.models.Pix;
import orders.split.services.ISplitOrderService;
import orders.split.services.SplitOrderService;
import orders.split.utils.Utils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

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
  public void ensureWontRunByIndividualOrdersSize() throws ValidationException {
    this.order.getIndividualOrders().remove("Bro");

    this.splitOrderService.execute(this.order);
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
    this.order.setDiscount(100.00);

    this.splitOrderService.execute(this.order);
  }
}
