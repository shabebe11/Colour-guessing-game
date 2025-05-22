package nz.ac.auckland.se281.engine;

import java.util.ArrayList;
import nz.ac.auckland.se281.model.Colour;

public interface Level {
  Colour getColour(int currentRound, Colour previous, ArrayList<Colour> history, boolean win);
}
