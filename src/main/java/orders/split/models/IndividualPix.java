package orders.split.models;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Writer;
import com.google.zxing.common.BitMatrix;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.Properties;

@Getter
@Setter
@AllArgsConstructor(staticName = "create")
public class IndividualPix {

  private String name;
  private String value;
  private String emv;
  private String qrCodeImage;

  public static IndividualPix create(String name, Double payment) {

    final String pixValue = new DecimalFormat("#.##").format(payment);
    final String emv = generateEmv(pixValue);

    return IndividualPix.create(name, "R$ " + pixValue, emv, generateQRCodeImage(emv, 400, 400) );
  }

  private static String generateEmv(final String pixValue)
  {
    String length = pixValue.length() < 10 ? "0" + pixValue.length() : String.valueOf(pixValue.length());

    int crc = 0xFFFF;
    int polynomial = 0x1021;

    final String pixKey = getPixKey();

    String emv = "000201"
        + "2658"
        + "0014br.gov.bcb.pix"
        + "01" + pixKey.length() + pixKey
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

  public static String generateQRCodeImage(String text, int width, int height) {
    try {
      Writer writer = new MultiFormatWriter();
      BitMatrix bitMatrix = writer.encode(text, BarcodeFormat.QR_CODE, width, height);
      BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
      int onColor = 0xFF000000;
      int offColor = 0xFFFFFFFF;

      for (int x = 0; x < width; x++) {
        for (int y = 0; y < height; y++) {
          image.setRGB(x, y, bitMatrix.get(x, y) ? onColor : offColor);
        }
      }

      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      ImageIO.write(image, "png", baos);
      baos.flush();
      byte[] imageBytes = baos.toByteArray();
      baos.close();
      return "data:image/png;base64," + DatatypeConverter.printBase64Binary(imageBytes);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  private static String getPixKey() {
    try {
      InputStream inputStream = IndividualPix.class.getClassLoader().getResourceAsStream("config.properties");

      Properties prop = new Properties();
      prop.load(inputStream);
      return prop.getProperty("pixKey");
    } catch (IOException e) {
      return null;
    }
  }
}
