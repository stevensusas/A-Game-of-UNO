package org.cis1200;

import java.awt.*;

public interface Card {
    String getNumber();

    public void changeColorforUnPlayable();

    Color getColor();

    void changeColorBack();

    boolean checkPlayable(Card c);
}
