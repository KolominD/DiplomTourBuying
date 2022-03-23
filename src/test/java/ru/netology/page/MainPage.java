package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.byText;

import static com.codeborne.selenide.Selenide.$;

public class MainPage {

    private SelenideElement buttonCredit = $(byText("Купить в кредит"));
    private SelenideElement buttonDebit = $(byText("Купить"));

    public PaymentPage selectDebitCard() {
        buttonDebit.click();
        return new PaymentPage();
    }

    public PaymentPage selectCreditCard() {
        buttonCredit.click();
        return new PaymentPage();
    }
}