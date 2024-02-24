package orders.split.validations;

import orders.split.enums.KeyType;
import orders.split.exceptions.ValidationException;
import orders.split.models.Order;

import static java.util.Objects.nonNull;
import static orders.split.utils.Utils.getVariableValueFromConfig;

public class Checker {

  public static void check(Order order) throws ValidationException {
    if (order.getIndividualOrders().size() < 2) {
      throw new ValidationException("Must have at least 2 individualOrders");
    }
    if (order.getDiscount() > order.getTotalPrice()) {
      throw new ValidationException("The discount value is greater than totalOrderPrice... Nothing to do here...");
    }
  }

  public static boolean checkEnvironment() throws ValidationException {
    try {
      final String pixKey = getVariableValueFromConfig("pixKey");
      final KeyType keyType = KeyType.from(getVariableValueFromConfig("keyType"));

      return nonNull(pixKey) && nonNull(keyType);
    } catch (NullPointerException e) {
      return false;
    }
  }
}
