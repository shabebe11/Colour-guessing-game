package nz.ac.auckland.se281.engine;

import java.util.ArrayList;
import nz.ac.auckland.se281.Main.Difficulty;
import nz.ac.auckland.se281.cli.MessageCli;
import nz.ac.auckland.se281.cli.Utils;
import nz.ac.auckland.se281.model.Colour;

public class Game {

  public static String AI_NAME = "HAL-9000";

  public static boolean checkColour(String input) {
    // checkColour determines whether the user input is valid or not
    switch (input) {
      case "RED", "R":
        return true;
      case "GREEN", "G":
        return true;
      case "BLUE", "B":
        return true;
      case "YELLOW", "Y":
        return true;
      default:
        MessageCli.INVALID_HUMAN_INPUT.printMessage();
        return false;
    }
  }

  private int totalRounds = 0;
  private int currentRound;
  private String namePlayer;
  private String[] inputs;
  private Level ai;
  private int playerPoints;
  private int aiPoints;
  private ArrayList<Colour> playerHistory;
  private boolean win = true;
  private Colour previous;
  private boolean gameStarted = false;

  public void newGame(Difficulty difficulty, int numRounds, String[] options) {
    // Creates a new game and resets all the previous or initilises the new values for the game
    namePlayer = options[0];
    MessageCli.WELCOME_PLAYER.printMessage(namePlayer);
    totalRounds = numRounds;
    ai = AiFactory.createAi(difficulty);
    this.currentRound = 0;
    aiPoints = 0;
    playerPoints = 0;
    playerHistory = new ArrayList<Colour>();
    gameStarted = true;
  }

  public void play() {

    // play is the function called when a round is going to be played
    if (!gameStarted) {
      MessageCli.GAME_NOT_STARTED.printMessage();
      return;
    }

    currentRound++;

    // Printing out that the round is starting and the round points are reset to zero
    int aiPoint = 0;
    int playerPoint = 0;
    MessageCli.START_ROUND.printMessage(currentRound, totalRounds);
    boolean valid = false;
    while (!valid) {
      MessageCli.ASK_HUMAN_INPUT.printMessage();
      String input = Utils.scanner.nextLine();
      inputs = input.toUpperCase().split(" ");
      if (inputs.length != 2) {
        MessageCli.INVALID_HUMAN_INPUT.printMessage();
        continue;
      }
      if ((checkColour(inputs[0])) && (checkColour(inputs[1]))) {
        valid = true;
      }
    }

    // Getting the user input colours and validating them
    Colour userColour = Colour.fromInput(inputs[0]);
    Colour userPick = Colour.fromInput(inputs[1]);
    MessageCli.PRINT_INFO_MOVE.printMessage(namePlayer, userColour.toString(), userPick.toString());

    if (!(playerHistory.isEmpty())) {
      previous = playerHistory.get(playerHistory.size() - 1);
    }

    // Getting the ai colours through the factory and the strategy designs
    Colour aiColour = ai.getColour(1, previous, playerHistory, win);
    Colour aiPick = ai.getColour(currentRound, previous, playerHistory, win);
    MessageCli.PRINT_INFO_MOVE.printMessage(AI_NAME, aiColour, aiPick);

    if (userPick.equals(aiColour)) {
      playerPoint++;
    }
    if (aiPick.equals(userColour)) {
      aiPoint++;
      win = true;
    }

    // Printing and equating points to the powercolour
    if (currentRound % 3 == 0) {
      Colour powerColour = Colour.getRandomColourForPowerColour();
      MessageCli.PRINT_POWER_COLOUR.printMessage(powerColour.toString());
      if (userPick.equals(aiColour) && (userPick.equals(powerColour))) {
        playerPoint += 2;
      }
      if (aiPick.equals(userColour) && (aiPick.equals(powerColour))) {
        aiPoint += 2;
      }
    }
    if (!(aiPick.equals(userColour))) {
      win = false;
    }

    // Printing the outcome of the round
    MessageCli.PRINT_OUTCOME_ROUND.printMessage(namePlayer, playerPoint);
    MessageCli.PRINT_OUTCOME_ROUND.printMessage(AI_NAME, aiPoint);
    playerPoints += playerPoint;
    aiPoints += aiPoint;

    playerHistory.add(userColour);

    if (currentRound == totalRounds) {
      this.showStats();
    }
  }

  public void showStats() {
    // Show stats shows the stats of botht the player and the ai at anypoint when the function is
    // called
    if (!gameStarted) {
      MessageCli.GAME_NOT_STARTED.printMessage();
      return;
    }

    MessageCli.PRINT_PLAYER_POINTS.printMessage(namePlayer, playerPoints);
    MessageCli.PRINT_PLAYER_POINTS.printMessage(AI_NAME, aiPoints);

    // showStats also shows the player and ai stats at the end of the game as well as who won or if
    // it was a tie
    if (currentRound == totalRounds) {
      MessageCli.PRINT_END_GAME.printMessage();
      if (aiPoints == playerPoints) {
        MessageCli.PRINT_TIE_GAME.printMessage();
      } else if (aiPoints > playerPoints) {
        MessageCli.PRINT_WINNER_GAME.printMessage(AI_NAME);
      } else {
        MessageCli.PRINT_WINNER_GAME.printMessage(namePlayer);
      }
      gameStarted = false;
    }
  }
}
