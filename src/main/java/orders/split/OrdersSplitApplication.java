package orders.split;

import orders.split.exceptions.ValidationException;
import orders.split.validations.Checker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OrdersSplitApplication {

  public static void main(String[] args) throws ValidationException {
    if (Checker.checkEnvironment()) {
      SpringApplication.run(OrdersSplitApplication.class, args);
    } else {
      System.out.println("First configure variables pixKey and/or keyType in config.properties");
    }
  }

}
