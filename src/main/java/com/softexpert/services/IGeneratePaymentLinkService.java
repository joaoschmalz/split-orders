package com.softexpert.services;

import com.softexpert.models.IndividualPix;
import com.softexpert.models.Lunch;

import java.util.List;

public interface IGeneratePaymentLinkService {

  List<IndividualPix> execute(Lunch lunch);
}
