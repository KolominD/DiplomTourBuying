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

import static com.codeborne.selenide.Selenide.*;

public class UICardNumberTest {
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
    void shouldSendCardStatusDeclined() {
        var paymentPage = new PaymentPage();
        var mainPage = new MainPage();
        mainPage.selectDebitCard();
        paymentPage.allFieldsInput(DataHelper.fakerNumber(16),
                DataHelper.getLocalDate(5, "MM"),
                DataHelper.getLocalDate(1, "YY"),
                DataHelper.fakerCardHolder(),
                DataHelper.fakerCVC());
        paymentPage.rightCheckField("Ошибка", "Ошибка! Банк отказал в проведении операции.");
    }

    @Test
    void shouldSendEmptyCardField() {
        var paymentPage = new PaymentPage();
        var mainPage = new MainPage();
        mainPage.selectDebitCard();
        paymentPage.allFieldsInput(null,
                DataHelper.getLocalDate(5, "MM"),
                DataHelper.getLocalDate(1, "YY"),
                DataHelper.fakerCardHolder(),
                DataHelper.fakerCVC());
        paymentPage.subCard("Неверный формат");
    }

    @Test
    void shouldSendWithNumberCVCInCardField() {
        var paymentPage = new PaymentPage();
        var mainPage = new MainPage();
        mainPage.selectDebitCard();
        paymentPage.allFieldsInput(DataHelper.fakerCVC(),
                DataHelper.getLocalDate(5, "MM"),
                DataHelper.getLocalDate(1, "YY"),
                DataHelper.fakerCardHolder(),
                DataHelper.fakerCVC());
        paymentPage.subCard("Неверный формат");
    }
    @Test
    void shouldSendWithRUNameInCardField() {
        var paymentPage = new PaymentPage();
        var mainPage = new MainPage();
        mainPage.selectDebitCard();
        paymentPage.allFieldsInput(DataHelper.fakerRUName(),
                DataHelper.getLocalDate(5, "MM"),
                DataHelper.getLocalDate(1, "YY"),
                DataHelper.fakerCardHolder(),
                DataHelper.fakerCVC());
        paymentPage.subCard("Неверный формат");
    }

    @Test
    void shouldSendWithENNameInCardField() {
        var paymentPage = new PaymentPage();
        var mainPage = new MainPage();
        mainPage.selectDebitCard();
        paymentPage.allFieldsInput(DataHelper.fakerCardHolder(),
                DataHelper.getLocalDate(5, "MM"),
                DataHelper.getLocalDate(1, "YY"),
                DataHelper.fakerCardHolder(),
                DataHelper.fakerCVC());
        paymentPage.subCard("Неверный формат");
    }
    @Test
    void shouldSendWithInvalidSymbolsInCardField() {
        var paymentPage = new PaymentPage();
        var mainPage = new MainPage();
        mainPage.selectDebitCard();
        paymentPage.allFieldsInput(DataHelper.dataForInvalidPath(),
                DataHelper.getLocalDate(5, "MM"),
                DataHelper.getLocalDate(1, "YY"),
                DataHelper.fakerCardHolder(),
                DataHelper.fakerCVC());
        paymentPage.subCard("Неверный формат");
    }
    @Test
    void shouldSendOverLongNumberInCardField() {
        var paymentPage = new PaymentPage();
        var mainPage = new MainPage();
        mainPage.selectDebitCard();
        paymentPage.allFieldsInput(DataHelper.fakerNumber(17),
                DataHelper.getLocalDate(5, "MM"),
                DataHelper.getLocalDate(1, "YY"),
                DataHelper.fakerCardHolder(),
                DataHelper.fakerCVC());
        paymentPage.subCard("Неверный формат");
    }
    // тест падает, но в поле карты все равно 16 цифр, модуляция не дает ввести больше
}
