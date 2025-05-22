package nz.ac.auckland.se281.engine;

import java.util.ArrayList;
import nz.ac.auckland.se281.model.Colour;

public class MediumStrategy implements Level {

  @Override
  public Colour getColour(
      int currentRound, Colour previous, ArrayList<Colour> history, boolean win) {
    AiPlayer ai = new AiPlayer();

    // Using the random strategy in the first round
    if (currentRound == 1) {
      ai.setStrategy(new Random());
      return ai.chooseColour(previous);
    } else {
      // Switching to the AvoidLast strategy from the 2nd round onwards
      ai.setStrategy(new AvoidLast());
      return ai.chooseColour(previous);
    }
  }
}
