package nz.ac.auckland.se281.engine;

import java.util.ArrayList;
import nz.ac.auckland.se281.model.Colour;

public class EasyStrategy implements Level {

  @Override
  public Colour getColour(
      int currentRound, Colour previous, ArrayList<Colour> history, boolean win) {
    AiPlayer ai = new AiPlayer();
    ai.setStrategy(new Random());
    return ai.chooseColour(previous);
  }
}
