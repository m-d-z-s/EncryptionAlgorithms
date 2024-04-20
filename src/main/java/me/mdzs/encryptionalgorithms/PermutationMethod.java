package me.mdzs.encryptionalgorithms;

import java.util.ArrayList;
import java.util.List;

public class PermutationMethod {
    private static final String ALPHABET_RUS = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";
    private static final String SYMBOLS = ".,:;!?- ";
    private static String keyWord = "брусилова";
    private static String keyPhrase = "";

    public PermutationMethod(String key){
        keyPhrase = key;
    }

    public String encrypt() {
        String encryptedText = "";

        // приоритеты букв в ключевом слове (по возрастанию)
        int[] priorities = new int[keyWord.length()];
        int index = 1;
        for (int i = 0; i < ALPHABET_RUS.length(); i++) {
            for (int j = 0; j < keyWord.length(); j++) {
                if (ALPHABET_RUS.charAt(i) == keyWord.charAt(j)) {
                    priorities[j] = index;
                    index++;
                }
            }
        }

        keyPhrase = keyPhrase.toLowerCase();
        keyPhrase = keyPhrase.replaceAll("[\\s\\n]", "");

        int countRows = keyPhrase.length() % keyWord.length() == 0 ? keyPhrase.length() / keyWord.length() : keyPhrase.length() / keyWord.length() + 1;
        char[][] matrix = new char[countRows][keyWord.length()];

        int indexKeyPhrase = 0;
        for (int i = 0; i < countRows; i++) {
            for (int j = 0; j < keyWord.length(); j++) {
                if (indexKeyPhrase < keyPhrase.length()) {
                    matrix[i][j] = keyPhrase.charAt(indexKeyPhrase);
                    indexKeyPhrase++;
                } else {
                    matrix[i][j] = ' ';
                }
            }
        }

        String[] strings = new String[keyWord.length()];

        for (int i = 0; i < keyWord.length(); i++) {
            strings[i] = "";
            for (int j = 0; j < countRows; j++) {
                strings[i] += matrix[j][i];
            }
        }


        // сортировка массива строк по приоритетам букв в ключевом слове
        for (int i = 0; i < keyWord.length(); i++) {
            for (int j = 0; j < keyWord.length(); j++) {
                if (priorities[j] == i + 1) {
                    encryptedText += strings[j];
                }
            }
        }
        // удаление лишних пробелов
        encryptedText = encryptedText.replaceAll("[\\s]", "");

        //разбивка на строки по 5 символов
        List<String> substrings = new ArrayList<>();
        for (int i = 0; i < encryptedText.length(); i += 5) {
            if (i + 5 < encryptedText.length()) {
                substrings.add(encryptedText.substring(i, i + 5));
            } else {
                substrings.add(encryptedText.substring(i));
            }
        }
        String result = String.join(" ", substrings);

        encryptedText = result;


        return encryptedText;
    }

    public String decrypt(){
        return "decrypt";
    }
}
