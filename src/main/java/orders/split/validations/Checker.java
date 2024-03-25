package orders.split.validations;

import orders.split.enums.KeyType;
import orders.split.exceptions.ValidationException;
import orders.split.http.views.OrderView;
import orders.split.models.IndividualOrder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Objects.nonNull;
import static orders.split.utils.Utils.getVariableValueFromConfig;

public class Checker {

  public static void check(final OrderView view) throws ValidationException {
    if (view.getIndividualOrders().size() < 2) {
      throw new ValidationException("Must have at least 2 individualOrders");
    }
    if (view.getDiscount() > view.getTotalPrice()) {
      throw new ValidationException("The discount value is greater than totalOrderPrice... Nothing to do here...");
    }
    checkForNegativeValues(view);
    checkForDuplicateNames(view.getIndividualOrders());
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

  private static void checkForNegativeValues(final OrderView view) throws ValidationException {
    if (view.getDiscount() < 0 || view.getTotalPrice() < 0 || view.getDeliveryFee() < 0) {
      throw new ValidationException("None of the values can be negative");
    }

    for (IndividualOrder individual : view.getIndividualOrders()) {
      if (individual.getOrderValue() < 0) throw new ValidationException("None of the values can be negative");
    }
  }

  private static void checkForDuplicateNames(final List<IndividualOrder> individualOrders) throws ValidationException {
    final Set<String> names = new HashSet<>(individualOrders.stream().map(IndividualOrder::getName).toList());

    if (names.size() < individualOrders.size()) throw new ValidationException("Duplicate names are not allowed");
  }
}
