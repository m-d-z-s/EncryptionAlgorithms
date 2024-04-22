package me.mdzs.encryptionalgorithms;

import kotlin.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class FactorizationCipher {
    private static final String ALPHABET_RUS = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";

    private static final List<Character> SYMBOLS = Arrays.asList(',', '.', '!');
    private final int countRowsAndColumns = 6;

    private ArrayList<List<Character>> matrix;

    private static String keyPhrase;
    private static String initialText;

    public FactorizationCipher(String key, String text) {
        keyPhrase = initKeyPhrase(key);
        initialText = initInitialText(text);
        matrix = generateMatrix(keyPhrase);
    }

    @NotNull
    private String initInitialText(@NotNull String text) {
        var initialText = text.toLowerCase();
        return initialText.replaceAll("[^а-яё]", "");
    }

    @NotNull
    private String initKeyPhrase(@NotNull String key) {
        var keyPhrase = key.toLowerCase();
        keyPhrase = keyPhrase.replaceAll("[^а-яё]", "");

        StringBuilder newKeyPhrase = new StringBuilder();
        for (int i = 0; i < keyPhrase.length(); i++) {
            if (!newKeyPhrase.toString().contains(String.valueOf(keyPhrase.charAt(i)))) {
                newKeyPhrase.append(keyPhrase.charAt(i));
            }
        }

        StringBuilder text = new StringBuilder(newKeyPhrase.toString());


        for (int i = 0; i < ALPHABET_RUS.length(); i++) { // добавление в конец keyPhrase буквы алфавита, которых нет в keyPhrase
            if (!keyPhrase.contains(String.valueOf(ALPHABET_RUS.charAt(i))) && !SYMBOLS.contains(String.valueOf(ALPHABET_RUS.charAt(i)))) {
                text.append(ALPHABET_RUS.charAt(i));
            }
        }

        return text.toString();
    }

    public String encrypt() {
        ArrayList<List<Integer>> coordinates = getCoordinates(initialText);

        var coordinatesLined = new ArrayList<Integer>();
        for (int i = 0; i < coordinates.get(0).size(); i++) {
            for (List<Integer> coordinate : coordinates) {
                coordinatesLined.add(coordinate.get(i));
            }
        }

        var newCoordinates = match(coordinatesLined);

        var item = processByCoordinatesPairs(newCoordinates);

        return item;
    }

    public String decrypt(String encryptedText) {
        var coordinates = getCoordinates(encryptedText);

        var revertedCoordinates = revertCoordinates(coordinates);
        var item = processByCoordinatesPairs(revertedCoordinates);

        return item;
    }

    @NotNull
    private ArrayList<List<Integer>> getCoordinates(@NotNull String text) {
        ArrayList<List<Integer>> coordinates = new ArrayList<>();
        // находим координаты букв в матрице
        for (int i = 0; i < text.length(); i++) {
            for (int j = 0; j < countRowsAndColumns; j++) {
                for (int k = 0; k < countRowsAndColumns; k++) {
                    if (text.charAt(i) == matrix.get(k).get(j)) {
                        coordinates.add(List.of(k, j));
                    }
                }
            }
        }

        return coordinates;
    }

//    @NotNull
//    private List<Pair<Integer, Integer>> revertCoordinates(@NotNull ArrayList<List<Integer>> coordinates) {
//        var revertedCoordinates = new ArrayList<Pair<Integer, Integer>>();
//        var first = coordinates.subList(0, coordinates.size() / 2);
//        var second = coordinates.subList(coordinates.size() / 2, coordinates.size());
//
//        for (int i = 0; i < first.size(); i++) {
//            revertedCoordinates.add(new Pair<>(first.get(i).get(0), second.get(i).get(0)));
//            revertedCoordinates.add(new Pair<>(first.get(i).get(1), second.get(i).get(1)));
//        }
//
//        return revertedCoordinates;
//    }

    @NotNull
    private List<Pair<Integer, Integer>> revertCoordinates(@NotNull ArrayList<List<Integer>> coordinates) {
        var revertedCoordinates = new ArrayList<Pair<Integer, Integer>>();
        List<Integer> first = new ArrayList<>();
        List<Integer> second = new ArrayList<>();
        List<Integer> processedCoordinates = new ArrayList<>();
        List<Integer> coordinatesLined = newCoordinatesLined(coordinates);
        for (int i = 0; i < coordinates.size() * 2; i++) {
            processedCoordinates.add(coordinatesLined.get(i));
//                revertedCoordinates.add(new Pair<>(coordinatesLined.get(i), coordinatesLined.get(i + coordinates.size())));
        }

        first = processedCoordinates.subList(0, processedCoordinates.size() / 2);
        second = processedCoordinates.subList(processedCoordinates.size() / 2, processedCoordinates.size());

        for (int i = 0; i < first.size(); i++) {
            revertedCoordinates.add(new Pair<>(first.get(i), second.get(i)));
        }

        return revertedCoordinates;
    }

    private List<Integer> newCoordinatesLined(ArrayList<List<Integer>> coordinates) {
        List<Integer> coordinatesLined = new ArrayList<>();
        for (int i = 0; i < coordinates.size(); i++) {
            for (int j = 0; j < coordinates.get(i).size(); j++) {
                coordinatesLined.add(coordinates.get(i).get(j));
            }
        }

        return coordinatesLined;
    }

    @NotNull
    private String processByCoordinates(@NotNull List<List<Integer>> coordinates) {
        StringBuilder message = new StringBuilder();
        for (List<Integer> coordinate : coordinates)
            message.append(matrix.get(coordinate.get(1)).get(coordinate.get(0)));

        return message.toString();
    }

    @NotNull
    private String processByCoordinatesPairs(@NotNull List<Pair<Integer, Integer>> coordinates) {
        StringBuilder message = new StringBuilder();
        for (Pair<Integer, Integer> coordinate : coordinates)
            message.append(matrix.get(coordinate.getFirst()).get(coordinate.getSecond()));

        return message.toString();
    }

    @NotNull
    private ArrayList<List<Character>> generateMatrix(@NotNull String keyPhrase) {
        ArrayList<List<Character>> matrix = new ArrayList<>();
        var keyPhraseArray = keyPhrase.toCharArray();
        int columnIndex = 0;
        int rowIndex = 0;
        int symbolsIndex = 2;

        for (int i = 0; i < countRowsAndColumns; i++) {
            matrix.add(new ArrayList<>(Collections.nCopies(countRowsAndColumns, '\0')));
        }

        for (char character : keyPhraseArray) {
            if (columnIndex == countRowsAndColumns) {
                columnIndex = 0;
                rowIndex++;
            }
            matrix.get(columnIndex++).set(rowIndex, character);
        }

        for (List<Character> characters : matrix) {
            for (int j = 0; j < characters.size(); j++) {
                if (characters.get(j) == '\0') characters.set(j, SYMBOLS.get(symbolsIndex--));
            }
        }

        return matrix;
    }

    @NotNull
    private ArrayList<Pair<Integer, Integer>> match(@NotNull ArrayList<Integer> coordinatesLined) {//добавляет пары координат (coordinatesString[0],coordinatesString[302]) .. (coordinatesString[301], coordinatesString[603])
        int step = 2;
        ArrayList<Pair<Integer, Integer>> newCoordinates = new ArrayList<>();
        for (int i = 0; i < coordinatesLined.size(); i += step) {
            // newCoordinates.add(List.of(Integer.parseInt(String.valueOf(coordinatesLined.charAt(i))), Integer.parseInt(String.valueOf(coordinatesLined.charAt(i + step)))));
            newCoordinates.add(new Pair<>(coordinatesLined.get(i), coordinatesLined.get(i + 1)));
        }

        return newCoordinates;
    }
}
