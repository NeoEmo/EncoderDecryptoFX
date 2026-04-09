package org.fxstudy.encoderdecryptofx;

public class AdvancedEncode {
    private String message;


    public void start(String language, int select, String message) {
        this.message = message;
        switch (select) {
            case 2 -> {
                advancedEncode(language);
            }
            case 3 -> {
                fullAdvancedEncode(language);
            }
        }
    }

    private void advancedEncode(String language) {

    }

    private void fullAdvancedEncode(String language) {

    }
}
