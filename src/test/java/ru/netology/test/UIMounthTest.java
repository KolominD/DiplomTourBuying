package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.db.dbHelper;
import ru.netology.page.PaymentPage;
import ru.netology.page.MainPage;

import static com.codeborne.selenide.Selenide.open;

public class UIMounthTest {
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
        dbHelper.dbClean();
        open("http://localhost:8080/");
    }

    @Test
    void shouldSendEmptyMounthField() {
        var paymentPage = new PaymentPage();
        var mainPage = new MainPage();
        mainPage.selectDebitCard();
        paymentPage.allFieldsInput(DataHelper.getCardNumber("APPROVED"),
                null,
                DataHelper.getLocalDate(0, "YY"),
                DataHelper.fakerCardHolder(),
                DataHelper.fakerCVC());
        paymentPage.subMounth("Неверный формат");
    }

    @Test
    void shouldSendWithOneNumberINMounthField() {
        var paymentPage = new PaymentPage();
        var mainPage = new MainPage();
        mainPage.selectDebitCard();
        paymentPage.allFieldsInput(DataHelper.getCardNumber("APPROVED"),
                DataHelper.fakerNumber(1),
                DataHelper.getLocalDate(0, "YY"),
                DataHelper.fakerCardHolder(),
                DataHelper.fakerCVC());
        paymentPage.subMounth("Неверный формат");
    }

    @Test
    void shouldSendWith13InMounthField() {
        var paymentPage = new PaymentPage();
        var mainPage = new MainPage();
        mainPage.selectDebitCard();
        paymentPage.allFieldsInput(DataHelper.getCardNumber("APPROVED"),
                "13",
                DataHelper.getLocalDate(0, "YY"),
                DataHelper.fakerCardHolder(),
                DataHelper.fakerCVC());
        paymentPage.subMounth("Неверно указан срок действия карты");
    }

    @Test
    void shouldSendWith00InMounthField() {
        var paymentPage = new PaymentPage();
        var mainPage = new MainPage();
        mainPage.selectDebitCard();
        paymentPage.allFieldsInput(DataHelper.getCardNumber("APPROVED"),
                "00",
                DataHelper.getLocalDate(0, "YY"),
                DataHelper.fakerCardHolder(),
                DataHelper.fakerCVC());
        paymentPage.subMounth("Неверно указан срок действия карты");
    }

    @Test
    void shouldSendWithENNameInMounthField() {
        var paymentPage = new PaymentPage();
        var mainPage = new MainPage();
        mainPage.selectDebitCard();
        paymentPage.allFieldsInput(DataHelper.getCardNumber("APPROVED"),
                DataHelper.fakerCardHolder(),
                DataHelper.getLocalDate(0, "YY"),
                DataHelper.fakerCardHolder(),
                DataHelper.fakerCVC());
        paymentPage.subMounth("Неверный формат");
    }

    @Test
    void shouldSendWithInvalidSymbolsInMounthField() {
        var paymentPage = new PaymentPage();
        var mainPage = new MainPage();
        mainPage.selectDebitCard();
        paymentPage.allFieldsInput(DataHelper.getCardNumber("APPROVED"),
                DataHelper.dataForInvalidPath(),
                DataHelper.getLocalDate(0, "YY"),
                DataHelper.fakerCardHolder(),
                DataHelper.fakerCVC());
        paymentPage.subMounth("Неверный формат");
    }

    @Test
    void shouldSendWithPastNumberInMounthField() {
        var paymentPage = new PaymentPage();
        var mainPage = new MainPage();
        mainPage.selectDebitCard();
        paymentPage.allFieldsInput(DataHelper.getCardNumber("APPROVED"),
                DataHelper.getLocalDate(-1,"MM"),
                DataHelper.getLocalDate(0, "YY"),
                DataHelper.fakerCardHolder(),
                DataHelper.fakerCVC());
        paymentPage.subMounth("Неверно указан срок действия карты");
    }
}
