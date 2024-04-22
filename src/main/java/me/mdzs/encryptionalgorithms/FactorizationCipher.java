package me.mdzs.encryptionalgorithms;

import java.util.ArrayList;
import java.util.List;

import static java.lang.reflect.Array.set;

public class FactorizationCipher {
    private static final String ALPHABET_RUS = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";

    private static final String SYMBOLS = ".,:;!?- ";


    private static String keyPhrase = "";
    private static String initialText = "";

    public FactorizationCipher(String key, String text) {
        keyPhrase = key;
        initialText = text;
    }

    public String encrypt() {
        String encryptedText = "";

        keyPhrase = keyPhrase.toLowerCase();
        keyPhrase = keyPhrase.replaceAll("[^а-яё]", "");

        String newKeyPhrase = "";
        for (int i = 0; i < keyPhrase.length(); i++) {
            if (!newKeyPhrase.contains(String.valueOf(keyPhrase.charAt(i)))) {
                newKeyPhrase += keyPhrase.charAt(i);
            }
        }

        initialText = initialText.toLowerCase();
        initialText = initialText.replaceAll("[^а-яё]", "");

        var text = newKeyPhrase;


        for (int i = 0; i < ALPHABET_RUS.length(); i++) { // добавление в конец keyPhrase буквы алфавита, которых нет в keyPhrase
            if (!keyPhrase.contains(String.valueOf(ALPHABET_RUS.charAt(i))) && !SYMBOLS.contains(String.valueOf(ALPHABET_RUS.charAt(i)))) {
                text += ALPHABET_RUS.charAt(i);
            }
        }

        ArrayList<List<Character>> matrix = new ArrayList<>();

        int countRowsAndColumns = (int) Math.ceil(Math.sqrt(text.length()));

        for (int i = 0; i < countRowsAndColumns; i++) {
            matrix.add(new ArrayList<>());
            for (int j = 0; j < countRowsAndColumns; j++) {
                if (i * countRowsAndColumns + j < text.length()) {
                    matrix.get(i).add(text.charAt(i * countRowsAndColumns + j));
                } else {
                    matrix.get(i).add(' ');
                }
            }
        }

        ArrayList<List<Integer>> coordinates = new ArrayList<>();
        // находим координаты букв в матрице
        for (int i = 0; i < initialText.length(); i++) {
            for (int j = 0; j < countRowsAndColumns; j++) {
                for (int k = 0; k < countRowsAndColumns; k++) {
                    if (initialText.charAt(i) == matrix.get(j).get(k)) {
                        coordinates.add(List.of(j, k));
                    }
                }
            }
        }
        System.out.println(coordinates);

        String coordinatesString = "";
        for (int i = 0; i < coordinates.size(); i++) {
            for (int k = 0; k < 2; k++) {
                coordinatesString += coordinates.get(i).get(k);
            }
        }

        System.out.println(coordinatesString);
        System.out.println(coordinatesString.charAt(0));

        int step = coordinatesString.length() / 2;


        // шифрование
        ArrayList<List<Integer>> newCoordinates = new ArrayList<>();
        newCoordinates = match(coordinatesString, step);
        System.out.println(newCoordinates);


        return encryptedText;
    }

    private ArrayList<List<Integer>> match(String coordinatesString, int step) {//добавляет пары координат (coordinatesString[0],coordinatesString[302]) .. (coordinatesString[301], coordinatesString[603])
        ArrayList<List<Integer>> newCoordinates = new ArrayList<>();
        for (int i = 0; i < coordinatesString.length() / 2; i += 2) {
            newCoordinates.add(List.of(Integer.parseInt(String.valueOf(coordinatesString.charAt(i))), Integer.parseInt(String.valueOf(coordinatesString.charAt(i + step)))));
        }
        return newCoordinates;
    }
}
