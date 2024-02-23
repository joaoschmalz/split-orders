package com.softexpert.http.controllers;

import com.softexpert.exceptions.ValidationException;
import com.softexpert.http.views.LunchView;
import com.softexpert.models.IndividualOrder;
import com.softexpert.models.IndividualPix;
import com.softexpert.models.Lunch;
import com.softexpert.services.GeneratePaymentLinkService;
import com.softexpert.services.IGeneratePaymentLinkService;
import com.softexpert.validations.Checker;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LunchController {

  private final IGeneratePaymentLinkService generatePaymentLinkService = new GeneratePaymentLinkService();

  @PostMapping(path = "api/split")
  public ResponseEntity splitOrder(@RequestBody LunchView view) {
    try {
      Checker.check(view);
      List<IndividualPix> payments = this.generatePaymentLinkService.execute(Lunch.from(view));

      return ResponseEntity.ok(payments);
    } catch (ValidationException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}
