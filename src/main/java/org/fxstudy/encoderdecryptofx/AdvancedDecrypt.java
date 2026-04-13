package org.fxstudy.encoderdecryptofx;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.IntStream;

public class AdvancedDecrypt {
    private String message;
    private String key;
    private String decryptMessage;

    InputStream alphabetRu = getClass().getResourceAsStream("/org/fxstudy/encoderdecryptofx/AlphabetRU");
    InputStream alphabetEng = getClass().getResourceAsStream("/org/fxstudy/encoderdecryptofx/AlphabetENG");
    InputStream alphabetEsp = getClass().getResourceAsStream("/org/fxstudy/encoderdecryptofx/AlphabetESP");
    InputStream alphabetAe = getClass().getResourceAsStream("/org/fxstudy/encoderdecryptofx/AlphabetAE");
    InputStream alphabetAr = getClass().getResourceAsStream("/org/fxstudy/encoderdecryptofx/AlphabetAR");
    InputStream alphabetFr = getClass().getResourceAsStream("/org/fxstudy/encoderdecryptofx/AlphabetFR");
    InputStream alphabetGe = getClass().getResourceAsStream("/org/fxstudy/encoderdecryptofx/AlphabetGE");
    InputStream alphabetGer = getClass().getResourceAsStream("/org/fxstudy/encoderdecryptofx/AlphabetGER");
    InputStream alphabetGr = getClass().getResourceAsStream("/org/fxstudy/encoderdecryptofx/AlphabetGR");
    InputStream alphabetIl = getClass().getResourceAsStream("/org/fxstudy/encoderdecryptofx/AlphabetIL");
    InputStream alphabetIta = getClass().getResourceAsStream("/org/fxstudy/encoderdecryptofx/AlphabetITA");
    InputStream alphabetKr = getClass().getResourceAsStream("/org/fxstudy/encoderdecryptofx/AlphabetKR");
    InputStream alphabetPl = getClass().getResourceAsStream("/org/fxstudy/encoderdecryptofx/AlphabetPL");
    InputStream alphabetTr = getClass().getResourceAsStream("/org/fxstudy/encoderdecryptofx/AlphabetTR");
    InputStream alphabetUa = getClass().getResourceAsStream("/org/fxstudy/encoderdecryptofx/AlphabetUA");

    public void start(String language, int select, String message, String key) {
        byte[] decodeBytes = Base64.getDecoder().decode(message);
        this.message = new String(decodeBytes, StandardCharsets.UTF_8);
        this.key = key;
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
        if (language.equals("Английский")) {
            advancedDecryptEng();
        }
        if (language.equals("Русский")) {
            advancedDecryptRu();
        }
    }

    private void advancedDecryptEng() {

        byte[] decodeBytes = Base64.getDecoder().decode(key);
        String decodeKey = new String(decodeBytes, StandardCharsets.UTF_8);
        String[] keyParts = decodeKey.split(" ");
        int realKey = Integer.parseInt(keyParts[10]);

        CreateAlphabet createAlphabet = new CreateAlphabet();
        CreateAlphabet.MixResult mr = createAlphabet.runDecodeKey(keyParts);
        String[] mixAlphabet = mr.mixAlphabet();

        List<String> messageList = message.chars()
                .mapToObj(character -> (char) character)
                .map(currentChar -> {
                    String charString = String.valueOf(currentChar);
                    if (currentChar == ' ') return " ";

                    int index = IntStream.range(0, mixAlphabet.length)
                            .filter(i -> mixAlphabet[i].equals(charString))
                            .findFirst()
                            .orElse(-1);

                    if (index != -1) {
                        int newIndex = (index - realKey + mixAlphabet.length) % mixAlphabet.length;
                        return mixAlphabet[newIndex];
                    }
                    return charString;
                })
                .toList();

        this.decryptMessage = String.join("", messageList);

    }

    private void advancedDecryptRu() {
        byte[] decodeBytes = Base64.getDecoder().decode(key);
        String decodeKey = new String(decodeBytes, StandardCharsets.UTF_8);
        String[] keyParts = decodeKey.split(" ");
        int realKey = Integer.parseInt(keyParts[10]);

        CreateAlphabet createAlphabet = new CreateAlphabet();
        CreateAlphabet.MixResult mr = createAlphabet.runDecodeKey(keyParts);
        String[] mixAlphabet = mr.mixAlphabet();

        List<String> messageList = message.chars()
                .mapToObj(character -> (char) character)
                .map(currentChar -> {
                    String charString = String.valueOf(currentChar);
                    if (currentChar == ' ') return " ";

                    int index = IntStream.range(0, mixAlphabet.length)
                            .filter(i -> mixAlphabet[i].equals(charString))
                            .findFirst()
                            .orElse(-1);

                    if (index != -1) {
                        int newIndex = (index - realKey + mixAlphabet.length) % mixAlphabet.length;
                        return mixAlphabet[newIndex];
                    }
                    return charString;
                })
                .toList();

        this.decryptMessage = String.join("", messageList);
    }

    private void fullAdvancedDecrypt(String language) {
        if (language.equals("Английский")) {
            fullAdvancedDecryptEng();
        }
        if (language.equals("Русский")) {
            fullAdvancedDecryptRu();
        }
    }

    private void fullAdvancedDecryptEng() {
        byte[] decodeBytes = Base64.getDecoder().decode(key);
        String decodeKey = new String(decodeBytes, StandardCharsets.UTF_8);
        String[] keyParts = decodeKey.split(" ");
        int realKey = Integer.parseInt(keyParts[32]);

        CreateAlphabet createAlphabet = new CreateAlphabet();
        CreateAlphabet.MixResult mr = createAlphabet.runDecodeKey(keyParts);
        String[] mixAlphabet = mr.mixAlphabet();

        List<String> messageList = message.chars()
                .mapToObj(character -> (char) character)
                .map(currentChar -> {
                    String charString = String.valueOf(currentChar);
                    if (currentChar == ' ') return " ";

                    int index = IntStream.range(0, mixAlphabet.length)
                            .filter(i -> mixAlphabet[i].equals(charString))
                            .findFirst()
                            .orElse(-1);

                    if (index != -1) {
                        int newIndex = (index - realKey + mixAlphabet.length) % mixAlphabet.length;
                        return mixAlphabet[newIndex];
                    }
                    return charString;
                })
                .toList();

        this.decryptMessage = String.join("", messageList);
    }

    private void fullAdvancedDecryptRu() {
        byte[] decodeBytes = Base64.getDecoder().decode(key);
        String decodeKey = new String(decodeBytes, StandardCharsets.UTF_8);
        String[] keyParts = decodeKey.split(" ");
        int realKey = Integer.parseInt(keyParts[32]);

        CreateAlphabet createAlphabet = new CreateAlphabet();
        CreateAlphabet.MixResult mr = createAlphabet.runDecodeKey(keyParts);
        String[] mixAlphabet = mr.mixAlphabet();

        List<String> messageList = message.chars()
                .mapToObj(character -> (char) character)
                .map(currentChar -> {
                    String charString = String.valueOf(currentChar);
                    if (currentChar == ' ') return " ";

                    int index = IntStream.range(0, mixAlphabet.length)
                            .filter(i -> mixAlphabet[i].equals(charString))
                            .findFirst()
                            .orElse(-1);

                    if (index != -1) {
                        int newIndex = (index - realKey + mixAlphabet.length) % mixAlphabet.length;
                        return mixAlphabet[newIndex];
                    }
                    return charString;
                })
                .toList();

        this.decryptMessage = String.join("", messageList);
    }

    public String getDecryptMessage() {
        return decryptMessage;
    }
}
