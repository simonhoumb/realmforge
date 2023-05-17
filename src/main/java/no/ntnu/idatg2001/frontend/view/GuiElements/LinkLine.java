package no.ntnu.idatg2001.frontend.view.GuiElements;

import javafx.scene.paint.Color;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import no.ntnu.idatg2001.backend.gameinformation.Link;

public class LinkLine extends Line {

  private Link link;

  public LinkLine(double startX, double startY, double endX, double endY, Link link) {
    super(startX, startY, endX, endY);
    this.link = link;
    setStroke(Color.BLACK);
  }

  public Link getLink() {
    return link;
  }

  public void setLink(Link link) {
    this.link = link;
  }
}
