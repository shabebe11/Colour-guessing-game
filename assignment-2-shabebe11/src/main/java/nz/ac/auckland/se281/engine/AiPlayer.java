package nz.ac.auckland.se281.engine;

import nz.ac.auckland.se281.model.Colour;

public class AiPlayer {
  private Strategy strategy;

  public void setStrategy(Strategy strategy) {
    this.strategy = strategy;
  }

  public Strategy getstrategy() {
    return this.strategy;
  }

  public Colour chooseColour(Colour previous) {
    return strategy.pickColour(previous);
  }
}
