package no.ntnu.idatg2001.frontend.view.GuiElements;

import javafx.geometry.Point2D;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import no.ntnu.idatg2001.backend.gameinformation.Link;
import no.ntnu.idatg2001.backend.gameinformation.Passage;

public class LinkLine extends Line {
  private Link link;
  private PassageRectangle sourcePassage;
  private PassageRectangle targetPassage;

  public LinkLine(double startX, double startY, double endX, double endY,
      Link link, PassageRectangle sourcePassage, PassageRectangle targetPassage) {
    super(startX, startY, endX, endY);
    this.link = link;
    this.sourcePassage = sourcePassage;
    this.targetPassage = targetPassage;
  }

  public Link getLink() {
    return link;
  }

  public PassageRectangle getSourcePassage() {
    return sourcePassage;
  }

  public PassageRectangle getTargetPassage() {
    return targetPassage;
  }

}