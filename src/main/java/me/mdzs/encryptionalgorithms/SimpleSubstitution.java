package me.mdzs.encryptionalgorithms;

import java.util.Scanner;

class SimpleSubstitution {
    private static final String ALPHABET_RUS = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";
    private static final String SYMBOLS = ".,:;!?- ";
    private static String keyPhrase = "";
    private static String initialText = "";

    public static void setKeyPhrase(String key) {
        keyPhrase = key;
    }

    public static void setInitialText(String text) {
        initialText = text;
    }


    public SimpleSubstitution(String key, String text) {
        keyPhrase = key;
        initialText = text;
    }


    private static String processing(String text){
        // подготовка keyPhrase. Перевод в нижний регистр и удаление символов, не входящих в алфавит
        text = text.toLowerCase();
        text = text.replaceAll("[^а-яё]", "");

        //удаление повторных букв
        String newKeyPhrase = "";
        for (int i = 0; i < text.length(); i++) {
            if (!newKeyPhrase.contains(String.valueOf(text.charAt(i)))) {
                newKeyPhrase += text.charAt(i);
            }
        }

        return text;
    }


    public String encrypt() {
        String encryptedText = "";

        // подготовка keyPhrase. Перевод в нижний регистр и удаление символов, не входящих в алфавит
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

        encryptedText = newKeyPhrase;


        for (int i = 0; i < ALPHABET_RUS.length(); i++) { // добавление в конец keyPhrase буквы алфавита, которых нет в keyPhrase
            if (!keyPhrase.contains(String.valueOf(ALPHABET_RUS.charAt(i))) && !SYMBOLS.contains(String.valueOf(ALPHABET_RUS.charAt(i)))) {
                encryptedText += ALPHABET_RUS.charAt(i);
            }
        }
        String newEncryptedText = "";

        for (int i = 0; i < initialText.length(); i++) { //сопоставление букв алфавита и символов keyPhrase
            if (ALPHABET_RUS.contains(String.valueOf(initialText.charAt(i)))) {
                newEncryptedText += encryptedText.charAt(ALPHABET_RUS.indexOf(initialText.charAt(i)));
            } else {
                newEncryptedText += initialText.charAt(i);
            }
        }

        for (int i = 0; i < newEncryptedText.length(); i++) {
            if ((i != 0) & (i % 5 == 0)){
                newEncryptedText = newEncryptedText.substring(0, i) + " " + newEncryptedText.substring(i);
            }
        }

        System.out.println("алфавит:");
        System.out.println(ALPHABET_RUS);
        System.out.println("Обработанный ключ:");
        System.out.println(encryptedText);

        System.out.println("результат:");

        return newEncryptedText;
    }

    public String decrypt(String encryptedText) {
        String decryptedText = "";

        keyPhrase = keyPhrase.toLowerCase();
        keyPhrase = keyPhrase.replaceAll("[^а-яё]", "");

        String newKeyPhrase = "";
        for (int i = 0; i < keyPhrase.length(); i++) {
            if (!newKeyPhrase.contains(String.valueOf(keyPhrase.charAt(i)))) {
                newKeyPhrase += keyPhrase.charAt(i);
            }
        }

        for (int i = 0; i < ALPHABET_RUS.length(); i++) { // добавление в конец keyPhrase буквы алфавита, которых нет в keyPhrase
            if (!keyPhrase.contains(String.valueOf(ALPHABET_RUS.charAt(i))) && !SYMBOLS.contains(String.valueOf(ALPHABET_RUS.charAt(i)))) {
                newKeyPhrase += ALPHABET_RUS.charAt(i);
            }
        }

        encryptedText = encryptedText.replaceAll("[^а-яё]", "");

        for (int i = 0; i < encryptedText.length(); i++) { // дешифровка
            if (ALPHABET_RUS.contains(String.valueOf(encryptedText.charAt(i)))) {
                decryptedText += ALPHABET_RUS.charAt(keyPhrase.indexOf(encryptedText.charAt(i)));
            } else {
                decryptedText += encryptedText.charAt(i);
            }
        }

        return decryptedText;
    }
}