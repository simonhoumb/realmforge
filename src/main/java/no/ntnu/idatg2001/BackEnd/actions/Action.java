package no.ntnu.idatg2001.BackEnd.actions;

import no.ntnu.idatg2001.BackEnd.entityinformation.Entity;

public interface Action {
  public void execute(Entity entity);
}
