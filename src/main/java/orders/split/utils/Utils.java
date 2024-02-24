package orders.split.utils;

import orders.split.models.IndividualPix;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Utils {

  public static String getVariableValueFromConfig(final String name) {
    try {
      InputStream inputStream = IndividualPix.class.getClassLoader().getResourceAsStream("config.properties");

      Properties prop = new Properties();
      prop.load(inputStream);
      return prop.getProperty(name);
    } catch (IOException e) {
      return null;
    }
  }
}
