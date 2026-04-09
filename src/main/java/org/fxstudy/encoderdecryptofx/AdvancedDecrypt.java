package org.fxstudy.encoderdecryptofx;

public class AdvancedDecrypt {
    private String message;

    public void start(String language, int select, String message) {
        this.message = message;
        switch (select) {
            case 2 -> {
                advancedDecrypt(language);
            }
            case 3 -> {
                fullAdvancedDecrypt(language);
            }
        }
    }

    private void advancedDecrypt(String language) {

    }

    private void fullAdvancedDecrypt(String language) {

    }
}
