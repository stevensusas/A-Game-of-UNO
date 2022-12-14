package org.cis1200;

public class WildAddFourCard implements Card {

    private String number;
    private java.awt.Color color;

    public WildAddFourCard() {
        this.number = "+4";
        this.color = java.awt.Color.PINK;
    }

    public String getNumber() {
        return this.number;
    }

    public java.awt.Color getColor() {
        return this.color;
    }

    public void changeColorforUnPlayable() {
    }

    public void changeColorBack() {
    }

    public boolean checkPlayable(Card c) {
        return true;
    }
}
