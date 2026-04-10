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
        if (language.equals("Английский")) {
            fullAdvancedDecryptEng();
        }
        if (language.equals("Русский")) {
            fullAdvancedDecryptRu();
        }
    }

    private void fullAdvancedDecryptEng() {
        List<String> listAlphabetEng = new BufferedReader(new InputStreamReader(Objects.requireNonNull(alphabetEng)))
                .lines()
                .toList();
        List<String> listAlphabetRu = new BufferedReader(new InputStreamReader(Objects.requireNonNull(alphabetRu)))
                .lines()
                .toList();
        List<String> listAlphabetGr = new BufferedReader(new InputStreamReader(Objects.requireNonNull(alphabetGr)))
                .lines()
                .toList();
        List<String> listAlphabetPl = new BufferedReader(new InputStreamReader(Objects.requireNonNull(alphabetPl)))
                .lines()
                .toList();
        List<String> listAlphabetEsp = new BufferedReader(new InputStreamReader(Objects.requireNonNull(alphabetEsp)))
                .lines()
                .toList();
        List<String> listAlphabetAe = new BufferedReader(new InputStreamReader(Objects.requireNonNull(alphabetAe)))
                .lines()
                .toList();
        List<String> listAlphabetAr = new BufferedReader(new InputStreamReader(Objects.requireNonNull(alphabetAr)))
                .lines()
                .toList();
        List<String> listAlphabetFr = new BufferedReader(new InputStreamReader(Objects.requireNonNull(alphabetFr)))
                .lines()
                .toList();
        List<String> listAlphabetGe = new BufferedReader(new InputStreamReader(Objects.requireNonNull(alphabetGe)))
                .lines()
                .toList();
        List<String> listAlphabetGer = new BufferedReader(new InputStreamReader(Objects.requireNonNull(alphabetGer)))
                .lines()
                .toList();
        List<String> listAlphabetIl = new BufferedReader(new InputStreamReader(Objects.requireNonNull(alphabetIl)))
                .lines()
                .toList();
        List<String> listAlphabetIta = new BufferedReader(new InputStreamReader(Objects.requireNonNull(alphabetIta)))
                .lines()
                .toList();
        List<String> listAlphabetKr = new BufferedReader(new InputStreamReader(Objects.requireNonNull(alphabetKr)))
                .lines()
                .toList();
        List<String> listAlphabetTr = new BufferedReader(new InputStreamReader(Objects.requireNonNull(alphabetTr)))
                .lines()
                .toList();
        List<String> listAlphabetUa = new BufferedReader(new InputStreamReader(Objects.requireNonNull(alphabetUa)))
                .lines()
                .toList();

        String[] enLetters = listAlphabetEng.getFirst().split(" ");
        String[] ruLetters = listAlphabetRu.getFirst().split(" ");
        String[] grLetters = listAlphabetGr.getFirst().split(" ");
        String[] plLetters = listAlphabetPl.getFirst().split(" ");
        String[] espLetters = listAlphabetEsp.getFirst().split(" ");
        String[] aeLetters = listAlphabetAe.getFirst().split(" ");
        String[] arLetters = listAlphabetAr.getFirst().split(" ");
        String[] frLetters = listAlphabetFr.getFirst().split(" ");
        String[] geLetters = listAlphabetGe.getFirst().split(" ");
        String[] gerLetters = listAlphabetGer.getFirst().split(" ");
        String[] ilLetters = listAlphabetIl.getFirst().split(" ");
        String[] itaLetters = listAlphabetIta.getFirst().split(" ");
        String[] krLetters = listAlphabetKr.getFirst().split(" ");
        String[] trLetters = listAlphabetTr.getFirst().split(" ");
        String[] uaLetters = listAlphabetUa.getFirst().split(" ");

        byte[] decodeBytes = Base64.getDecoder().decode(key);
        String decodeKey = new String(decodeBytes, StandardCharsets.UTF_8);
        String[] keyParts = decodeKey.split(" ");
        int realKey = Integer.parseInt(keyParts[14]);

        List<String> result = new ArrayList<>();
        result.addAll(Arrays.asList(enLetters).subList(0, Integer.parseInt(keyParts[0])));
        result.addAll(Arrays.asList(ruLetters).subList(0, Integer.parseInt(keyParts[1])));
        result.addAll(Arrays.asList(grLetters).subList(0, Integer.parseInt(keyParts[2])));
        result.addAll(Arrays.asList(plLetters).subList(0, Integer.parseInt(keyParts[3])));
        result.addAll(Arrays.asList(espLetters).subList(0, Integer.parseInt(keyParts[4])));
        result.addAll(Arrays.asList(aeLetters).subList(0, Integer.parseInt(keyParts[5])));
        result.addAll(Arrays.asList(arLetters).subList(0, Integer.parseInt(keyParts[6])));
        result.addAll(Arrays.asList(frLetters).subList(0, Integer.parseInt(keyParts[7])));
        result.addAll(Arrays.asList(geLetters).subList(0, Integer.parseInt(keyParts[8])));
        result.addAll(Arrays.asList(gerLetters).subList(0, Integer.parseInt(keyParts[9])));
        result.addAll(Arrays.asList(ilLetters).subList(0, Integer.parseInt(keyParts[10])));
        result.addAll(Arrays.asList(itaLetters).subList(0, Integer.parseInt(keyParts[11])));
        result.addAll(Arrays.asList(krLetters).subList(0, Integer.parseInt(keyParts[12])));
        result.addAll(Arrays.asList(trLetters).subList(0, Integer.parseInt(keyParts[13])));
        result.addAll(Arrays.asList(uaLetters));
        result.addAll(Arrays.asList(trLetters).subList(Integer.parseInt(keyParts[13]), 58));
        result.addAll(Arrays.asList(krLetters).subList(Integer.parseInt(keyParts[12]), 40));
        result.addAll(Arrays.asList(itaLetters).subList(Integer.parseInt(keyParts[11]), 42));
        result.addAll(Arrays.asList(ilLetters).subList(Integer.parseInt(keyParts[10]), 22));
        result.addAll(Arrays.asList(gerLetters).subList(Integer.parseInt(keyParts[9]), 52));
        result.addAll(Arrays.asList(geLetters).subList(Integer.parseInt(keyParts[8]), 33));
        result.addAll(Arrays.asList(frLetters).subList(Integer.parseInt(keyParts[7]), 52));
        result.addAll(Arrays.asList(arLetters).subList(Integer.parseInt(keyParts[6]), 76));
        result.addAll(Arrays.asList(aeLetters).subList(Integer.parseInt(keyParts[5]), 28));
        result.addAll(Arrays.asList(espLetters).subList(Integer.parseInt(keyParts[4]), 54));
        result.addAll(Arrays.asList(plLetters).subList(Integer.parseInt(keyParts[3]), 70));
        result.addAll(Arrays.asList(grLetters).subList(Integer.parseInt(keyParts[2]), 48));
        result.addAll(Arrays.asList(ruLetters).subList(Integer.parseInt(keyParts[1]), 66));
        result.addAll(Arrays.asList(enLetters).subList(Integer.parseInt(keyParts[0]), 52));
        Set<String> uniqueSet = new LinkedHashSet<>(result);
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

    private void fullAdvancedDecryptRu() {
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
        List<String> listAlphabetEsp = new BufferedReader(new InputStreamReader(Objects.requireNonNull(alphabetEsp)))
                .lines()
                .toList();
        List<String> listAlphabetAe = new BufferedReader(new InputStreamReader(Objects.requireNonNull(alphabetAe)))
                .lines()
                .toList();
        List<String> listAlphabetAr = new BufferedReader(new InputStreamReader(Objects.requireNonNull(alphabetAr)))
                .lines()
                .toList();
        List<String> listAlphabetFr = new BufferedReader(new InputStreamReader(Objects.requireNonNull(alphabetFr)))
                .lines()
                .toList();
        List<String> listAlphabetGe = new BufferedReader(new InputStreamReader(Objects.requireNonNull(alphabetGe)))
                .lines()
                .toList();
        List<String> listAlphabetGer = new BufferedReader(new InputStreamReader(Objects.requireNonNull(alphabetGer)))
                .lines()
                .toList();
        List<String> listAlphabetIl = new BufferedReader(new InputStreamReader(Objects.requireNonNull(alphabetIl)))
                .lines()
                .toList();
        List<String> listAlphabetIta = new BufferedReader(new InputStreamReader(Objects.requireNonNull(alphabetIta)))
                .lines()
                .toList();
        List<String> listAlphabetKr = new BufferedReader(new InputStreamReader(Objects.requireNonNull(alphabetKr)))
                .lines()
                .toList();
        List<String> listAlphabetTr = new BufferedReader(new InputStreamReader(Objects.requireNonNull(alphabetTr)))
                .lines()
                .toList();
        List<String> listAlphabetUa = new BufferedReader(new InputStreamReader(Objects.requireNonNull(alphabetUa)))
                .lines()
                .toList();

        String[] ruLetters = listAlphabetRu.getFirst().split(" ");
        String[] enLetters = listAlphabetEng.getFirst().split(" ");
        String[] grLetters = listAlphabetGr.getFirst().split(" ");
        String[] plLetters = listAlphabetPl.getFirst().split(" ");
        String[] espLetters = listAlphabetEsp.getFirst().split(" ");
        String[] aeLetters = listAlphabetAe.getFirst().split(" ");
        String[] arLetters = listAlphabetAr.getFirst().split(" ");
        String[] frLetters = listAlphabetFr.getFirst().split(" ");
        String[] geLetters = listAlphabetGe.getFirst().split(" ");
        String[] gerLetters = listAlphabetGer.getFirst().split(" ");
        String[] ilLetters = listAlphabetIl.getFirst().split(" ");
        String[] itaLetters = listAlphabetIta.getFirst().split(" ");
        String[] krLetters = listAlphabetKr.getFirst().split(" ");
        String[] trLetters = listAlphabetTr.getFirst().split(" ");
        String[] uaLetters = listAlphabetUa.getFirst().split(" ");

        byte[] decodeBytes = Base64.getDecoder().decode(key);
        String decodeKey = new String(decodeBytes, StandardCharsets.UTF_8);
        String[] keyParts = decodeKey.split(" ");
        int realKey = Integer.parseInt(keyParts[14]);

        List<String> result = new ArrayList<>();
        result.addAll(Arrays.asList(ruLetters).subList(0, Integer.parseInt(keyParts[0])));
        result.addAll(Arrays.asList(enLetters).subList(0, Integer.parseInt(keyParts[1])));
        result.addAll(Arrays.asList(grLetters).subList(0, Integer.parseInt(keyParts[2])));
        result.addAll(Arrays.asList(plLetters).subList(0, Integer.parseInt(keyParts[3])));
        result.addAll(Arrays.asList(espLetters).subList(0, Integer.parseInt(keyParts[4])));
        result.addAll(Arrays.asList(aeLetters).subList(0, Integer.parseInt(keyParts[5])));
        result.addAll(Arrays.asList(arLetters).subList(0, Integer.parseInt(keyParts[6])));
        result.addAll(Arrays.asList(frLetters).subList(0, Integer.parseInt(keyParts[7])));
        result.addAll(Arrays.asList(geLetters).subList(0, Integer.parseInt(keyParts[8])));
        result.addAll(Arrays.asList(gerLetters).subList(0, Integer.parseInt(keyParts[9])));
        result.addAll(Arrays.asList(ilLetters).subList(0, Integer.parseInt(keyParts[10])));
        result.addAll(Arrays.asList(itaLetters).subList(0, Integer.parseInt(keyParts[11])));
        result.addAll(Arrays.asList(krLetters).subList(0, Integer.parseInt(keyParts[12])));
        result.addAll(Arrays.asList(trLetters).subList(0, Integer.parseInt(keyParts[13])));
        result.addAll(Arrays.asList(uaLetters));
        result.addAll(Arrays.asList(trLetters).subList(Integer.parseInt(keyParts[13]), 58));
        result.addAll(Arrays.asList(krLetters).subList(Integer.parseInt(keyParts[12]), 40));
        result.addAll(Arrays.asList(itaLetters).subList(Integer.parseInt(keyParts[11]), 42));
        result.addAll(Arrays.asList(ilLetters).subList(Integer.parseInt(keyParts[10]), 22));
        result.addAll(Arrays.asList(gerLetters).subList(Integer.parseInt(keyParts[9]), 52));
        result.addAll(Arrays.asList(geLetters).subList(Integer.parseInt(keyParts[8]), 33));
        result.addAll(Arrays.asList(frLetters).subList(Integer.parseInt(keyParts[7]), 52));
        result.addAll(Arrays.asList(arLetters).subList(Integer.parseInt(keyParts[6]), 76));
        result.addAll(Arrays.asList(aeLetters).subList(Integer.parseInt(keyParts[5]), 28));
        result.addAll(Arrays.asList(espLetters).subList(Integer.parseInt(keyParts[4]), 54));
        result.addAll(Arrays.asList(plLetters).subList(Integer.parseInt(keyParts[3]), 70));
        result.addAll(Arrays.asList(grLetters).subList(Integer.parseInt(keyParts[2]), 48));
        result.addAll(Arrays.asList(enLetters).subList(Integer.parseInt(keyParts[1]), 52));
        result.addAll(Arrays.asList(ruLetters).subList(Integer.parseInt(keyParts[0]), 66));
        Set<String> uniqueSet = new LinkedHashSet<>(result);
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

    public String getDecryptMessage() {
        return decryptMessage;
    }
}
