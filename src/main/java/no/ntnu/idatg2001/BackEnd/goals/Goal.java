package no.ntnu.idatg2001.BackEnd.goals;

import no.ntnu.idatg2001.BackEnd.entityinformation.Entity;

public interface Goal {

  public boolean isFulfilled(Entity entity);
}
