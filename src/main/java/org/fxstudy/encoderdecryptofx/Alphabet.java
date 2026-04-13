package org.fxstudy.encoderdecryptofx;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * ENUM-класс алфавитов:
 * здесь собраны все алфавиты в программе, вы можете самостоятельно добавить новый, для этого добавьте файл с любым
 * нужным вам алфавитом в папку "resources" и назовите его: "Alphabet" + Индекс страны. Сюда вам предстоит написать
 * строчку enum для него,
 * а именно ИндексСтраны("название файла", количество символов в алфавите, следующий индекс
 * (важно!!! Пишите следующий, не предыдущий, не нынешний, это последовательность алфавитов. 1,2,3,4... и так далее)).
 * **/

public enum Alphabet {
    ENG("AlphabetENG", 52, 1),
    RU("AlphabetRU", 66, 2),
    GR("AlphabetGR", 48, 3),
    PL("AlphabetPL", 70, 4),
    ESP("AlphabetESP", 54, 5),
    AE("AlphabetAE", 28, 6),
    AR("AlphabetAR", 76, 7),
    FR("AlphabetFR", 52, 8),
    GE("AlphabetGE", 33, 9),
    GER("AlphabetGER", 52, 10),
    IL("AlphabetIL", 22, 11),
    ITA("AlphabetITA", 42, 12),
    KR("AlphabetKR", 40, 13),
    TR("AlphabetTR", 58, 14),
    UA("AlphabetUA", 66, 15);

    private final String resourceName;
    private final int length;
    private final int index;
    private String[] letters;

    Alphabet(String resourceName, int length, int index) {
        this.resourceName = "/org/fxstudy/encoderdecryptofx/" + resourceName;
        this.length = length;
        this.index = index;
    }

    public String[] getLetters() {
        if (letters == null) {
            try (InputStream inputStream = getClass().getResourceAsStream(resourceName)) {
                if (inputStream == null) throw new RuntimeException("Resource not found " + resourceName);
                List<String> lines = new BufferedReader(new InputStreamReader(inputStream)).lines().toList();
                letters = lines.getFirst().split(" ");
                if (letters.length != length) {
                    throw new IllegalStateException("Alphabet " + resourceName + " length mismatch");
                }
            } catch (IOException e ) {
                throw new RuntimeException("Failed to load alphabet " + resourceName, e);
            }
            return letters;
        }
        return letters;
    }

    public int getLength() {
        return length;
    }

    public int getIndex() {
        return index;
    }

    public String getResourceName() {
        return resourceName;
    }
}
