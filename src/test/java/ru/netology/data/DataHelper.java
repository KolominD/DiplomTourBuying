package ru.netology.data;

import com.github.javafaker.Faker;

import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

public class DataHelper {
    private DataHelper() {

    }

    private static final Faker faker = new Faker(new Locale("en"));

    @Value
    public static class CardInfo {
        private String numberCards;
        private String month;
        private String year;
        private String holder;
        private String cvc;
    }

    public static String getCardNumber(String status) {
        String cardNumber = "";
        if (status.equals("APPROVED")) {
            cardNumber = "4444 4444 4444 4441";
        }
        if (status.equals("DECLINED")) {
            cardNumber = "4444 4444 4444 4442";
        }
        return cardNumber;
    }

    public static String dataForInvalidPath() {
        String[] symbols = {"+=_-", "%$#^@", "**--**", "...!"};
        int rnd = new Random().nextInt(symbols.length);
        return symbols[rnd];
    }

    public static String fakerNumber(int lenght) {
        String number = faker.number().digits(lenght);
        return number;
    }

    public static String fakerCardHolder() {
        String holder = faker.name().name();
        return holder;
    }

    public static String fakerCVC() {
        String cvc = faker.number().digits(3);
        return cvc;
    }


    public static String getLocalDate(int numberMonthOrYear , String ofPattern) {
        return LocalDate.now().plusMonths(numberMonthOrYear).format(DateTimeFormatter.ofPattern(ofPattern));
    }

    public static String fakerString(int length) {
        String str = "qwertyqwertyqwertyqwertyqwertyqwerty";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(36);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    public static String fakerRUName() {
        Faker faker2 = new Faker(new Locale("ru"));
        String textRU = faker2.name().name();
        return textRU;
    }
}
