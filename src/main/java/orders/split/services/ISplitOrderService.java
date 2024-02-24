package orders.split.services;

import orders.split.exceptions.ValidationException;
import orders.split.models.Pix;
import orders.split.models.Order;

import java.util.List;

public interface ISplitOrderService {

  List<Pix> execute(Order order) throws ValidationException;
}
