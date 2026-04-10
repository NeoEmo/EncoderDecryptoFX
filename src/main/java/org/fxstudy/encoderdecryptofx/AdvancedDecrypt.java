package org.fxstudy.encoderdecryptofx;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
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

        byte[] decodeBytes = Base64.getDecoder().decode(key);
        String decodeKey = new String(decodeBytes, StandardCharsets.UTF_8);
        String[] keyParts = decodeKey.split(" ");
        int realKey = Integer.parseInt(keyParts[3]);

        List<String> alphabet = new ArrayList<>();
        alphabet.addAll(Arrays.asList(enLetters).subList(0, Integer.parseInt(keyParts[0])));
        alphabet.addAll(Arrays.asList(ruLetters).subList(0 , Integer.parseInt(keyParts[1])));
        alphabet.addAll(Arrays.asList(grLetters).subList(0, Integer.parseInt(keyParts[2])));
        alphabet.addAll(Arrays.asList(plLetters));
        alphabet.addAll(Arrays.asList(grLetters).subList(Integer.parseInt(keyParts[2]), 48));
        alphabet.addAll(Arrays.asList(ruLetters).subList(Integer.parseInt(keyParts[1]), 66));
        alphabet.addAll(Arrays.asList(enLetters).subList(Integer.parseInt(keyParts[0]), 52));
        Set<String> uniqueSet = new LinkedHashSet<>(alphabet);
        List<String> uniqueList = new ArrayList<>(uniqueSet);
        String[] mixAlphabet = uniqueList.toArray(new String[0]);

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

                        /**Отладка**/
        System.out.println(realKey);
        System.out.println(key);
        System.out.println(message);
        System.out.println(Arrays.toString(mixAlphabet));
        System.out.println(Arrays.hashCode(mixAlphabet));
        System.out.println(mixAlphabet.length);
    }

    private void advancedDecryptRu() {
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

        byte[] decodeBytes = Base64.getDecoder().decode(key);
        String decodeKey = new String(decodeBytes, StandardCharsets.UTF_8);
        String[] keyParts = decodeKey.split(" ");
        int realKey = Integer.parseInt(keyParts[3]);

        List<String> alphabet = new ArrayList<>();
        alphabet.addAll(Arrays.asList(ruLetters).subList(0, Integer.parseInt(keyParts[0])));
        alphabet.addAll(Arrays.asList(enLetters).subList(0 , Integer.parseInt(keyParts[1])));
        alphabet.addAll(Arrays.asList(grLetters).subList(0, Integer.parseInt(keyParts[2])));
        alphabet.addAll(Arrays.asList(plLetters));
        alphabet.addAll(Arrays.asList(grLetters).subList(Integer.parseInt(keyParts[2]), 48));
        alphabet.addAll(Arrays.asList(enLetters).subList(Integer.parseInt(keyParts[1]), 52));
        alphabet.addAll(Arrays.asList(ruLetters).subList(Integer.parseInt(keyParts[0]), 66));
        Set<String> uniqueSet = new LinkedHashSet<>(alphabet);
        List<String> uniqueList = new ArrayList<>(uniqueSet);
        String[] mixAlphabet = uniqueList.toArray(new String[0]);

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
                        int newIndex = index - realKey;
                        while (newIndex < 0 ) newIndex += mixAlphabet.length;
                        newIndex %= mixAlphabet.length;
                        return mixAlphabet[newIndex];
                    }
                    return charString;
                })
                .toList();

        this.decryptMessage = String.join("", messageList);

        /**Отладка**/
        System.out.println(realKey);
        System.out.println(key);
        System.out.println(message);
        System.out.println(Arrays.toString(mixAlphabet));
        System.out.println(Arrays.hashCode(mixAlphabet));
        System.out.println(mixAlphabet.length);
    }

    private void fullAdvancedDecrypt(String language) {

    }

    public String getDecryptMessage() {
        return decryptMessage;
    }
}
