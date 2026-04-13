package org.fxstudy.encoderdecryptofx;

import java.util.*;


public class CreateAlphabet {
    record MixResult (String[] mixAlphabet, int[]parameters, int[] indexes) {}

    public MixResult runSimpleMix(int index) {
        return simpleMix(index);
    }

    public MixResult runFullMix(int index) {
        return fullMix(index);
    }

    public MixResult runDecodeKey(String[] keyParts) {
        return DecodeKey(keyParts);
    }

    private MixResult simpleMix(int index) {
        Alphabet guaranteedAlphabet = Arrays.stream(Alphabet.values())
                .filter(a -> a.getIndex() == index)
                .findFirst()
                .orElseThrow();

        List<Alphabet> all = new ArrayList<>(Arrays.asList(Alphabet.values()));
        all.remove(guaranteedAlphabet);
        Collections.shuffle(all);
        List<Alphabet> randomThree = all.subList(0, 3);
        randomThree.add(guaranteedAlphabet);
        Collections.shuffle(randomThree);
        List<Alphabet> allFour = randomThree;

        return mix(allFour);
    }

    private MixResult fullMix(int index) {
        Alphabet guaranteedAlphabet = Arrays.stream(Alphabet.values())
                .filter(a -> a.getIndex() == index)
                .findFirst()
                .orElseThrow();

        List<Alphabet> all = new ArrayList<>(Arrays.asList(Alphabet.values()));
        all.remove(guaranteedAlphabet);
        Collections.shuffle(all);
        all.add(guaranteedAlphabet);
        Collections.shuffle(all);
        List<Alphabet> allAlphabets = all;

        return mix(allAlphabets);
    }

    private void customMix(int index) {

    }

    private static MixResult mix (List<Alphabet> alphabets) {
        List<String> result = new ArrayList<>();
        int amount = alphabets.size();
        int[] parameters = new int[amount];
        int[] indexes = new int[amount];

        for (int i = 0; i < amount - 1; i++) {
            int length = alphabets.get(i).getLength();
            parameters[i] = (int) (Math.random() * length);
        }

        for (int i = 0; i < amount - 1; i++) {
            Alphabet alphabet = alphabets.get(i);
            int parameter = parameters[i];
            String[] letters = alphabet.getLetters();
            result.addAll(Arrays.asList(Objects.requireNonNull(letters)).subList(0, parameter));
        }

        Alphabet center = alphabets.get(amount - 1);
        result.addAll(Arrays.asList(Objects.requireNonNull(center.getLetters())));

        for (int i = amount - 2; i >= 0; i--) {
            Alphabet alphabet = alphabets.get(i);
            int parameter = parameters[i];
            String[] letters = alphabet.getLetters();
            result.addAll(Arrays.asList(Objects.requireNonNull(letters)).subList(parameter, letters.length));
        }

        Set<String> unique = new LinkedHashSet<>(result);
        String[] mixedAlphabet = unique.toArray(new String[0]);

        for (int i = 0; i < amount; i++) {
            Alphabet alphabet = alphabets.get(i);
            indexes[i] = alphabet.getIndex();
        }

        return new MixResult(mixedAlphabet, parameters, indexes);
    }

    private static MixResult decodeMix(int[] indexes, int[] parameters) {
        List<Alphabet> alphabets = new ArrayList<>();
        for (int idx : indexes) {
            Alphabet a = Arrays.stream(Alphabet.values())
                    .filter(al -> al.getIndex() == idx)
                    .findFirst().orElseThrow();
            alphabets.add(a);
        }
        int amount = alphabets.size();

        List<String> result = new ArrayList<>();
        for (int i = 0; i < amount - 1; i++) {
            int parameter = parameters[i];
            String[] letters = alphabets.get(i).getLetters();
            result.addAll(Arrays.asList(letters).subList(0, parameter));
        }

        result.addAll(Arrays.asList(alphabets.get(amount - 1).getLetters()));

        for (int i = amount - 2; i >= 0; i--) {
            int parameter = parameters[i];
            String[] letters = alphabets.get(i).getLetters();
            if (parameter < letters.length) {
                result.addAll(Arrays.asList(letters).subList(parameter, letters.length));
            }
        }
        Set<String> unique = new LinkedHashSet<>(result);
        String[] mixedAlphabet = unique.toArray(new String[0]);
        return new MixResult(mixedAlphabet, parameters, indexes);
    }

    private MixResult DecodeKey(String[] keyParts) {
        int numIndices = Integer.parseInt(keyParts[0]);
        int numParams = Integer.parseInt(keyParts[1]);

        int[] indexes = new int[numIndices];
        int j = 0;
        for (int i = 2; i < 2 + numIndices; i++) {
            indexes[j++] = Integer.parseInt(keyParts[i]);
        }

        int[] parameters = new int[numParams];
        j = 0;
        for (int i = 2 + numIndices; i < 2 + numIndices + numParams; i++) {
            parameters[j++] = Integer.parseInt(keyParts[i]);
        }

        return decodeMix(indexes, parameters);
    }
}
