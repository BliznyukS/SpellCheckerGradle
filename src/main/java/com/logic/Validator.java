package com.logic;

public class Validator {
    public boolean validateWord(String word) {
        if (word.length() >= 45 || word.contains("^[^a-zA-Z0-9]+$") || word.startsWith("'")) {
            return false;
        } else return true;
    }
}
