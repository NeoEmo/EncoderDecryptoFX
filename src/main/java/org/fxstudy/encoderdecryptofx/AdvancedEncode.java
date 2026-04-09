package org.fxstudy.encoderdecryptofx;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AdvancedEncode {
    private String message;
    public String encodeMessage;
    public String key;


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

    record MixingResult(String[] mixedAlphabet, int firstParameter, int secondParameter, int thirdParameter) {

    }

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
        if (language.equals("Английский")) {
            advancedEncodeEng();
        }
        if (language.equals("Русский")) {
            advancedEncodeRu();
        }
    }

    private void advancedEncodeEng() {
        MixingResult mr = simpleMix(1);
        String[] advancedAlphabetEng = Objects.requireNonNull(mr).mixedAlphabet();
        int[]Params = { mr.firstParameter(), mr.secondParameter(), mr.thirdParameter(), mr.mixedAlphabet.length };
        KeyGenerator keyGenerator = new KeyGenerator();
        keyGenerator.advancedStart(Params);
        String key = keyGenerator.getKey();
        byte[] decodedBytes = Base64.getDecoder().decode(key);
        String decodeKey = new String(decodedBytes, StandardCharsets.UTF_8);
        String[]keyParts = decodeKey.split(" ");
        int realKey = Integer.parseInt(keyParts[3]);

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
                        int newIndex = (index + realKey) % 52;
                        return advancedAlphabetEng[newIndex];
                    }
                    return charString;
                })
                .collect(Collectors.toList());

        String encodeMessage = messageList.stream().collect(Collectors.joining());


        this.encodeMessage = Base64.getEncoder().encodeToString(encodeMessage.getBytes(StandardCharsets.UTF_8));
        this.key = key;

        System.out.println(encodeMessage);
        System.out.println(Arrays.toString(keyParts));
        System.out.println(key);
        System.out.println(decodeKey);
        System.out.println(Arrays.toString(advancedAlphabetEng));
    }

    private void advancedEncodeRu() {
        int index = 2;
        String[] advancedAlphabetRu = simpleMix(2).mixedAlphabet();
    }

    private MixingResult simpleMix(int index) {
        switch (index) {
            case 1 -> {
                List<String> listAlphabetRu = new BufferedReader(new InputStreamReader(Objects.requireNonNull(alphabetRu)))
                        .lines()
                        .toList();
                List<String> listAlphabetEng = new BufferedReader(new InputStreamReader(Objects.requireNonNull(alphabetEng)))
                        .lines()
                        .toList();
                List<String> listAlphabetGr = new BufferedReader(new InputStreamReader(Objects.requireNonNull(alphabetGr)))
                        .lines()
                        .toList();
                List<String> listAlphabetPl = new BufferedReader(new InputStreamReader(Objects.requireNonNull(alphabetPl)))
                        .lines()
                        .toList();

                String[] ruLetters = listAlphabetRu.getFirst().split(" ");
                String[] enLetters = listAlphabetEng.getFirst().split(" ");
                String[] grLetters = listAlphabetGr.getFirst().split(" ");
                String[] plLetters = listAlphabetPl.getFirst().split(" ");

                int firstParameter = (int) (Math.random() * 52);
                int secondParameter = (int) (Math.random() * 66);
                int thirdParameter = (int) (Math.random() * 48);
                List<String> result = new ArrayList<>();
                result.addAll(Arrays.asList(enLetters).subList(0, firstParameter));
                result.addAll(Arrays.asList(ruLetters).subList(0 , secondParameter));
                result.addAll(Arrays.asList(grLetters).subList(0, thirdParameter));
                result.addAll(Arrays.asList(plLetters));
                result.addAll(Arrays.asList(grLetters).subList(thirdParameter, 48));
                result.addAll(Arrays.asList(ruLetters).subList(secondParameter, 66));
                result.addAll(Arrays.asList(enLetters).subList(firstParameter, 52));
                String[] mixAlphabet = result.toArray(new String[0]);

                return new MixingResult(mixAlphabet, firstParameter, secondParameter, thirdParameter);
            }
            case 2 -> {
                List<String> listAlphabetRu = new BufferedReader(new InputStreamReader(Objects.requireNonNull(alphabetRu)))
                        .lines()
                        .toList();
                List<String> listAlphabetEng = new BufferedReader(new InputStreamReader(Objects.requireNonNull(alphabetEng)))
                        .lines()
                        .toList();
                List<String> listAlphabetGr = new BufferedReader(new InputStreamReader(Objects.requireNonNull(alphabetGr)))
                        .lines()
                        .toList();
                List<String> listAlphabetPl = new BufferedReader(new InputStreamReader(Objects.requireNonNull(alphabetPl)))
                        .lines()
                        .toList();

                int firstParameter = (int) (Math.random() * 66);
                int secondParameter = (int) (Math.random() * 52);
                int thirdParameter = (int) (Math.random() * 48);
                int fourParameter = (int) (Math.random() * 70);
            }
            default -> throw new IllegalStateException("Unexpected value: " + index);
        }
        return null;
    }

    private void fullAdvancedEncode(String language) {

    }
}
