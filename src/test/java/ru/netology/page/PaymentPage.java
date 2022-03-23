package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class PaymentPage {
    private SelenideElement fieldCard = $("[placeholder='0000 0000 0000 0000']");
    private SelenideElement fieldMonth = $("[placeholder='08']");
    private SelenideElement fieldYear = $("[placeholder='22']");
    private SelenideElement fieldHolder = $(byText("Владелец")).parent().$("input");
    private SelenideElement fieldCVC = $("[placeholder='999']");
    private SelenideElement fieldGo = $(byText("Продолжить"));

    private SelenideElement finalMessage = $("[class='notification__title']");
    private SelenideElement finalMessageDescription = $(".notification__content");

    private SelenideElement subCard = fieldCard.parent().parent().$(".input__sub");
    private SelenideElement subMonth = fieldMonth.parent().parent().$(".input__sub");
    private SelenideElement subYear = fieldYear.parent().parent().$(".input__sub");
    private SelenideElement subHolder = fieldHolder.parent().parent().$(".input__sub");
    private SelenideElement subCVC = fieldCVC.parent().parent().$(".input__sub");

    public void rightCheckField(String expectedText, String expectedMessageDescription) {
        finalMessage.shouldHave(exactText(expectedText), Duration.ofSeconds(15));
        finalMessageDescription.shouldBe(visible).shouldHave(exactText(expectedMessageDescription));
    }

    public void subCard(String expectedMessage) {
        subCard.shouldHave(text(expectedMessage));
    }

    public void subMounth(String expectedMessage) {
        subMonth.shouldHave(text(expectedMessage));
    }

    public void subYear(String expectedMessage) {
        subYear.shouldHave(text(expectedMessage));
    }

    public void subCVC(String expectedMessage) {
        subCVC.shouldHave(text(expectedMessage));
    }

    public void subHolder(String expectedMessage) {
        subHolder.shouldHave(text(expectedMessage));
    }


    public void allFieldsInput(String dateNumberCard, String dateMonth, String dateYear, String dateCardOwner, String dateCVC) {
        fieldCard.setValue(dateNumberCard);
        fieldMonth.setValue(dateMonth);
        fieldYear.setValue(dateYear);
        fieldHolder.setValue(dateCardOwner);
        fieldCVC.setValue(dateCVC);
        fieldGo.click();
    }


}
