package org.fxstudy.encoderdecryptofx;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SimpleEncode {
    private String message;
    public String encodeMessage;
    public String key;

    InputStream alphabetRu = getClass().getResourceAsStream("/org/fxstudy/encoderdecryptofx/AlphabetRU");
    InputStream alphabetEng = getClass().getResourceAsStream("/org/fxstudy/encoderdecryptofx/AlphabetENG");

    public void start(String language, String message) {
        this.message = message;
        simpleEncode(language);
    }

    private void simpleEncode(String language) {
        if(language.equals("Английский")) {
            simpleEncodeEng();
        }
        if(language.equals("Русский")) {
            simpleEncodeRu();
        }
    }

    private void simpleEncodeEng() {
        KeyGenerator keyGenerator = new KeyGenerator();
        keyGenerator.start(1);
        String key = keyGenerator.getKey();
        char realKey = key.charAt(2);
        List<String> listAlphabetEng = new BufferedReader(new InputStreamReader(Objects.requireNonNull(alphabetEng)))
                .lines()
                .toList();
        String[] engLetters = listAlphabetEng.getFirst().split(" ");
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
                        int newIndex = (index + realKey) % 52;
                        return engLetters[newIndex];
                    }
                    return charString;
                })
                .collect(Collectors.toList());

        this.encodeMessage = messageList.stream().collect(Collectors.joining());
        this.key = key;
    }

    private void simpleEncodeRu() {
        KeyGenerator keyGenerator = new KeyGenerator();
        keyGenerator.start(1);
        String key = keyGenerator.getKey();
        char realKey = key.charAt(2);
        List<String> listAlphabetRu = new BufferedReader(new InputStreamReader(Objects.requireNonNull(alphabetRu)))
                .lines()
                .toList();
        String[] ruLetters = listAlphabetRu.getFirst().split(" ");
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
                        int newIndex = (index + realKey) % 66;
                        return ruLetters[newIndex];
                    }
                    return charString;
                })
                .collect(Collectors.toList());

        this.encodeMessage = messageList.stream().collect(Collectors.joining());
        this.key = key;
    }
}
