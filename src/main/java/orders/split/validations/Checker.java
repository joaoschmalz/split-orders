package orders.split.validations;

import orders.split.enums.KeyType;
import orders.split.exceptions.ValidationException;
import orders.split.http.views.LunchView;

import static java.util.Objects.nonNull;
import static orders.split.utils.Utils.getVariableValueFromConfig;

public class Checker {

  public static void check(LunchView view) throws ValidationException {
    if (view.getIndividualOrder().isEmpty()) {
      throw new ValidationException("IndividualOrder must have at least 1 person");
    }
    if (view.getDiscount() > view.getTotalPrice()) {
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
