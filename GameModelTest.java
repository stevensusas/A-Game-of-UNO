package org.cis1200;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

public class GameModelTest {

    @Test
    public void simpleRunUserWin() {
        GameModel gameModel = new GameModel();
        gameModel.modifyCurrentCard(new NumberCard("3", Color.BLUE));
        LinkedList<Card> newUserDeck = new LinkedList<>();
        LinkedList<Card> newComputerDeck = new LinkedList<>();
        for (int i = 0; i < 7; i++) {
            newUserDeck.add(new NumberCard(String.valueOf(i), Color.BLUE));
            newComputerDeck.add(new NumberCard(String.valueOf(i), Color.BLUE));
        }
        gameModel.modifyUserandComputerDeckforTest(newUserDeck, newComputerDeck);
        for (int i = 0; i < 6; i++) {
            gameModel.play(newUserDeck.get(0));
            gameModel.play(newComputerDeck.get(0));
        }

        gameModel.play(newUserDeck.get(0));
        assertTrue(gameModel.over());
        assertEquals("You won!", gameModel.winner());
        assertEquals(14, gameModel.getPastCards().size());
    }

    @Test
    public void simpleRunComputerWin() {
        GameModel gameModel = new GameModel();
        gameModel.modifyCurrentCard(new NumberCard(String.valueOf(3), Color.BLUE));
        LinkedList<Card> newUserDeck = new LinkedList<>();
        LinkedList<Card> newComputerDeck = new LinkedList<>();
        for (int i = 0; i < 7; i++) {
            newUserDeck.add(new NumberCard(String.valueOf(i), Color.BLUE));
            newComputerDeck.add(new NumberCard(String.valueOf(i), Color.BLUE));
        }
        gameModel.modifyUserandComputerDeckforTest(newUserDeck, newComputerDeck);
        for (int i = 0; i < 5; i++) {
            gameModel.play(newUserDeck.get(0));
            gameModel.play(newComputerDeck.get(0));
        }
        gameModel.userDrawForTest(new NumberCard("5", Color.GREEN));
        gameModel.pass();
        gameModel.play(newComputerDeck.get(0));
        gameModel.play(newUserDeck.get(0));
        gameModel.play(newComputerDeck.get(0));
        assertTrue(gameModel.over());
        assertEquals("You lost!", gameModel.winner());
    }

    @Test
    public void simpleRunTie() {
        GameModel gameModel = new GameModel();
        gameModel.modifyCurrentCard(new NumberCard("3", Color.BLUE));
        LinkedList<Card> newUserDeck = new LinkedList<>();
        LinkedList<Card> newComputerDeck = new LinkedList<>();
        LinkedList<Card> newDrawingDeck = new LinkedList<>();
        newDrawingDeck.add(new NumberCard("3", Color.BLUE));
        gameModel.modifyDrawingDeckforTest(newDrawingDeck);
        for (int i = 0; i < 7; i++) {
            newUserDeck.add(new NumberCard(String.valueOf(i), Color.BLUE));
            newComputerDeck.add(new NumberCard(String.valueOf(i), Color.BLUE));
        }
        gameModel.modifyUserandComputerDeckforTest(newUserDeck, newComputerDeck);
        for (int i = 0; i < 5; i++) {
            gameModel.play(newUserDeck.get(0));
            gameModel.play(newComputerDeck.get(0));
        }
        gameModel.draw(gameModel.getUserDeck());
        assertTrue(gameModel.over());
        assertEquals("Drawing deck empty, It's a tie!", gameModel.winner());
    }

    @Test
    public void unplayableCardDoesNotPlay() {
        GameModel gameModel = new GameModel();
        gameModel.modifyCurrentCard(new NumberCard("3", Color.BLUE));
        LinkedList<Card> newUserDeck = new LinkedList<>();
        LinkedList<Card> newComputerDeck = new LinkedList<>();
        for (int i = 0; i < 7; i++) {
            newUserDeck.add(new NumberCard(String.valueOf(i), Color.GREEN));
            newComputerDeck.add(new NumberCard(String.valueOf(i), Color.GREEN));
        }
        gameModel.modifyUserandComputerDeckforTest(newUserDeck, newComputerDeck);
        gameModel.play(newUserDeck.get(0));
        assertEquals(7, gameModel.getUserCardNumber());
        assertEquals("3", gameModel.getCurrentCard().getNumber());
        assertEquals(Color.BLUE, gameModel.getCurrentCard().getColor());
    }

