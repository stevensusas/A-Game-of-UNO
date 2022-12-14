package org.cis1200;

public class NumberCard implements Card {
    private String number;
    private java.awt.Color color;
    private java.awt.Color colorbuffer;

    NumberCard(String number, java.awt.Color color) {
        this.number = number;
        this.color = color;
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
        if (c.getNumber().equals("+4") | c.getNumber().equals("+2")) {
            return false;
        }
        if (c.getColor().equals(java.awt.Color.PINK)) {
            return true;
        }
        return (c.getColor().equals(color) || c.getNumber().equals(number));
    }
}
