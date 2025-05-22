package nz.ac.auckland.se281.engine;

import java.util.ArrayList;
import nz.ac.auckland.se281.model.Colour;

public class HardStrategy implements Level {
  private AiPlayer ai = new AiPlayer();
  private Strategy previousStrategy;

  @Override
  public Colour getColour(
      // Get colour gets the colour the colour using the correct strategy
      int currentRound, Colour previous, ArrayList<Colour> history, boolean win) {
    if (currentRound <= 2) {
      ai.setStrategy(new Random());
      return ai.chooseColour(previous);
    }

    // Switching the strategy to leastUsed in the third round
    else if (currentRound == 3) {
      ai.setStrategy(new LeastUsed());
      Colour leastUsed = leastUsed(history);
      previousStrategy = ai.getstrategy();
      return ai.chooseColour(leastUsed);
    } else {

      // if the ai did not win it changes the strategy as long as its in the fourth round
      if (win == false) {
        if (previousStrategy instanceof AvoidLast) {
          ai.setStrategy(new LeastUsed());
          Colour leastUsed = leastUsed(history);
          previousStrategy = ai.getstrategy();
          return ai.chooseColour(leastUsed);
        } else {
          ai.setStrategy(new AvoidLast());
          previousStrategy = ai.getstrategy();
          return ai.chooseColour(previous);
        }
      } else {
        if (previousStrategy instanceof AvoidLast) {
          ai.setStrategy(new AvoidLast());
          previousStrategy = ai.getstrategy();
          return ai.chooseColour(previous);
        } else {
          ai.setStrategy(new LeastUsed());
          Colour leastUsed = leastUsed(history);
          previousStrategy = ai.getstrategy();
          return ai.chooseColour(leastUsed);
        }
      }
    }
  }

  public Colour leastUsed(ArrayList<Colour> history) {

    // Going through the players previous colours picked and determining the least used colour to
    // pick it
    int blue = 0;
    int green = 0;
    int yellow = 0;
    int red = 0;
    for (Colour c : history) {
      switch (c) {
        case RED:
          red++;
          break;
        case GREEN:
          green++;
          break;
        case BLUE:
          blue++;
          break;
        case YELLOW:
          yellow++;
          break;
        default:
          break;
      }
    }
    int min = Math.min(Math.min(red, green), Math.min(blue, yellow));

    if (red == min) {
      return Colour.RED;
    }
    if (green == min) {
      return Colour.GREEN;
    }
    if (blue == min) {
      return Colour.BLUE;
    }
    return Colour.YELLOW;
  }
}
