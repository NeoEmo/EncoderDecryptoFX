package org.fxstudy.encoderdecryptofx;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SimpleDecrypt {
    private String message;
    private String key;
    private String decryptMessage;

    InputStream alphabetRu = getClass().getResourceAsStream("/org/fxstudy/encoderdecryptofx/AlphabetRU");
    InputStream alphabetEng = getClass().getResourceAsStream("/org/fxstudy/encoderdecryptofx/AlphabetENG");

    public void start(String language, String message, String key) {
        this.message = message;
        this.key = key;
        simpleDecrypt(language);
    }

    private void simpleDecrypt(String language) {
        if(language.equals("Английский")) {
            simpleDecryptEng();
        }
        if(language.equals("Русский")) {
            simpleDecryptRu();
        }
    }

    private void simpleDecryptEng() {
        List<String> listAlphabetEng = new BufferedReader(new InputStreamReader(Objects.requireNonNull(alphabetEng)))
                .lines()
                .toList();
        String[] engLetters = listAlphabetEng.getFirst().split(" ");
        char realKey = key.charAt(2);
        List<String> messageList = message.chars()
                .mapToObj(character -> (char) character)
                .map(currentChar -> {
                    String charString = String.valueOf(currentChar);
                    if (currentChar == ' ') return " ";

                    int index = IntStream.range(0, engLetters.length)
                            .filter(i -> engLetters[i].equals(charString))
                            .findFirst()
                            .orElse(-1);

                    if (index != -1) {
                        int newIndex = (index - realKey) % 52;
                        if (newIndex < 0) {
                            newIndex += 52;
                        }
                        return engLetters[newIndex];
                    }
                    return charString;
                })
                .collect(Collectors.toList());

        this.decryptMessage = messageList.stream().collect(Collectors.joining());
    }

    private void simpleDecryptRu() {
        List<String> listAlphabetRu = new BufferedReader(new InputStreamReader(Objects.requireNonNull(alphabetRu)))
                .lines()
                .toList();
        String[] ruLetters = listAlphabetRu.getFirst().split(" ");
        char realKey = key.charAt(2);
        List<String> messageList = message.chars()
                .mapToObj(character -> (char) character)
                .map(currentChar -> {
                    String charString = String.valueOf(currentChar);
                    if (currentChar == ' ') return " ";

                    int index = IntStream.range(0, ruLetters.length)
                            .filter(i -> ruLetters[i].equals(charString))
                            .findFirst()
                            .orElse(-1);

                    if (index != -1) {
                        int newIndex = (index - realKey) % 66;
                        if (newIndex < 0) {
                            newIndex += 66;
                        }
                        return ruLetters[newIndex];
                    }
                    return charString;
                })
                .collect(Collectors.toList());

        this.decryptMessage = messageList.stream().collect(Collectors.joining());
    }

    public String getDecryptMessage() {
        return decryptMessage;
    }
}
