package ntnu.no.idatg2001.BackEnd.goals;

import ntnu.no.idatg2001.BackEnd.entityinformation.Entity;

public interface Goal {

  public boolean isFulfilled(Entity entity);
}
