package org.fxstudy.encoderdecryptofx;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class KeyGenerator {
    private List<String> listAlphabetEng;
    private String key;
    InputStream alphabetEng = getClass().getResourceAsStream("/org/fxstudy/encoderdecryptofx/AlphabetENG");

    public void start(int select) {
        switch (select) {
            case 1 -> {
                simpleKeyGenerator();
            }
            case 2 -> {
                advancedKeyGenerator();
            }
            case 3 -> {
                advancedFullKeyGenerator();
            }
        }
    }

    private void simpleKeyGenerator() {

                /**
                 * Дань уважения прошлому, жалко что его может разгадать тот же дипсик за 10 секунд :D
                 * Переписан на новые рельсы, отчего он возможно будет чуть быстрее CLI решения
                 * **/

        listAlphabetEng = new BufferedReader(new InputStreamReader(Objects.requireNonNull(alphabetEng)))
                .lines()
                .collect(Collectors.toList());

        String[] letters = listAlphabetEng.getFirst().split(" ");

        int random1 = (int) (Math.random() * 9);
        int random2 = (int) (Math.random() * 52);
        int random3 = (int) (Math.random() * 52);
        int random4 = (int) (Math.random() * 52);
        int random5 = (int) (Math.random() * 52);
        int random6 = (int) (Math.random() * 99);
        int random7 = (int) (Math.random() * 99);
        int random8 = (int) (Math.random() * 99);
        int random9 = (int) (Math.random() * 99);
        int random10 = (int) (Math.random() * 99);

        int guaranteedDigit = random1 + 1;

        String key = letters[random1] + letters[random2] + guaranteedDigit + random6 +
                letters[random3] + letters[random4] + random7 + random8 +
                letters[random5] + random9 + random10;

        this.key = key;
    }

    private void advancedKeyGenerator() {

    }

    private void advancedFullKeyGenerator() {

    }

    public String getKey() {
        return key;
    }
}
