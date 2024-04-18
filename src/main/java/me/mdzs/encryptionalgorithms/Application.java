package me.mdzs.encryptionalgorithms;

import java.util.Scanner;

public class Application {
    public static void main(String[] args) {

//        Scanner scanner1 = new Scanner(System.in);
//        System.out.println("Введите ключевую фразу:");
//        String keyPhrase = scanner1.nextLine();

        String keyPhrase = "Его пример другим наука;\n" +
                "Но, боже мой, какая скука\n";

//        Scanner scanner2 = new Scanner(System.in);
//        System.out.println("Введите текст для шифрования:");
//        String initialText = scanner2.nextLine();

        String initialText = "В этой конурке он приладил к стене узенькую трехногую кровать, накрыв ее небольшим подобием тюфяка, убитым и плоским, как блин, и, может быть, так же замаслившимся, как блин, который удалось ему вытребовать у хозяина гостиницы. Покамест слуги управлялись и возились, господин отправился в общую залу. Какие бывают эти общие залы — всякий проезжающий знает очень хорошо.";

        SimpleSubstitution simpleSubstitution = new SimpleSubstitution(keyPhrase, initialText);
        var encrypt = simpleSubstitution.encrypt();
        System.out.println("encrypt:");
        System.out.println(encrypt);

        var decrypt = simpleSubstitution.decrypt(encrypt);
        System.out.println("decrypt:");
        System.out.println(decrypt);

    }
}
