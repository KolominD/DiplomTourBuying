package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.db.dbHelper;
import ru.netology.page.PaymentPage;
import ru.netology.page.MainPage;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HappyPathAndSadPath {

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
    void happyPathByDebitCard() {
        var PaymentPage = new PaymentPage();
        var mainPage = new MainPage();
        mainPage.selectDebitCard();
        PaymentPage.allFieldsInput(DataHelper.getCardNumber("APPROVED"),
                DataHelper.getLocalDate(3, "MM"),
                DataHelper.getLocalDate(0, "YY"),
                DataHelper.fakerCardHolder(),
                DataHelper.fakerCVC());
        PaymentPage.rightCheckField("Успешно", "Операция одобрена Банком.");

        var statusSQL = dbHelper.dbGetStatus();
        assertEquals("APPROVED", statusSQL);

        var countSQL = dbHelper.dbGetOrder_Entity();
        assertEquals("1", countSQL);
    }

    @Test
    void happyPathByCreditCard() {
        var PaymentPage = new PaymentPage();
        var mainPage = new MainPage();
        mainPage.selectCreditCard();
        PaymentPage.allFieldsInput(DataHelper.getCardNumber("APPROVED"),
                DataHelper.getLocalDate(3, "MM"),
                DataHelper.getLocalDate(0, "YY"),
                DataHelper.fakerCardHolder(),
                DataHelper.fakerCVC());
        PaymentPage.rightCheckField("Успешно", "Операция одобрена Банком.");

        var statusSQL = dbHelper.dbGetStatus();
        assertEquals("APPROVED", statusSQL);

        var countSQL = dbHelper.dbGetOrder_Entity();
        assertEquals("1", countSQL);
    }

    @Test
    void shouldCheckAmountInDB() {
        var paymentPage = new PaymentPage();
        var mainPage = new MainPage();
        mainPage.selectDebitCard();
        paymentPage.allFieldsInput(DataHelper.getCardNumber("APPROVED"),
                DataHelper.getLocalDate(3, "MM"),
                DataHelper.getLocalDate(0, "YY"),
                DataHelper.fakerCardHolder(),
                DataHelper.fakerCVC());
        paymentPage.rightCheckField("Успешно", "Операция одобрена Банком.");
        var amountSQL = dbHelper.dbGetAmount();
        assertEquals("45000", amountSQL);
    }

    @Test
    void sadPathByCreditCard() {
        var PaymentPage = new PaymentPage();
        var mainPage = new MainPage();
        mainPage.selectCreditCard();
        PaymentPage.allFieldsInput(DataHelper.getCardNumber("DECLINED"),
                DataHelper.getLocalDate(3, "MM"),
                DataHelper.getLocalDate(0, "YY"),
                DataHelper.fakerCardHolder(),
                DataHelper.fakerCVC());
        PaymentPage.rightCheckField("Ошибка", "Ошибка! Банк отказал в проведении операции.");

        var statusSQL = dbHelper.dbGetStatus();
        assertEquals("DECLINED", statusSQL);
    }
}
