package orders.split.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor(staticName = "create")
public class IndividualOrder {

  private String name;
  private double orderValue;
}
