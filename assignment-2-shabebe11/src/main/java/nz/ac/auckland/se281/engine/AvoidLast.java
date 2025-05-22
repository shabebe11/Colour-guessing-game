package nz.ac.auckland.se281.engine;

import nz.ac.auckland.se281.model.Colour;

public class AvoidLast implements Strategy {

  @Override
  public Colour pickColour(Colour previous) {
    return Colour.getRandomColourExcluding(previous);
  }
}
