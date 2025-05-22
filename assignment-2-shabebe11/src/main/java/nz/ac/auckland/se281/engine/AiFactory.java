package nz.ac.auckland.se281.engine;

import nz.ac.auckland.se281.Main.Difficulty;

public class AiFactory {

  public static Level createAi(Difficulty type) {
    // Creating the ai based on which difficulty has been picked so that it can be used later

    switch (type) {
      case EASY:
        return new EasyStrategy();

      case MEDIUM:
        return new MediumStrategy();

      case HARD:
        return new HardStrategy();

      default:
        System.exit(0);
    }
    return null;
  }
}
