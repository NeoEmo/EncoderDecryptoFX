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

    record MixingResult(String[] mixedAlphabet, int firstParameter, int secondParameter, int thirdParameter) {}
    record MaxMixingResult(String[] maxMixedAlphabet, int firstParameter, int secondParameter, int thirdParameter,
                           int fourthParameter, int fifthParameter, int sixthParameter,
                           int seventhParameter, int eighthParameter, int ninthParameter, int tenthParameter,
                           int eleventhParameter, int twelfthParameter, int thirteenthParameter, int fourteenthParameter) {}

    public void start(String language, String message) {
        this.message = message;
        advancedEncode(language);
    }

    public void fullStart (String language, String message) {
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
                        int newIndex = (index + realKey) % advancedAlphabetEng.length;
                        return advancedAlphabetEng[newIndex];
                    }
                    return charString;
                })
                .toList();

        String encodeMessage = messageList.stream().collect(Collectors.joining());


        this.encodeMessage = Base64.getEncoder().encodeToString(encodeMessage.getBytes(StandardCharsets.UTF_8));
        this.key = key;

                    /**ОТЛАДКИ**/
        System.out.println(encodeMessage);
        System.out.println(Arrays.toString(keyParts));
        System.out.println(key);
        System.out.println(decodeKey);
        System.out.println(Arrays.toString(advancedAlphabetEng));
        System.out.println(Arrays.hashCode(advancedAlphabetEng));
        System.out.println(advancedAlphabetEng.length);
    }

    private void advancedEncodeRu() {
        MixingResult mr = simpleMix(2);
        String[] advancedAlphabetRu = Objects.requireNonNull(mr).mixedAlphabet();

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

                    /**ОТЛАДКИ**/
        System.out.println(encodeMessage);
        System.out.println(Arrays.toString(keyParts));
        System.out.println(key);
        System.out.println(decodeKey);
        System.out.println(Arrays.toString(advancedAlphabetRu));
        System.out.println(Arrays.hashCode(advancedAlphabetRu));
        System.out.println(advancedAlphabetRu.length);
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
                Set<String> uniqueSet = new LinkedHashSet<>(result);
                List<String> uniqueList = new ArrayList<>(uniqueSet);
                String[] mixAlphabet = uniqueList.toArray(new String[0]);

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

                String[] ruLetters = listAlphabetRu.getFirst().split(" ");
                String[] enLetters = listAlphabetEng.getFirst().split(" ");
                String[] grLetters = listAlphabetGr.getFirst().split(" ");
                String[] plLetters = listAlphabetPl.getFirst().split(" ");

                int firstParameter = (int) (Math.random() * 66);
                int secondParameter = (int) (Math.random() * 52);
                int thirdParameter = (int) (Math.random() * 48);
                List<String> result = new ArrayList<>();
                result.addAll(Arrays.asList(ruLetters).subList(0, firstParameter));
                result.addAll(Arrays.asList(enLetters).subList(0 , secondParameter));
                result.addAll(Arrays.asList(grLetters).subList(0, thirdParameter));
                result.addAll(Arrays.asList(plLetters));
                result.addAll(Arrays.asList(grLetters).subList(thirdParameter, 48));
                result.addAll(Arrays.asList(enLetters).subList(secondParameter, 52));
                result.addAll(Arrays.asList(ruLetters).subList(firstParameter, 66));
                Set<String> uniqueSet = new LinkedHashSet<>(result);
                List<String> uniqueList = new ArrayList<>(uniqueSet);
                String[] mixAlphabet = uniqueList.toArray(new String[0]);

                return new MixingResult(mixAlphabet, firstParameter, secondParameter, thirdParameter);
            }
            default -> throw new IllegalStateException("Unexpected value: " + index);
        }
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
        MaxMixingResult mmr = fullMix(1);
        String[] fullAdvancedAlphabetEng = Objects.requireNonNull(mmr).maxMixedAlphabet();
        int[]Params = { mmr.firstParameter(), mmr.secondParameter(), mmr.thirdParameter(), mmr.fourthParameter(),
                mmr.fifthParameter(), mmr.sixthParameter(), mmr.seventhParameter(), mmr.eighthParameter(),
                mmr.ninthParameter(), mmr.tenthParameter(), mmr.eleventhParameter(), mmr.twelfthParameter(),
                mmr.thirteenthParameter(), mmr.fourteenthParameter(), mmr.maxMixedAlphabet.length};

        KeyGenerator keyGenerator = new KeyGenerator();
        keyGenerator.fullAdvancedStart(Params);
        String key = keyGenerator.getKey();
        byte[] decodeBytes = Base64.getDecoder().decode(key);
        String decodeKey = new String(decodeBytes, StandardCharsets.UTF_8);
        String[]keyParts = decodeKey.split(" ");
        int realKey = Integer.parseInt(keyParts[14]);

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

        /**ОТЛАДКИ**/
        System.out.println(encodeMessage);
        System.out.println(Arrays.toString(keyParts));
        System.out.println(key);
        System.out.println(decodeKey);
        System.out.println(Arrays.toString(fullAdvancedAlphabetEng));
        System.out.println(Arrays.hashCode(fullAdvancedAlphabetEng));
        System.out.println(fullAdvancedAlphabetEng.length);
    }

    private void fullAdvancedEncodeRu() {
        MaxMixingResult mmr = fullMix(2);
        String[] fullAdvancedAlphabetRu = Objects.requireNonNull(mmr).maxMixedAlphabet();
        int[]Params = { mmr.firstParameter(), mmr.secondParameter(), mmr.thirdParameter(), mmr.fourthParameter(),
                mmr.fifthParameter(), mmr.sixthParameter(), mmr.seventhParameter(), mmr.eighthParameter(),
                mmr.ninthParameter(), mmr.tenthParameter(), mmr.eleventhParameter(), mmr.twelfthParameter(),
                mmr.thirteenthParameter(), mmr.fourteenthParameter(), mmr.maxMixedAlphabet.length};

        KeyGenerator keyGenerator = new KeyGenerator();
        keyGenerator.fullAdvancedStart(Params);
        String key = keyGenerator.getKey();
        byte[] decodeBytes = Base64.getDecoder().decode(key);
        String decodeKey = new String(decodeBytes, StandardCharsets.UTF_8);
        String[]keyParts = decodeKey.split(" ");
        int realKey = Integer.parseInt(keyParts[14]);

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

        /**ОТЛАДКИ**/
        System.out.println(encodeMessage);
        System.out.println(Arrays.toString(keyParts));
        System.out.println(key);
        System.out.println(decodeKey);
        System.out.println(Arrays.toString(fullAdvancedAlphabetRu));
        System.out.println(Arrays.hashCode(fullAdvancedAlphabetRu));
        System.out.println(fullAdvancedAlphabetRu.length);
    }

    private MaxMixingResult fullMix(int index) {
        switch (index) {
            case 1 -> {
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

                int firstParameter = (int) (Math.random() * 52);
                int secondParameter = (int) (Math.random() * 66);
                int thirdParameter = (int) (Math.random() * 48);
                int fourthParameter = (int) (Math.random() * 70);
                int fifthParameter = (int) (Math.random() * 54);
                int sixthParameter = (int) (Math.random() * 28);
                int seventhParameter = (int) (Math.random() * 76);
                int eighthParameter = (int) (Math.random() * 52);
                int ninthParameter = (int) (Math.random() * 33);
                int tenthParameter = (int) (Math.random() * 52);
                int eleventhParameter = (int) (Math.random() * 22);
                int twelfthParameter = (int) (Math.random() * 42);
                int thirteenthParameter = (int) (Math.random() * 40);
                int fourteenthParameter = (int) (Math.random() * 58);

                List<String> result = new ArrayList<>();
                result.addAll(Arrays.asList(enLetters).subList(0, firstParameter));
                result.addAll(Arrays.asList(ruLetters).subList(0, secondParameter));
                result.addAll(Arrays.asList(grLetters).subList(0, thirdParameter));
                result.addAll(Arrays.asList(plLetters).subList(0, fourthParameter));
                result.addAll(Arrays.asList(espLetters).subList(0, fifthParameter));
                result.addAll(Arrays.asList(aeLetters).subList(0, sixthParameter));
                result.addAll(Arrays.asList(arLetters).subList(0, seventhParameter));
                result.addAll(Arrays.asList(frLetters).subList(0, eighthParameter));
                result.addAll(Arrays.asList(geLetters).subList(0, ninthParameter));
                result.addAll(Arrays.asList(gerLetters).subList(0, tenthParameter));
                result.addAll(Arrays.asList(ilLetters).subList(0, eleventhParameter));
                result.addAll(Arrays.asList(itaLetters).subList(0, twelfthParameter));
                result.addAll(Arrays.asList(krLetters).subList(0, thirteenthParameter));
                result.addAll(Arrays.asList(trLetters).subList(0, fourteenthParameter));
                result.addAll(Arrays.asList(uaLetters));
                result.addAll(Arrays.asList(trLetters).subList(fourteenthParameter, 58));
                result.addAll(Arrays.asList(krLetters).subList(thirteenthParameter, 40));
                result.addAll(Arrays.asList(itaLetters).subList(twelfthParameter, 42));
                result.addAll(Arrays.asList(ilLetters).subList(eleventhParameter, 22));
                result.addAll(Arrays.asList(gerLetters).subList(tenthParameter, 52));
                result.addAll(Arrays.asList(geLetters).subList(ninthParameter, 33));
                result.addAll(Arrays.asList(frLetters).subList(eighthParameter, 52));
                result.addAll(Arrays.asList(arLetters).subList(seventhParameter, 76));
                result.addAll(Arrays.asList(aeLetters).subList(sixthParameter, 28));
                result.addAll(Arrays.asList(espLetters).subList(fifthParameter, 54));
                result.addAll(Arrays.asList(plLetters).subList(fourthParameter, 70));
                result.addAll(Arrays.asList(grLetters).subList(thirdParameter, 48));
                result.addAll(Arrays.asList(ruLetters).subList(secondParameter, 66));
                result.addAll(Arrays.asList(enLetters).subList(firstParameter, 52));
                Set<String> uniqueSet = new LinkedHashSet<>(result);
                List<String> uniqueList = new ArrayList<>(uniqueSet);
                String[] mixAlphabet = uniqueList.toArray(new String[0]);

                return new MaxMixingResult(mixAlphabet, firstParameter, secondParameter, thirdParameter,
                        fourthParameter, fifthParameter, sixthParameter, seventhParameter, eighthParameter,
                        ninthParameter, tenthParameter, eleventhParameter, twelfthParameter, thirteenthParameter, fourteenthParameter);
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

                int firstParameter = (int) (Math.random() * 66);
                int secondParameter = (int) (Math.random() * 52);
                int thirdParameter = (int) (Math.random() * 48);
                int fourthParameter = (int) (Math.random() * 70);
                int fifthParameter = (int) (Math.random() * 54);
                int sixthParameter = (int) (Math.random() * 28);
                int seventhParameter = (int) (Math.random() * 76);
                int eighthParameter = (int) (Math.random() * 52);
                int ninthParameter = (int) (Math.random() * 33);
                int tenthParameter = (int) (Math.random() * 52);
                int eleventhParameter = (int) (Math.random() * 22);
                int twelfthParameter = (int) (Math.random() * 42);
                int thirteenthParameter = (int) (Math.random() * 40);
                int fourteenthParameter = (int) (Math.random() * 58);

                List<String> result = new ArrayList<>();
                result.addAll(Arrays.asList(ruLetters).subList(0, firstParameter));
                result.addAll(Arrays.asList(enLetters).subList(0, secondParameter));
                result.addAll(Arrays.asList(grLetters).subList(0, thirdParameter));
                result.addAll(Arrays.asList(plLetters).subList(0, fourthParameter));
                result.addAll(Arrays.asList(espLetters).subList(0, fifthParameter));
                result.addAll(Arrays.asList(aeLetters).subList(0, sixthParameter));
                result.addAll(Arrays.asList(arLetters).subList(0, seventhParameter));
                result.addAll(Arrays.asList(frLetters).subList(0, eighthParameter));
                result.addAll(Arrays.asList(geLetters).subList(0, ninthParameter));
                result.addAll(Arrays.asList(gerLetters).subList(0, tenthParameter));
                result.addAll(Arrays.asList(ilLetters).subList(0, eleventhParameter));
                result.addAll(Arrays.asList(itaLetters).subList(0, twelfthParameter));
                result.addAll(Arrays.asList(krLetters).subList(0, thirteenthParameter));
                result.addAll(Arrays.asList(trLetters).subList(0, fourteenthParameter));
                result.addAll(Arrays.asList(uaLetters));
                result.addAll(Arrays.asList(trLetters).subList(fourteenthParameter, 58));
                result.addAll(Arrays.asList(krLetters).subList(thirteenthParameter, 40));
                result.addAll(Arrays.asList(itaLetters).subList(twelfthParameter, 42));
                result.addAll(Arrays.asList(ilLetters).subList(eleventhParameter, 22));
                result.addAll(Arrays.asList(gerLetters).subList(tenthParameter, 52));
                result.addAll(Arrays.asList(geLetters).subList(ninthParameter, 33));
                result.addAll(Arrays.asList(frLetters).subList(eighthParameter, 52));
                result.addAll(Arrays.asList(arLetters).subList(seventhParameter, 76));
                result.addAll(Arrays.asList(aeLetters).subList(sixthParameter, 28));
                result.addAll(Arrays.asList(espLetters).subList(fifthParameter, 54));
                result.addAll(Arrays.asList(plLetters).subList(fourthParameter, 70));
                result.addAll(Arrays.asList(grLetters).subList(thirdParameter, 48));
                result.addAll(Arrays.asList(enLetters).subList(secondParameter, 52));
                result.addAll(Arrays.asList(ruLetters).subList(firstParameter, 66));
                Set<String> uniqueSet = new LinkedHashSet<>(result);
                List<String> uniqueList = new ArrayList<>(uniqueSet);
                String[] mixAlphabet = uniqueList.toArray(new String[0]);

                return new MaxMixingResult(mixAlphabet, firstParameter, secondParameter, thirdParameter,
                        fourthParameter, fifthParameter, sixthParameter, seventhParameter, eighthParameter,
                        ninthParameter, tenthParameter, eleventhParameter, twelfthParameter, thirteenthParameter, fourteenthParameter);
            }
            default -> throw new IllegalStateException("Unexpected value: " + index);
        }
    }
}
