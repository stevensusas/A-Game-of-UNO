package org.cis1200;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class UNOGameStateIterator implements Iterator<String> {

    private BufferedReader bufferedreader;
    private String line;

    public UNOGameStateIterator(BufferedReader reader) {
        if (reader == null) {
            throw new IllegalArgumentException();
        }
        bufferedreader = reader;
        try {
            line = bufferedreader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public UNOGameStateIterator() {
        this(fileToReader("Files/GameState.csv"));
    }

    public static BufferedReader fileToReader(String s) {
        try {
            FileReader reader = new FileReader("Files/SavedGame.csv");
            return new BufferedReader(reader);
        } catch (FileNotFoundException | NullPointerException e) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public boolean hasNext() {
        if (line == null) {
            try {
                bufferedreader.close();
                return false;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return true;
    }

    @Override
    public String next() {
        if (line == null) {
            throw new NoSuchElementException();
        }
        String nextline = line;
        try {
            line = bufferedreader.readLine();
        } catch (IOException e) {
            line = null;
        }
        return nextline;
    }
}
