package org.cis1200;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class Game {

    public static void main(String[] args) {
        JFrame frame = new JFrame("UNO Game");

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 3));
        frame.getContentPane().add(panel);
        JOptionPane.showMessageDialog(
                null, "Welcome to UNO! This game is a " +
                        "classical implementation of the popular " +
                        "card game, UNO. If you have never played UNO\n" +
                        "before, I would recommend looking the " +
                        "game up to become more familiarized with the game.\n" +
                        "\n" +
                        "There are many versions of UNO rules out there, " +
                        "but the general rule that this game implements is:" +
                        " Try to deplete the cards that you have,\n" +
                        "cause you win when you deplete your cards! Cards" +
                        " with the same color or number can be played against each other.\n" +
                        "Wild cards can be played against any other card except for " +
                        "the addition cards. When a player " +
                        "plays an addition card, the other player\n" +
                        "must also respond with an addition card, " +
                        "other wise he has to " +
                        "draw the amount of cards specified by " +
                        "the addition card. The wild add four card\n" +
                        "can be played against any other cards, while the add two " +
                        "cards need to be played against addition " +
                        "cards or cards of the same color.\n" +
                        "Drawing as punishment for being unable to respond to an " +
                        "addition card will be followed by a reset" +
                        " of the current card to a wild card.\n" +
                        "\n" +
                        "To play a card, simply click on that card. Cards that cannot be" +
                        " played against the current card are marked in gray and cannot\n" +
                        "be played. When it's your turn to play, you can either play a " +
                        "card, or draw card(s). After you draw, " +
                        "you can choose to play or pass.\n" +
                        "Note that you cannot pass before either playing or drawing. " +
                        "The game will end when either one player " +
                        "deplete their deck, or when\n" +
                        "the drawing deck is depleted. In that case, the side with " +
                        "less cards win. If both sides have equal " +
                        "amount of cards, it is a tie.\n" +
                        "\n" +
                        "If at any point, you want to save the game" +
                        " and load the game later, " +
                        "you can press the \"Save\" and \"Load\" buttons." +
                        " You can also reset the game\n" +
                        "at any time. Good luck!",
                "Welcome to UNO!", JOptionPane.INFORMATION_MESSAGE);

        Initialize main = new Initialize();

        Initialize.CurrentCard currentCard = main.new CurrentCard();
        panel.add(currentCard);
        GameModel gameModel = main.getGame();
        Initialize.AIDeck aiDeck = main.new AIDeck();
        panel.add(aiDeck);
        JPanel userdeck = new JPanel();
        panel.add(userdeck);
        JPanel notificationpanel = new JPanel();
        Initialize.NotificationPanel notificationWidget = main.new NotificationPanel();
        notificationpanel.add(notificationWidget);
        for (int i = 0; i < gameModel.getUserCardNumber(); i++) {
            Initialize.DeckCard deck = main.new DeckCard(main.getGame().getUserDeck().get(i));
            userdeck.add(deck);
            deck.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                        if (!(gameModel.over() || !gameModel.getUserDeck().contains(deck.getCard()))
                                && deck.checkPlayable()) {
                            gameModel.play(deck.getCard());
                            deck.repaint();
                            currentCard.repaint();
                            try {
                                gameModel.play(gameModel.getCardtoPlay());
                                aiDeck.repaint();
                            } catch (NullPointerException x) {
                                gameModel.pass();
                                aiDeck.repaint();
                            }
                            currentCard.repaint();
                            aiDeck.repaint();
                            panel.revalidate();
                            userdeck.revalidate();
                            notificationWidget.repaint();
                        }
                    }

                @Override
                public void mousePressed(MouseEvent e) {
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                        if (!(gameModel.over() || !gameModel.getUserDeck().contains(deck.getCard()))
                                && !deck.checkPlayable()) {
                            deck.getCard().changeColorforUnPlayable();
                        }
                        deck.repaint();
                    }

                @Override
                public void mouseExited(MouseEvent e) {
                        if (!(gameModel.over() || !gameModel.getUserDeck().contains(deck.getCard()))
                                && !deck.checkPlayable()) {
                            deck.getCard().changeColorBack();
                        }
                        deck.repaint();
                }
            });
        }

        JButton draw = new JButton("Draw");
        panel.add(draw);
        draw.addActionListener(e -> {
            if (!gameModel.over()) {
                try {
                    LinkedList<Card> drawn = gameModel.draw(gameModel.getUserDeckUnencapsulated());
                    for (Card c : drawn) {
                        Initialize.DeckCard deck = main.new DeckCard(c);
                        userdeck.add(deck);
                        notificationWidget.repaint();
                        deck.addMouseListener(new MouseListener() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                    if (!(gameModel.over() || !gameModel.getUserDeckUnencapsulated()
                                            .contains(deck.getCard())) && deck.checkPlayable()) {
                                        gameModel.play(deck.getCard());
                                        deck.repaint();
                                        currentCard.repaint();
                                        try {
                                            gameModel.play(gameModel.getCardtoPlay());
                                            aiDeck.repaint();
                                        } catch (NullPointerException x) {
                                            gameModel.pass();
                                            aiDeck.repaint();
                                        } catch (IllegalArgumentException h) {
                                            notificationpanel.repaint();
                                        }
                                        currentCard.repaint();
                                        aiDeck.repaint();
                                        panel.revalidate();
                                        userdeck.revalidate();
                                        notificationWidget.repaint();
                                    }
                                }

                            @Override
                            public void mousePressed(MouseEvent e) {
                            }

                            @Override
                            public void mouseReleased(MouseEvent e) {
                            }

                            @Override
                            public void mouseEntered(MouseEvent e) {
                                    if (!(gameModel.over()
                                            || !gameModel.getUserDeck().contains(deck.getCard()))
                                            && !deck.checkPlayable()) {
                                        deck.getCard().changeColorforUnPlayable();
                                    }
                                    deck.repaint();
                                }

                            @Override
                            public void mouseExited(MouseEvent e) {
                                    if (!(gameModel.over()
                                            || !gameModel.getUserDeck().contains(deck.getCard()))
                                            && !deck.checkPlayable()) {
                                        deck.getCard().changeColorBack();
                                    }
                                    deck.repaint();
                                }
                        }
                        );
                        currentCard.repaint();
                        panel.revalidate();
                        userdeck.revalidate();
                    }
                } catch (IllegalArgumentException g) {
                    notificationWidget.repaint();
                }
            }
        }
        );

        JButton pass = new JButton("Pass");
        panel.add(pass);
        pass.addActionListener(e -> {
            if (!(gameModel.over()) && !gameModel.getDrawn()) {
                JOptionPane.showMessageDialog(
                        pass, "You cannot pass before you draw!", "Error", JOptionPane.ERROR_MESSAGE
                );
            } else {
                gameModel.pass();
                try {
                    gameModel.play(gameModel.getCardtoPlay());
                    aiDeck.repaint();
                    currentCard.repaint();
                } catch (NullPointerException x) {
                    gameModel.pass();
                    aiDeck.repaint();
                    currentCard.repaint();
                } catch (IllegalArgumentException g) {
                    notificationWidget.repaint();
                }
                aiDeck.repaint();
                currentCard.repaint();
                panel.revalidate();
                userdeck.revalidate();
                notificationWidget.repaint();
            }
        }
        );
        panel.add(notificationpanel);
        JButton reset = new JButton("Reset");
        panel.add(reset);
        reset.addActionListener(e -> {
            gameModel.reset();
            aiDeck.repaint();
            userdeck.removeAll();
            userdeck.revalidate();
            userdeck.repaint();

            for (int i = 0; i < gameModel.getUserCardNumber(); i++) {
                Initialize.DeckCard deck = main.new DeckCard(main.getGame().getUserDeck().get(i));
                userdeck.add(deck);
                deck.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                            if (!(gameModel.over() ||
                                    !gameModel.getUserDeck().contains(deck.getCard()))
                                    && deck.checkPlayable()) {
                                gameModel.play(deck.getCard());
                                deck.repaint();
                                currentCard.repaint();
                                try {
                                    gameModel.play(gameModel.getCardtoPlay());
                                    aiDeck.repaint();
                                } catch (NullPointerException x) {
                                    gameModel.pass();
                                    aiDeck.repaint();
                                } catch (IllegalArgumentException g) {
                                    aiDeck.repaint();
                                }
                                currentCard.repaint();
                                aiDeck.repaint();
                                panel.revalidate();
                                userdeck.revalidate();
                                notificationWidget.repaint();
                            }
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                            if (!(gameModel.over()
                                    || !gameModel.getUserDeck().contains(deck.getCard()))
                                    && !deck.checkPlayable()) {
                                deck.getCard().changeColorforUnPlayable();
                            }
                            deck.repaint();
                        }

                    @Override
                    public void mouseExited(MouseEvent e) {
                            if (!(gameModel.over()
                                    || !gameModel.getUserDeck().contains(deck.getCard()))
                                    && !deck.checkPlayable()) {
                                deck.getCard().changeColorBack();
                            }
                            deck.repaint();
                        }
                });
            }
            currentCard.repaint();
            notificationWidget.repaint();
            panel.revalidate();
        });
        JButton save = new JButton("Save");
        save.addActionListener(e -> {
            gameModel.writeGameToFile("Files/SavedGame.csv", false);
        });
        panel.add(save);

        JButton load = new JButton("Load");
        load.addActionListener(e -> {
            try {
                gameModel.readGameToFile();
            } catch (NoSuchElementException g) {
                JOptionPane.showMessageDialog(
                        load, "You have not saved a game yet!", "Error", JOptionPane.ERROR_MESSAGE
                );
            }
            aiDeck.repaint();
            userdeck.removeAll();
            userdeck.revalidate();
            userdeck.repaint();
            for (int i = 0; i < gameModel.getUserCardNumber(); i++) {
                Initialize.DeckCard deck = main.new DeckCard(main.getGame().getUserDeck().get(i));
                userdeck.add(deck);
                deck.addMouseListener(new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                            if (!(gameModel.over()
                                    || !gameModel.getUserDeck().contains(deck.getCard()))
                                    && deck.checkPlayable()) {
                                gameModel.play(deck.getCard());
                                deck.repaint();
                                currentCard.repaint();
                                try {
                                    gameModel.play(gameModel.getCardtoPlay());
                                    aiDeck.repaint();
                                } catch (NullPointerException x) {
                                    gameModel.pass();
                                    aiDeck.repaint();
                                } catch (IllegalArgumentException g) {
                                    aiDeck.repaint();
                                }
                                currentCard.repaint();
                                aiDeck.repaint();
                                panel.revalidate();
                                userdeck.revalidate();
                                notificationWidget.repaint();
                            }
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                            if (!(gameModel.over()
                                    || !gameModel.getUserDeck().contains(deck.getCard()))
                                    && !deck.checkPlayable()) {
                                deck.getCard().changeColorforUnPlayable();
                            }
                            deck.repaint();
                        }

                    @Override
                    public void mouseExited(MouseEvent e) {
                            if (!(gameModel.over()
                                    || !gameModel.getUserDeck().contains(deck.getCard()))
                                    && !deck.checkPlayable()) {
                                deck.getCard().changeColorBack();
                            }
                            deck.repaint();
                        }
                });
            }
            currentCard.repaint();
            notificationWidget.repaint();
            panel.revalidate();

        });
        panel.add(load);
        frame.requestFocusInWindow();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
