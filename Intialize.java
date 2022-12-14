package org.cis1200;

import javax.swing.*;
import java.awt.*;

class Initialize {
    private GameModel gameModel = new GameModel();

    public GameModel getGame() {
        return this.gameModel;
    }

    public class CurrentCard extends JComponent {
        private Card currentCard;

        public CurrentCard() {
            currentCard = gameModel.getCurrentCard();
        }

        @Override
        public void paintComponent(Graphics gc) {
            super.paintComponent(gc);
            gc.setColor(gameModel.getCurrentCard().getColor());
            gc.fillRect(0, 0, 40, 60);
            gc.setColor(Color.BLACK);
            gc.fillRect(45, 0, 5, 60);
            gc.drawString(gameModel.getCurrentCard().getNumber(), 20, 30);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(120, 100);
        }
    }

    public class DeckCard extends JComponent {

        private Card card;

        public DeckCard(Card c) {
            this.card = c;
        }

        public Card getCard() {
            return this.card;
        }

        public boolean checkPlayable() {
            return this.card.checkPlayable(gameModel.getCurrentCard());
        }

        @Override
        public void paintComponent(Graphics gc) {
            super.paintComponent(gc);
            if (gameModel.getUserDeck().contains(card)) {
                gc.setColor(card.getColor());
                gc.fillRect(0, 0, 40, 60);
                gc.setColor(Color.BLACK);
                gc.drawString(card.getNumber(), 20, 30);
            }
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(40, 60);
        }
    }

    public class AIDeck extends JComponent {

        @Override
        public void paintComponent(Graphics gc) {
            super.paintComponent(gc);
            for (int i = 0; i < gameModel.getComputerCardNumber(); i++) {
                gc.setColor(Color.BLACK);
                gc.fillRect(i * 25, 0, 20, 60);
                gc.setColor(Color.white);
                gc.fillRect((i + 1) * 20 + 5 * i, 0, 5, 60);
            }
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(25 * gameModel.getComputerCardNumber(), 60);
        }

    }

    public class NotificationPanel extends JComponent {

        @Override
        public void paintComponent(Graphics gc) {
            super.paintComponent(gc);
            if (gameModel.getComputerCardNumber() == 1) {
                gc.setColor(Color.GREEN);
                gc.fillRect(0, 0, 500, 500);
                gc.setColor(Color.BLACK);
                gc.drawString("UNO!", 125, 125);
            }
            if (gameModel.getUserCardNumber() == 1) {
                gc.setColor(Color.ORANGE);
                gc.fillRect(0, 0, 500, 500);
                gc.setColor(Color.BLACK);
                gc.drawString("UNO!", 125, 125);
            }
            if (gameModel.getComputerCardNumber() == 0) {
                gc.setColor(Color.BLACK);
                gc.fillRect(0, 0, 500, 500);
                gc.setColor(Color.WHITE);
                gc.drawString("Game Over! You Lost!", 125, 125);
            }
            if (gameModel.getUserCardNumber() == 0) {
                gc.setColor(Color.WHITE);
                gc.fillRect(0, 0, 500, 500);
                gc.setColor(Color.BLACK);
                gc.drawString("Game Over! You Won!", 125, 125);
            }
            if (gameModel.getDrawingDeck().size() == 0
                    && gameModel.getComputerCardNumber() < gameModel.getUserCardNumber()) {
                gc.setColor(Color.BLACK);
                gc.fillRect(0, 0, 500, 500);
                gc.setColor(Color.WHITE);
                gc.drawString("Drawing Deck is Empty, You lost!", 125, 125);
            }
            if (gameModel.getDrawingDeck().size() == 0
                    && gameModel.getComputerCardNumber() > gameModel.getUserCardNumber()) {
                gc.setColor(Color.WHITE);
                gc.fillRect(0, 0, 500, 500);
                gc.setColor(Color.BLACK);
                gc.drawString("Drawing Deck is Empty, You won!", 125, 125);
            }
            if (gameModel.getDrawingDeck().size() == 0
                    && gameModel.getComputerCardNumber() == gameModel.getUserCardNumber()) {
                gc.setColor(Color.GRAY);
                gc.fillRect(0, 0, 500, 500);
                gc.setColor(Color.BLACK);
                gc.drawString("Drawing Deck is Empty, it's a tie!", 125, 125);
            }
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(500, 500);
        }
    }
}