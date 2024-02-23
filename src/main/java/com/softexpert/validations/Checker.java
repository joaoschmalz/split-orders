package com.softexpert.validations;

import com.softexpert.exceptions.ValidationException;
import com.softexpert.http.views.LunchView;

public class Checker {

  public static void check(LunchView view) throws ValidationException {
    if (view.getIndividualOrder().isEmpty()) {
      throw new ValidationException("IndividualOrder must have at least 1 person");
    }
    if (view.getDiscount() > view.getTotalPrice()) {
      throw new ValidationException("The discount value is greater than totalOrderPrice... Nothing to do here...");
    }
  }
}