    @Test
    public void aiHeuristicSimpleCase() {
        GameModel gameModel = new GameModel();
        gameModel.modifyCurrentCard(new NumberCard("0", Color.BLUE));
        LinkedList<Card> newUserDeck = new LinkedList<>();
        LinkedList<Card> newComputerDeck = new LinkedList<>();
        for (int i = 0; i < 7; i++) {
            newUserDeck.add(new NumberCard(String.valueOf(i), Color.BLUE));
            newComputerDeck.add(new NumberCard(String.valueOf(i), Color.GREEN));
        }
        gameModel.modifyUserandComputerDeckforTest(newUserDeck, newComputerDeck);
        gameModel.play(newUserDeck.get(0));
        assertEquals(1, gameModel.getAICurrentDeckPlayableCardCount(gameModel.getCurrentCard()));
        assertEquals(9, gameModel.getGlobalPlayableCardCount(newComputerDeck.get(0)));
        Card expected = new NumberCard("0", Color.GREEN);
        assertEquals(expected.getColor(), gameModel.getCardtoPlay().getColor());
        assertEquals(expected.getNumber(), gameModel.getCardtoPlay().getNumber());
    }

    @Test
    public void aiHeuristicAMoreComplexCase() {
        GameModel gameModel = new GameModel();
        gameModel.modifyCurrentCard(new NumberCard("0", Color.BLUE));
        LinkedList<Card> newUserDeck = new LinkedList<>();
        LinkedList<Card> newComputerDeck = new LinkedList<>();
        LinkedList<Card> newPastDeck = new LinkedList<>();
        for (int i = 0; i < 4; i++) {
            newUserDeck.add(new NumberCard(String.valueOf(i), Color.GREEN));
            newComputerDeck.add(new NumberCard(String.valueOf(i), Color.BLUE));
            newUserDeck.add(new NumberCard(String.valueOf(i), Color.YELLOW));
            newComputerDeck.add(new NumberCard(String.valueOf(i), Color.RED));
        }
        for (int i = 0; i < 6; i++) {
            newPastDeck.add(new NumberCard(String.valueOf(i), Color.BLUE));
        }
        for (int i = 0; i < 4; i++) {
            newPastDeck.add(new NumberCard(String.valueOf(i), Color.GREEN));
        }
        for (int i = 0; i < 2; i++) {
            newPastDeck.add(new NumberCard(String.valueOf(i), Color.YELLOW));
        }
        for (int i = 0; i < 2; i++) {
            newPastDeck.add(new NumberCard(String.valueOf(i), Color.RED));
        }

        gameModel.modifyUserandComputerDeckforTest(newUserDeck, newComputerDeck);
        gameModel.modifyPastDeckforTest(newPastDeck);
        gameModel.play(newUserDeck.get(0));
        assertEquals(2, gameModel.getAICurrentDeckPlayableCardCount(gameModel.getCurrentCard()));
        assertEquals(15, gameModel.getGlobalPlayableCardCount(newComputerDeck.get(0)));
        assertEquals(11, gameModel.getGlobalPlayableCardCount(newComputerDeck.get(1)));
        Card expected = new NumberCard("0", Color.BLUE);
        assertEquals(expected.getColor(), gameModel.getCardtoPlay().getColor());
        assertEquals(expected.getNumber(), gameModel.getCardtoPlay().getNumber());
    }

