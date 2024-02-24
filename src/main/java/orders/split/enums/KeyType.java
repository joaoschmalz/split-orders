package orders.split.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import orders.split.exceptions.ValidationException;


@Getter
@AllArgsConstructor
public enum KeyType {
  CPF("2633"), CNPJ("2636"), EMAIL("2643"), PHONE("2636"), ALEATORY("2658");

  private final String code;

  public static KeyType from(final String value) throws ValidationException {
    try{
      return KeyType.valueOf(value.toUpperCase());
    } catch (Exception e) {
      throw new ValidationException("Wrong KeyType check config.properties...");
    }
  }
}
