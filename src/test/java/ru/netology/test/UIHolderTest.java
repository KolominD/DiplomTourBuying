package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.db.dbHelper;
import ru.netology.page.MainPage;
import ru.netology.page.PaymentPage;

import static com.codeborne.selenide.Selenide.open;

public class UIHolderTest {
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
    void sendingInvalidCardOwnerEmptyTest() {
        var paymentPage = new PaymentPage();
        var mainPage = new MainPage();
        mainPage.selectDebitCard();
        paymentPage.allFieldsInput(DataHelper.getCardNumber("APPROVED"),
                DataHelper.getLocalDate(5, "MM"),
                DataHelper.getLocalDate(1, "YY"),
                null,
                DataHelper.fakerCVC());
        paymentPage.subHolder("Поле обязательно для заполнения");
    }

    @Test
    void shouldSendWithRUName() {
        var paymentPage = new PaymentPage();
        var mainPage = new MainPage();
        mainPage.selectDebitCard();
        paymentPage.allFieldsInput(DataHelper.getCardNumber("APPROVED"),
                DataHelper.getLocalDate(5, "MM"),
                DataHelper.getLocalDate(1, "YY"),
                DataHelper.fakerRUName(),
                DataHelper.fakerCVC());
        paymentPage.subHolder("Неверный формат");
    }
    @Test
    void shouldSendWithNumberCVCinFieldHolder() {
        var paymentPage = new PaymentPage();
        var mainPage = new MainPage();
        mainPage.selectCreditCard();
        paymentPage.allFieldsInput(DataHelper.getCardNumber("APPROVED"),
                DataHelper.getLocalDate(5, "MM"),
                DataHelper.getLocalDate(1, "YY"),
                DataHelper.fakerCVC(),
                DataHelper.fakerCVC());
        paymentPage.subHolder("Неверный формат");
    }
    @Test
    void shouldSendWithOverlongHolder() {
        var paymentPage = new PaymentPage();
        var mainPage = new MainPage();
        mainPage.selectDebitCard();
        paymentPage.allFieldsInput(DataHelper.getCardNumber("APPROVED"),
                DataHelper.getLocalDate(5, "MM"),
                DataHelper.getLocalDate(1, "YY"),
                DataHelper.fakerString(29),
                DataHelper.fakerCVC());
        paymentPage.subHolder("Максимальное кол-во символов 27");
    }
    @Test
    void shouldSendWithOverShortHolder() {
        var paymentPage = new PaymentPage();
        var mainPage = new MainPage();
        mainPage.selectDebitCard();
        paymentPage.allFieldsInput(DataHelper.getCardNumber("APPROVED"),
                DataHelper.getLocalDate(5, "MM"),
                DataHelper.getLocalDate(1, "YY"),
                DataHelper.fakerString(1),
                DataHelper.fakerCVC());
        paymentPage.subHolder("Минимальное кол-во символов 2");
    }
    @Test
    void shouldSendInvalidSymbols() {
        var paymentPage = new PaymentPage();
        var mainPage = new MainPage();
        mainPage.selectDebitCard();
        paymentPage.allFieldsInput(DataHelper.getCardNumber("APPROVED"),
                DataHelper.getLocalDate(5, "MM"),
                DataHelper.getLocalDate(1, "YY"),
                DataHelper.dataForInvalidPath(),
                DataHelper.fakerCVC());
        paymentPage.subHolder("Неверный формат, использованы недопустимые символы");
    }
}
