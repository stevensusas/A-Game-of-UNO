package org.cis1200;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;
import java.util.TreeMap;

public final class GameModel {
    private LinkedList<Card> user;
    private LinkedList<Card> computer;
    private LinkedList<Card> deck;
    private Card currentcard;
    private LinkedList<Card> pastCards;
    private boolean player;

    private boolean drawn;

    public GameModel() {
        user = new LinkedList<Card>();
        computer = new LinkedList<Card>();
        deck = new LinkedList<Card>();
        drawn = false;
        for (int i = 0; i < 9; i++) {
            deck.add(new NumberCard(String.valueOf(i), Color.YELLOW));
            deck.add(new NumberCard(String.valueOf(i), Color.BLUE));
            deck.add(new NumberCard(String.valueOf(i), Color.GREEN));
            deck.add(new NumberCard(String.valueOf(i), Color.RED));
        }

        for (int i = 0; i < 4; i++) {
            deck.add(new WildCard());
        }

        deck.add(new AddTwoCard(Color.YELLOW));
        deck.add(new AddTwoCard(Color.GREEN));
        deck.add(new AddTwoCard(Color.BLUE));
        deck.add(new AddTwoCard(Color.RED));
        deck.add(new WildAddFourCard());
        deck.add(new WildAddFourCard());

        player = true;
        initializeDraw(user);
        initializeDraw(computer);
        currentcard = drawInitialCard();
        pastCards = new LinkedList<Card>();
        pastCards.add(currentcard);
    }

    public LinkedList<Card> draw(LinkedList<Card> side) {
        LinkedList<Card> drawnCards = new LinkedList<Card>();
        if (currentcard.getNumber().equals("+4")) {
            for (int i = 0; i < 4; i++) {
                Random random = new Random();
                Card card = deck.remove(random.nextInt(deck.size()));
                side.add(card);
                drawnCards.add(card);
            }
            currentcard = new WildCard();
        } else if (currentcard.getNumber().equals("+2")) {
            for (int i = 0; i < 2; i++) {
                Random random = new Random();
                Card card = deck.remove(random.nextInt(deck.size()));
                side.add(card);
                drawnCards.add(card);
            }
            currentcard = new WildCard();
        } else {
            Random random = new Random();
            Card card = deck.remove(random.nextInt(deck.size()));
            side.add(card);
            drawnCards.add(card);
        }
        drawn = true;
        return drawnCards;
    }

    public Card drawInitialCard() {
        Random random = new Random();
        Card card = deck.remove(random.nextInt(deck.size()));
        return card;
    }

    public boolean getDrawn() {
        return drawn;
    }

    public void initializeDraw(LinkedList<Card> side) {
        for (int i = 0; i < 7; i++) {
            Random random = new Random();
            Card card = deck.remove(random.nextInt(deck.size()));
            side.add(card);
        }
    }

    public void play(Card card) {
        if (player && card.checkPlayable(currentcard)) {
            user.remove(card);
            pastCards.add(card);
            pass();
            currentcard = card;
            drawn = false;
        } else {
            if (!player && card.checkPlayable(currentcard) && !over()) {
                computer.remove(card);
                pastCards.add(card);
                pass();
                currentcard = card;
            }
        }
    }

    public int getUserCardNumber() {
        LinkedList<Card> cards = new LinkedList<Card>();
        cards.addAll(user);
        return cards.size();
    }

    public int getComputerCardNumber() {
        return computer.size();
    }

    public LinkedList<Card> getUserDeck() {
        LinkedList<Card> cards = new LinkedList<Card>();
        cards.addAll(user);
        return cards;
    }

    public LinkedList<Card> getUserDeckUnencapsulated() {
        return user;
    }

    public Card getCurrentCard() {
        return this.currentcard;
    }

    public int getAICurrentDeckPlayableCardCount(Card card) {
        int currentDeckPlayableCount = 0;
        for (Card c : computer) {
            if (card.checkPlayable(c)) {
                currentDeckPlayableCount = currentDeckPlayableCount + 1;
            }
        }
        return currentDeckPlayableCount;
    }

    public int getGlobalPlayableCardCount(Card card) {
        int pastCardPlayableCount = 0;
        for (Card c : pastCards) {
            if (card.checkPlayable(c)) {
                pastCardPlayableCount = pastCardPlayableCount + 1;
            }
        }
        pastCardPlayableCount = pastCardPlayableCount + getAICurrentDeckPlayableCardCount(card);
        return pastCardPlayableCount;
    }

    public int getAIPlayableCardCounttoCurrentCard() {
        int count = 0;
        for (Card c : computer) {
            if (c.checkPlayable(currentcard)) {
                count++;
            }
        }
        return count;
    }

    public Card getCardtoPlay() {
        if (getAIPlayableCardCounttoCurrentCard() == 0) {
            draw(computer);
        }
        TreeMap<Integer, Card> map = new TreeMap<Integer, Card>();
        for (Card c : computer) {
            if (c.checkPlayable(currentcard)) {
                map.put(getGlobalPlayableCardCount(c), c);
            }
        }
        return map.lastEntry().getValue();
    }

    public void pass() {
        player = !player;
        drawn = false;
    }

    public LinkedList<Card> getDrawingDeck() {
        LinkedList<Card> cards = new LinkedList<Card>();
        cards.addAll(deck);
        return cards;
    }

