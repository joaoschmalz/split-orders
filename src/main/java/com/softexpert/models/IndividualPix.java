package com.softexpert.models;

import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor(staticName = "create")
public class IndividualPix {

  private String name;
  private String value;
  private String emv;
//  private String qRCode;

  public static IndividualPix create(String name, Double payment) {

    final String pixValue = new DecimalFormat("#.##").format(payment);

    return IndividualPix.create(name, "R$ " + pixValue, generateEmv(pixValue));
  }

  private static String generateEmv(final String pixValue)
  {
    String length = pixValue.length() < 10 ? "0" + pixValue.length() : String.valueOf(pixValue.length());

    int crc = 0xFFFF;
    int polynomial = 0x1021;

    String emv = "000201"
        + "2658"
        + "0014br.gov.bcb.pix"
        + "0136fe259c42-f654-4804-ba19-ab65350bf7d6"
        + "52040000"
        + "5303986"
        + "54" +  length + pixValue
        + "5802BR"
        + "5912Joao Schmalz"
        + "6009JOINVILLE"
        + "6207"
        + "0503***"
        + "6304";

    byte[] bytes = emv.getBytes(StandardCharsets.US_ASCII);

    for (byte b : bytes)
    {
      for (int i = 0; i < 8; i++)
      {
        boolean bit = ((b >> (7 - i) & 1) == 1);
        boolean c15 = ((crc >> 15 & 1) == 1);
        crc <<= 1;
        if (c15 ^ bit)
        {
          crc ^= polynomial;
        }
      }
    }

    crc &= 0xffff;

    return emv + String.format("%04X", crc);
  }
}