    @Test
    public void aiHeuristicWithWild() {
        GameModel gameModel = new GameModel();
        gameModel.modifyCurrentCard(new NumberCard("0", Color.BLUE));
        LinkedList<Card> newUserDeck = new LinkedList<>();
        LinkedList<Card> newComputerDeck = new LinkedList<>();
        LinkedList<Card> newPastDeck = new LinkedList<>();
        newComputerDeck.add(new WildCard());
        newUserDeck.add(new WildCard());
        for (int i = 0; i < 4; i++) {
            newUserDeck.add(new NumberCard(String.valueOf(i), Color.GREEN));
            newComputerDeck.add(new NumberCard(String.valueOf(i), Color.BLUE));
            newUserDeck.add(new NumberCard(String.valueOf(i), Color.YELLOW));
            newComputerDeck.add(new NumberCard(String.valueOf(i), Color.RED));
        }
        for (int i = 0; i < 6; i++) {
            newPastDeck.add(new NumberCard(String.valueOf(i), Color.BLUE));
        }
        for (int i = 0; i < 4; i++) {
            newPastDeck.add(new NumberCard(String.valueOf(i), Color.GREEN));
        }
        for (int i = 0; i < 2; i++) {
            newPastDeck.add(new NumberCard(String.valueOf(i), Color.YELLOW));
        }
        for (int i = 0; i < 2; i++) {
            newPastDeck.add(new NumberCard(String.valueOf(i), Color.RED));
        }

        gameModel.modifyUserandComputerDeckforTest(newUserDeck, newComputerDeck);
        gameModel.modifyPastDeckforTest(newPastDeck);
        gameModel.play(newUserDeck.get(1));
        assertEquals(3, gameModel.getAICurrentDeckPlayableCardCount(gameModel.getCurrentCard()));
        assertEquals(24, gameModel.getGlobalPlayableCardCount(newComputerDeck.get(0)));
        assertEquals(16, gameModel.getGlobalPlayableCardCount(newComputerDeck.get(1)));
        assertEquals(12, gameModel.getGlobalPlayableCardCount(newComputerDeck.get(2)));
        Card expected = new WildCard();
        assertEquals(expected.getColor(), gameModel.getCardtoPlay().getColor());
        assertEquals(expected.getNumber(), gameModel.getCardtoPlay().getNumber());
    }

    @Test
    public void aiHeuristicWithWildAsCurrentCard() {
        GameModel gameModel = new GameModel();
        gameModel.modifyCurrentCard(new NumberCard("0", Color.BLUE));
        LinkedList<Card> newUserDeck = new LinkedList<>();
        LinkedList<Card> newComputerDeck = new LinkedList<>();
        LinkedList<Card> newPastDeck = new LinkedList<>();
        newComputerDeck.add(new WildCard());
        newUserDeck.add(new WildCard());
        for (int i = 0; i < 4; i++) {
            newUserDeck.add(new NumberCard(String.valueOf(i), Color.GREEN));
            newComputerDeck.add(new NumberCard(String.valueOf(i), Color.BLUE));
            newUserDeck.add(new NumberCard(String.valueOf(i), Color.YELLOW));
            newComputerDeck.add(new NumberCard(String.valueOf(i), Color.RED));
        }
        for (int i = 0; i < 6; i++) {
            newPastDeck.add(new NumberCard(String.valueOf(i), Color.BLUE));
        }
        for (int i = 0; i < 4; i++) {
            newPastDeck.add(new NumberCard(String.valueOf(i), Color.GREEN));
        }
        for (int i = 0; i < 2; i++) {
            newPastDeck.add(new NumberCard(String.valueOf(i), Color.YELLOW));
        }
        for (int i = 0; i < 2; i++) {
            newPastDeck.add(new NumberCard(String.valueOf(i), Color.RED));
        }

        gameModel.modifyUserandComputerDeckforTest(newUserDeck, newComputerDeck);
        gameModel.modifyPastDeckforTest(newPastDeck);
        gameModel.play(newUserDeck.get(0));
        assertEquals(9, gameModel.getAICurrentDeckPlayableCardCount(gameModel.getCurrentCard()));
        assertEquals(24, gameModel.getGlobalPlayableCardCount(newComputerDeck.get(0)));
        assertEquals(16, gameModel.getGlobalPlayableCardCount(newComputerDeck.get(1)));
        assertEquals(12, gameModel.getGlobalPlayableCardCount(newComputerDeck.get(2)));
        Card expected = new WildCard();
        assertEquals(expected.getColor(), gameModel.getCardtoPlay().getColor());
        assertEquals(expected.getNumber(), gameModel.getCardtoPlay().getNumber());
    }

}