    public void reset() {
        user = new LinkedList<Card>();
        computer = new LinkedList<Card>();
        deck = new LinkedList<Card>();
        drawn = false;
        for (int i = 0; i < 9; i++) {
            deck.add(new NumberCard(String.valueOf(i), Color.YELLOW));
            deck.add(new NumberCard(String.valueOf(i), Color.BLUE));
            deck.add(new NumberCard(String.valueOf(i), Color.GREEN));
            deck.add(new NumberCard(String.valueOf(i), Color.RED));
        }
        for (int i = 0; i < 4; i++) {
            deck.add(new WildCard());
        }
        deck.add(new AddTwoCard(Color.YELLOW));
        deck.add(new AddTwoCard(Color.GREEN));
        deck.add(new AddTwoCard(Color.BLUE));
        deck.add(new AddTwoCard(Color.RED));
        deck.add(new WildAddFourCard());
        deck.add(new WildAddFourCard());

        player = true;
        initializeDraw(user);
        initializeDraw(computer);
        currentcard = drawInitialCard();
        pastCards = new LinkedList<Card>();
        pastCards.add(currentcard);
    }

    public boolean over() {
        return getDrawingDeck().size() == 0 || getUserCardNumber() == 0
                || getComputerCardNumber() == 0;
    }

    public String winner() {
        String message = null;
        if (over() && getDrawingDeck().size() == 0) {
            message = "Drawing deck empty, It's a tie!";
        }
        if (over() && getUserCardNumber() == 0) {
            message = "You won!";
        }
        if (over() && getComputerCardNumber() == 0) {
            message = "You lost!";
        }
        return message;
    }

    public LinkedList<Card> getPastCards() {
        LinkedList<Card> cards = new LinkedList<Card>();
        cards.addAll(pastCards);
        return cards;
    }

    public void modifyCurrentCard(Card newCard) {
        pastCards.remove(currentcard);
        currentcard = newCard;
        pastCards.add(newCard);
    }

    public void modifyUserandComputerDeckforTest(
            LinkedList<Card> newUserDeck, LinkedList<Card> newComputerDeck
    ) {
        user = newUserDeck;
        computer = newComputerDeck;
    }

    public void modifyDrawingDeckforTest(LinkedList<Card> newDrawingDeck) {
        deck = newDrawingDeck;
    }

    public void modifyPastDeckforTest(LinkedList<Card> newPastDeck) {
        pastCards = newPastDeck;
    }

    public void userDrawForTest(Card c) {
        user.add(c);
    }

    public void writeStringsToFile(
            LinkedList<LinkedList<Card>> list, String filePath,
            boolean append
    ) {
        try {
            FileWriter fw = new FileWriter(filePath, append);
            BufferedWriter bw = new BufferedWriter(fw);
            for (LinkedList<Card> e : list) {
                for (Card c : e) {
                    String color = "Color";
                    Color cColor = c.getColor();
                    if (Color.BLUE.equals(cColor)) {
                        color = "Blue";
                    } else if (Color.GREEN.equals(cColor)) {
                        color = "Green";
                    } else if (Color.RED.equals(cColor)) {
                        color = "Red";
                    } else if (Color.YELLOW.equals(cColor)) {
                        color = "Yellow";
                    } else if (Color.PINK.equals(cColor)) {
                        color = "Wild";
                    }
                    bw.write(c.getNumber() + " " + color);
                    bw.write(",");
                    bw.flush();
                }
                bw.newLine();
                bw.flush();
            }
        } catch (IOException e) {
            return;
        }

    }

    public void writeGameToFile(
            String filePath,
            boolean append
    ) {
        LinkedList<LinkedList<Card>> gamestate = new LinkedList<LinkedList<Card>>();
        LinkedList<Card> currentCard = new LinkedList<Card>();
        gamestate.add(user);
        gamestate.add(computer);
        gamestate.add(deck);
        gamestate.add(pastCards);
        currentCard.add(currentcard);
        gamestate.add(currentCard);
        writeStringsToFile(gamestate, filePath, append);
    }

    static Color cardStringtoColor(String string) {
        Color color = switch (string) {
            case "Blue" -> Color.BLUE;
            case "Red" -> Color.RED;
            case "Yellow" -> Color.YELLOW;
            case "Green" -> Color.GREEN;
            case "Wild" -> Color.PINK;
            default -> null;
        };
        return color;
    }

    static LinkedList<Card> stringToCardsList(String line) {
        LinkedList<Card> linelist = new LinkedList<>();
        try {
            for (String cardx : line.split(",")) {
                Card card = null;
                if (cardx.split(" ")[0].equals("+4")) {
                    card = new WildAddFourCard();
                } else if (cardx.split(" ")[0].equals("+2")) {
                    card = new AddTwoCard(cardStringtoColor(cardx.split(" ")[1]));
                } else if (cardx.split(" ")[0].equals("W")) {
                    card = new WildCard();
                } else {
                    card = new NumberCard(
                            cardx.split(" ")[0],
                            cardStringtoColor(cardx.split(" ")[1])
                    );
                }
                linelist.add(card);
            }
            return linelist;
        } catch (ArrayIndexOutOfBoundsException e) {
            return linelist;
        }
    }

    public void readGameToFile() {
        UNOGameStateIterator gamestate = new UNOGameStateIterator();
        user = stringToCardsList(gamestate.next());
        computer = stringToCardsList(gamestate.next());
        deck = stringToCardsList(gamestate.next());
        pastCards = stringToCardsList(gamestate.next());
        currentcard = stringToCardsList(gamestate.next()).get(0);
    }
}
