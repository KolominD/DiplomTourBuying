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

public class UICVCTest {
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
        DataBaseHelper.dbClean();
        open("http://localhost:8080/");
    }

    @Test
    void shouldSendWithEmptyCVCField() {
        var paymentPage = new PaymentPage();
        var mainPage = new MainPage();
        mainPage.selectDebitCard();
        paymentPage.allFieldsInput(DataHelper.getCardNumber("APPROVED"),
                DataHelper.getLocalDate(3, "MM"),
                DataHelper.getLocalDate(0, "YY"),
                DataHelper.fakerCardHolder(),
                null);
        paymentPage.subCVC("Неверный формат");
    }
    @Test
    void shouldSendWith1CVCField() {
        var paymentPage = new PaymentPage();
        var mainPage = new MainPage();
        mainPage.selectDebitCard();
        paymentPage.allFieldsInput(DataHelper.getCardNumber("APPROVED"),
                DataHelper.getLocalDate(3, "MM"),
                DataHelper.getLocalDate(0, "YY"),
                DataHelper.fakerCardHolder(),
                "1");
        paymentPage.subCVC("Неверный формат");
    }

    @Test
    void shouldSendWith1234CVCField() {
        var paymentPage = new PaymentPage();
        var mainPage = new MainPage();
        mainPage.selectDebitCard();
        paymentPage.allFieldsInput(DataHelper.getCardNumber("APPROVED"),
                DataHelper.getLocalDate(3, "MM"),
                DataHelper.getLocalDate(0, "YY"),
                DataHelper.fakerCardHolder(),
                "1234");
        paymentPage.subCVC("Неверный формат");
    }
    // тест падает, но в поле CVC остается 3 цифры

    @Test
    void shouldSendWithENNameInCVCField() {
        var paymentPage = new PaymentPage();
        var mainPage = new MainPage();
        mainPage.selectDebitCard();
        paymentPage.allFieldsInput(DataHelper.getCardNumber("APPROVED"),
                DataHelper.getLocalDate(3, "MM"),
                DataHelper.getLocalDate(0, "YY"),
                DataHelper.fakerCardHolder(),
                DataHelper.fakerCardHolder());
        paymentPage.subCVC("Неверный формат");
    }
    @Test
    void shouldSendWithInvalidSymbolsInCVCField() {
        var paymentPage = new PaymentPage();
        var mainPage = new MainPage();
        mainPage.selectDebitCard();
        paymentPage.allFieldsInput(DataHelper.getCardNumber("APPROVED"),
                DataHelper.getLocalDate(3, "MM"),
                DataHelper.getLocalDate(0, "YY"),
                DataHelper.fakerCardHolder(),
                DataHelper.dataForInvalidPath());
        paymentPage.subCVC("Неверный формат");
    }
}
