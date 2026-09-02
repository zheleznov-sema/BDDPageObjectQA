package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.sleep;

public class VerificationPage {

    private final SelenideElement codeField = $("[data-test-id='code'] input");
    private final SelenideElement verifyButton  = $("[data-test-id='action-verify']");

    public DashboardPage validVerify(String verificationCode) {
        codeField.setValue(verificationCode);
        verifyButton.click();

        sleep(1000);

        System.out.println("URL AFTER VERIFY: " + WebDriverRunner.url());
        System.out.println("PAGE AFTER VERIFY: " + $("body").text());

        return new DashboardPage();
    }

}
