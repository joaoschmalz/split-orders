package orders.split.http.controllers;

import orders.split.http.views.OrderView;
import orders.split.exceptions.ValidationException;
import orders.split.models.Pix;
import orders.split.models.Order;
import orders.split.services.GeneratePaymentLinkService;
import orders.split.services.IGeneratePaymentLinkService;
import orders.split.validations.Checker;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8081")
public class OrderSplitController {

  private final IGeneratePaymentLinkService generatePaymentLinkService = new GeneratePaymentLinkService();

  @PostMapping(path = "api/order-split")
  public ResponseEntity splitOrder(@RequestBody OrderView view) {
    try {
      Checker.check(view);
      List<Pix> payments = this.generatePaymentLinkService.execute(Order.from(view));

      return ResponseEntity.ok(payments);
    } catch (ValidationException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}
