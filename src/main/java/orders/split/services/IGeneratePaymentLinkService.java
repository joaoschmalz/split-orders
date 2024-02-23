package orders.split.services;

import orders.split.models.IndividualPix;
import orders.split.models.Lunch;

import java.util.List;

public interface IGeneratePaymentLinkService {

  List<IndividualPix> execute(Lunch lunch);
}
