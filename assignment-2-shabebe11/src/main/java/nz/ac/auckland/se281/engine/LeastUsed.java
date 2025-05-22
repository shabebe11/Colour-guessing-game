package nz.ac.auckland.se281.engine;

import nz.ac.auckland.se281.model.Colour;

public class LeastUsed implements Strategy {

  @Override
  public Colour pickColour(Colour leastUsed) {
    return leastUsed;
  }
}
