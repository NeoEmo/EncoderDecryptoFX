package org.fxstudy.encoderdecryptofx;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AdvancedEncode {
    private String message;
    public String encodeMessage;
    public String key;

    public void start(String language, String message) {
        this.message = message;
        advancedEncode(language);
    }

    public void fullStart(String language, String message) {
        this.message = message;
        fullAdvancedEncode(language);
    }

    private void advancedEncode(String language) {
        if (language.equals("Английский")) {
            advancedEncodeEng();
        }
        if (language.equals("Русский")) {
            advancedEncodeRu();
        }
    }

    private void advancedEncodeEng() {
        CreateAlphabet createAlphabet = new CreateAlphabet();
        CreateAlphabet.MixResult mr = createAlphabet.runSimpleMix(1);
        String[] advancedAlphabetEng = mr.mixAlphabet();
        ArrayList<Integer> params = new ArrayList<>();
        params.add(mr.indexes().length);
        params.add(mr.parameters().length);

        for (int i : mr.indexes()) {
            params.add(i);
        }

        for (int i : mr.parameters()) {
            params.add(i);
        }

        params.add(mr.mixAlphabet().length);

        KeyGenerator keyGenerator = new KeyGenerator();
        keyGenerator.advancedStart(params);
        String key = keyGenerator.getKey();
        byte[] decodeBytes = Base64.getDecoder().decode(key);
        String decodeKey = new String(decodeBytes, StandardCharsets.UTF_8);
        String[] keyParts = decodeKey.split(" ");
        int realKey = Integer.parseInt(keyParts[10]);

        List<String> messageList = message.chars()
                .mapToObj(character -> (char) character)
                .map(currentChar -> {
                    String charString = String.valueOf(currentChar);
                    if (currentChar == ' ') return " ";

                    int index = IntStream.range(0, advancedAlphabetEng.length)
                            .filter(i -> advancedAlphabetEng[i].equals(charString))
                            .findFirst()
                            .orElse(-1);

                    if (index != -1) {
                        int newIndex = (index + realKey) % advancedAlphabetEng.length;
                        return advancedAlphabetEng[newIndex];
                    }
                    return charString;
                })
                .toList();

        String encodeMessage = messageList.stream().collect(Collectors.joining());

        this.encodeMessage = Base64.getEncoder().encodeToString(encodeMessage.getBytes(StandardCharsets.UTF_8));
        this.key = key;
    }

    private void advancedEncodeRu() {
        CreateAlphabet createAlphabet = new CreateAlphabet();
        CreateAlphabet.MixResult mr = createAlphabet.runSimpleMix(2);
        String[] advancedAlphabetRu = mr.mixAlphabet();
        ArrayList<Integer> params = new ArrayList<>();
        params.add(mr.indexes().length);
        params.add(mr.parameters().length);

        for (int i : mr.indexes()) {
            params.add(i);
        }

        for (int i : mr.parameters()) {
            params.add(i);
        }

        params.add(mr.mixAlphabet().length);

        KeyGenerator keyGenerator = new KeyGenerator();
        keyGenerator.advancedStart(params);
        String key = keyGenerator.getKey();
        byte[] decodeBytes = Base64.getDecoder().decode(key);
        String decodeKey = new String(decodeBytes, StandardCharsets.UTF_8);
        String[] keyParts = decodeKey.split(" ");
        int realKey = Integer.parseInt(keyParts[10]);

        List<String> messageList = message.chars()
                .mapToObj(character -> (char) character)
                .map(currentChar -> {
                    String charString = String.valueOf(currentChar);
                    if (currentChar == ' ') return " ";

                    int index = IntStream.range(0, advancedAlphabetRu.length)
                            .filter(i -> advancedAlphabetRu[i].equals(charString))
                            .findFirst()
                            .orElse(-1);

                    if (index != -1) {
                        int newIndex = (index + realKey) % advancedAlphabetRu.length;
                        return advancedAlphabetRu[newIndex];
                    }
                    return charString;
                })
                .toList();

        String encodeMessage = messageList.stream().collect(Collectors.joining());

        this.encodeMessage = Base64.getEncoder().encodeToString(encodeMessage.getBytes(StandardCharsets.UTF_8));
        this.key = key;
    }

    private void fullAdvancedEncode(String language) {
        if (language.equals("Английский")) {
            fullAdvancedEncodeEng();
        }
        if (language.equals("Русский")) {
            fullAdvancedEncodeRu();
        }
    }

    private void fullAdvancedEncodeEng() {
        CreateAlphabet createAlphabet = new CreateAlphabet();
        CreateAlphabet.MixResult mr = createAlphabet.runFullMix(1);
        String[] fullAdvancedAlphabetEng = mr.mixAlphabet();
        ArrayList<Integer> params = new ArrayList<>();
        params.add(mr.indexes().length);
        params.add(mr.parameters().length);

        for (int i : mr.indexes()) {
            params.add(i);
        }

        for (int i : mr.parameters()) {
            params.add(i);
        }

        params.add(mr.mixAlphabet().length);

        KeyGenerator keyGenerator = new KeyGenerator();
        keyGenerator.fullAdvancedStart(params);
        String key = keyGenerator.getKey();
        byte[] decodeBytes = Base64.getDecoder().decode(key);
        String decodeKey = new String(decodeBytes, StandardCharsets.UTF_8);
        String[] keyParts = decodeKey.split(" ");
        int realKey = Integer.parseInt(keyParts[32]);

        List<String> messageList = message.chars()
                .mapToObj(character -> (char) character)
                .map(currentChar -> {
                    String charString = String.valueOf(currentChar);
                    if (currentChar == ' ') return " ";

                    int index = IntStream.range(0, fullAdvancedAlphabetEng.length)
                            .filter(i -> fullAdvancedAlphabetEng[i].equals(charString))
                            .findFirst()
                            .orElse(-1);

                    if (index != -1) {
                        int newIndex = (index + realKey) % fullAdvancedAlphabetEng.length;
                        return fullAdvancedAlphabetEng[newIndex];
                    }
                    return charString;
                })
                .toList();

        String encodeMessage = messageList.stream().collect(Collectors.joining());

        this.encodeMessage = Base64.getEncoder().encodeToString(encodeMessage.getBytes(StandardCharsets.UTF_8));
        this.key = key;
    }

    private void fullAdvancedEncodeRu() {
        CreateAlphabet createAlphabet = new CreateAlphabet();
        CreateAlphabet.MixResult mr = createAlphabet.runFullMix(2);
        String[] fullAdvancedAlphabetRu = mr.mixAlphabet();
        ArrayList<Integer> params = new ArrayList<>();
        params.add(mr.indexes().length);
        params.add(mr.parameters().length);

        for (int i : mr.indexes()) {
            params.add(i);
        }

        for (int i : mr.parameters()) {
            params.add(i);
        }

        params.add(mr.mixAlphabet().length);

        KeyGenerator keyGenerator = new KeyGenerator();
        keyGenerator.fullAdvancedStart(params);
        String key = keyGenerator.getKey();
        byte[] decodeBytes = Base64.getDecoder().decode(key);
        String decodeKey = new String(decodeBytes, StandardCharsets.UTF_8);
        String[] keyParts = decodeKey.split(" ");
        int realKey = Integer.parseInt(keyParts[32]);

        List<String> messageList = message.chars()
                .mapToObj(character -> (char) character)
                .map(currentChar -> {
                    String charString = String.valueOf(currentChar);
                    if (currentChar == ' ') return " ";

                    int index = IntStream.range(0, fullAdvancedAlphabetRu.length)
                            .filter(i -> fullAdvancedAlphabetRu[i].equals(charString))
                            .findFirst()
                            .orElse(-1);

                    if (index != -1) {
                        int newIndex = (index + realKey) % fullAdvancedAlphabetRu.length;
                        return fullAdvancedAlphabetRu[newIndex];
                    }
                    return charString;
                })
                .toList();

        String encodeMessage = messageList.stream().collect(Collectors.joining());

        this.encodeMessage = Base64.getEncoder().encodeToString(encodeMessage.getBytes(StandardCharsets.UTF_8));
        this.key = key;
    }
}
