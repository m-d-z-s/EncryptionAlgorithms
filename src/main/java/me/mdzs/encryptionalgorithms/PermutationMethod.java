package me.mdzs.encryptionalgorithms;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PermutationMethod {
    private static final String ALPHABET_RUS = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";
    private static final String SYMBOLS = ".,:;!?- ";
    private static String keyWord = "брусилова";
    private static String keyPhrase = "";

    List<Integer> priorities = getPriorities(keyWord);;

    public PermutationMethod(String key) {
        keyPhrase = key;
        priorities = getPriorities(keyWord);
    }

    private List<Integer> getPriorities(String keyWord) {
        List<Integer> priorities = new ArrayList<>();
        int index = 0;
        for (int i = 0; i < ALPHABET_RUS.length(); i++) {
            for (int j = 0; j < keyWord.length(); j++) {
                if (ALPHABET_RUS.charAt(i) == keyWord.charAt(j)) {
                    priorities.add(j);

                }
            }
        }
        return priorities;
    }

    private int countRows() {
        int countRows = keyPhrase.length() % keyWord.length() ==0? keyPhrase.length() / keyWord.length() : keyPhrase.length() / keyWord.length() + 1;

        return countRows;
    }

    public String encrypt() {
        StringBuilder encryptedText = new StringBuilder();

        keyPhrase = keyPhrase.toLowerCase();
        keyPhrase = keyPhrase.replaceAll("[\\s\\n]", "");

        int countRows = countRows();

        List<List<Character>> matrix = generateMatrix(keyPhrase, keyWord.length());


        String message = makeMessage(matrix);

        message = output(message);


        return message;
    }

    private String makeMessage(List<List<Character>> matrix) {
        StringBuilder message = new StringBuilder();
        for (Integer priorities : priorities) {
            var column = matrix.get(priorities);
            for (int i = 0; i < column.size(); i++) {
                message.append(column.get(i));
            }
        }
        return message.toString();
    }

    private List<List<Character>> generateMatrix(String keyPhrase, int length) {
        List<List<Character>> matrix = new ArrayList<>();
        var characters = keyPhrase.toCharArray();

        for (int i = 0; i < length; i++) {
            List<Character> columns = new ArrayList<>();
            for (int j = 0; j < characters.length; j+=length) {
                    columns.add(characters[j+i]);
            }
            matrix.add(columns);
        }
        return matrix;
    }

    public String decrypt(@NotNull String encryptedText) {
        StringBuilder decryptedText = new StringBuilder();
        encryptedText = encryptedText.replace(" ", "");
        int countRows = countRows();

        var wrongColumns = getWrongColumnsIndexes(encryptedText.length(), countRows);

        ArrayList<ArrayList<Character>> matrix = new ArrayList<>();
        int counter = keyWord.length();
        while (counter > 0) {
            counter--;
            matrix.add(new ArrayList<>());
        }

        String partition = "";

        for (int priority : priorities) {
            if (wrongColumns.contains(priority))  partition = encryptedText.substring(0, countRows - 1).concat(" ");
            else partition = encryptedText.substring(0, countRows);

            encryptedText = encryptedText.replace(partition, "");
            var characters = partition.toCharArray();
            ArrayList<Character> column = new ArrayList<>();
            for (char character : characters) {
                column.add(character);
            }
            matrix.set(priority, column);
        }

        for (int i = 0; i < matrix.get(0).size(); i++) {
            for (ArrayList<Character> characters : matrix) {
                decryptedText.append(characters.get(i));
            }
        }

        return decryptedText.toString();
    }

    @NotNull
    private List<Integer> getWrongColumnsIndexes(int length, int countRows) {
        ArrayList<Integer> indexes = new ArrayList<>();
        int maxIndex = keyWord.length() - 1;
        int wrongCount = countRows * keyPhrase.length() % length;
        for (int i = 0; i < wrongCount; i++) {
            indexes.add(maxIndex--);
        }
        return indexes;
    }

    private String output(String text) {
        // удаление лишних пробелов
        text = text.replaceAll("[\\s]", "");

        //разбивка на строки по 5 символов
        List<String> substrings = new ArrayList<>();
        for (int i = 0; i < text.length(); i += 5) {
            if (i + 5 < text.length()) {
                substrings.add(text.substring(i, i + 5));
            } else {
                substrings.add(text.substring(i));
            }
        }
        String result = String.join(" ", substrings);

        text = result;

        return text;

    }

}
