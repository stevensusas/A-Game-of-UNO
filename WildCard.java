package org.cis1200;

public class WildCard implements Card {
    private String number;
    private java.awt.Color color;
    private java.awt.Color colorbuffer;

    WildCard() {
        this.number = "W";
        this.color = java.awt.Color.PINK;
    }

    public String getNumber() {
        return this.number;
    }

    public java.awt.Color getColor() {
        return this.color;
    }

    public void changeColorforUnPlayable() {
        colorbuffer = this.color;
        this.color = java.awt.Color.GRAY;
    }

    public void changeColorBack() {
        this.color = colorbuffer;
        colorbuffer = null;
    }

    public boolean checkPlayable(Card c) {
        return !(c.getNumber().equals("+4") | c.getNumber().equals("+2"));
    }
}
