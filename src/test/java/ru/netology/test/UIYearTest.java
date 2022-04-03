package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.dataBase.DataBaseHelper;
import ru.netology.page.PaymentPage;
import ru.netology.page.MainPage;

import static com.codeborne.selenide.Selenide.open;

public class UIYearTest {
    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    public void setUp() {
        open("http://localhost:8080/");
    }
    @Test
    void shouldSendEmptyYearField() {
        var paymentPage = new PaymentPage();
        var mainPage = new MainPage();
        mainPage.selectDebitCard();
        paymentPage.allFieldsInput(DataHelper.getCardNumber("APPROVED"),
                DataHelper.getLocalDate(5, "MM"),
                null,
                DataHelper.fakerCardHolder(),
                DataHelper.fakerCVC());
        paymentPage.subYear("Неверный формат");
    }
    @Test
    void shouldSendWithPastYear() {
        var paymentPage = new PaymentPage();
        var mainPage = new MainPage();
        mainPage.selectDebitCard();
        paymentPage.allFieldsInput(DataHelper.getCardNumber("APPROVED"),
                DataHelper.getLocalDate(5, "MM"),
                DataHelper.getLocalDate(-3,"YY"),
                DataHelper.fakerCardHolder(),
                DataHelper.fakerCVC());
        paymentPage.subYear("Истёк срок действия карты");
    }

    @Test
    void shouldSendWithOverFutureYear() {
        var paymentPage = new PaymentPage();
        var mainPage = new MainPage();
        mainPage.selectDebitCard();
        paymentPage.allFieldsInput(DataHelper.getCardNumber("APPROVED"),
                DataHelper.getLocalDate(5, "MM"),
                DataHelper.getLocalDate(100,"YY"),
                DataHelper.fakerCardHolder(),
                DataHelper.fakerCVC());
        paymentPage.subYear("Неверно указан срок действия карты");
    }
    @Test
    void shouldSendInvalidSymbolsInYearField() {
        var paymentPage = new PaymentPage();
        var mainPage = new MainPage();
        mainPage.selectDebitCard();
        paymentPage.allFieldsInput(DataHelper.getCardNumber("APPROVED"),
                DataHelper.getLocalDate(5, "MM"),
                DataHelper.dataForInvalidPath(),
                DataHelper.fakerCardHolder(),
                DataHelper.fakerCVC());
        paymentPage.subYear("Неверный формат");
    }
    @Test
    void shouldSendENNameInYearField() {
        var paymentPage = new PaymentPage();
        var mainPage = new MainPage();
        mainPage.selectDebitCard();
        paymentPage.allFieldsInput(DataHelper.getCardNumber("APPROVED"),
                DataHelper.getLocalDate(5, "MM"),
                DataHelper.fakerCardHolder(),
                DataHelper.fakerCardHolder(),
                DataHelper.fakerCVC());
        paymentPage.subYear("Неверный формат");
    }

    @Test
    void shouldSend1InYearField() {
        var paymentPage = new PaymentPage();
        var mainPage = new MainPage();
        mainPage.selectDebitCard();
        paymentPage.allFieldsInput(DataHelper.getCardNumber("APPROVED"),
                DataHelper.getLocalDate(5, "MM"),
                "1",
                DataHelper.fakerCardHolder(),
                DataHelper.fakerCVC());
        paymentPage.subYear("Неверный формат");
    }
}
