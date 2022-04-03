package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.dataBase.DataBaseHelper;
import ru.netology.page.MainPage;
import ru.netology.page.PaymentPage;

import static com.codeborne.selenide.Selenide.open;
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
        DataBaseHelper.dbClean();
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

        var statusSQL = DataBaseHelper.dbGetStatus();
        assertEquals("APPROVED", statusSQL);

        var countSQL = DataBaseHelper.dbGetOrderEntity();
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

        var statusSQL = DataBaseHelper.dbGetStatusByCreditCard();
        assertEquals("APPROVED", statusSQL);

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
        var amountSQL = DataBaseHelper.dbGetAmountByDebitCard();
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

        var statusSQL = DataBaseHelper.dbGetStatusByCreditCard();
        assertEquals("DECLINED", statusSQL);
    }
}
