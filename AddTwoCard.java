package org.cis1200;

public class AddTwoCard implements Card {
    private String number;
    private java.awt.Color color;
    private java.awt.Color colorbuffer;

    public AddTwoCard(java.awt.Color color) {
        this.number = "+2";
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
        return c.getNumber().equals("+2") || c.getNumber().equals("+4")
                || c.getColor().equals(color)
                || c.getNumber().equals("W");
    }
}
