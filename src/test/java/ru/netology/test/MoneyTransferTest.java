package ru.netology.test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;
import ru.netology.page.TransferPage;
import ru.netology.page.VerificationPage;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;



public class MoneyTransferTest {

    @AfterEach
    void tearDown() {
        closeWebDriver();
    }

    @Test
    void shouldTransferMoneyFromSecondCardToFirst(){
        open("http://localhost:9999");
        LoginPage loginPage = new LoginPage();
        VerificationPage verificationPage = loginPage.validLogin("vasya", "qwerty123");
        DashboardPage dashboardPage = verificationPage.validVerify("12345");

        int firstCardBalance = dashboardPage.getCardBalance(0);
        int secondCardBalance = dashboardPage.getCardBalance(1);

        int amount = 1000;

        TransferPage transferPage =
                dashboardPage.selectCardToTopUp(0);

        DashboardPage dashboardAfterTransfer =
                transferPage.makeTransfer("5559 0000 0000 0002", amount);

        int actualFirstCardBalance = dashboardAfterTransfer.getCardBalance(0);
        int actualSecondCardBalance = dashboardAfterTransfer.getCardBalance(1);

        int expectedFirstCardBalance = firstCardBalance + amount;
        int expectedSecondCardBalance = secondCardBalance - amount;

        assertEquals(expectedFirstCardBalance, actualFirstCardBalance);
        assertEquals(expectedSecondCardBalance, actualSecondCardBalance);

    }

    @Test
    void shouldTransferMoneyFromFirstCardToSecond() {
        open("http://localhost:9999");

        LoginPage loginPage = new LoginPage();
        VerificationPage verificationPage =
                loginPage.validLogin("vasya", "qwerty123");
        DashboardPage dashboardPage =
                verificationPage.validVerify("12345");


        int firstCardBalance = dashboardPage.getCardBalance(0);
        int secondCardBalance = dashboardPage.getCardBalance(1);

        int amount = 1000;

        TransferPage transferPage =
                dashboardPage.selectCardToTopUp(1);

        DashboardPage dashboardAfterTransfer =
                transferPage.makeTransfer("5559 0000 0000 0001", amount);

        int actualFirstCardBalance =
                dashboardAfterTransfer.getCardBalance(0);
        int actualSecondCardBalance =
                dashboardAfterTransfer.getCardBalance(1);

        int expectedFirstCardBalance =
                firstCardBalance - amount;

        int expectedSecondCardBalance =
                secondCardBalance + amount;

        assertEquals(expectedFirstCardBalance, actualFirstCardBalance);
        assertEquals(expectedSecondCardBalance, actualSecondCardBalance);
    }


    @Disabled("Known bug: transfer amount can exceed card balance")

    @Test
    void shouldNotTransferMoreThanCardBalance() {
        open("http://localhost:9999");

        LoginPage loginPage = new LoginPage();

        VerificationPage verificationPage =
                loginPage.validLogin("vasya", "qwerty123");

        DashboardPage dashboardPage =
                verificationPage.validVerify("12345");

        int secondCardBalance =
                dashboardPage.getCardBalance(1);

        int amount =
                secondCardBalance + 1000;

        TransferPage transferPage =
                dashboardPage.selectCardToTopUp(0);

        DashboardPage dashboardAfterTransfer =
                transferPage.makeTransfer(
                        "5559 0000 0000 0002",
                        amount
                );

        int actualSecondCardBalance =
                dashboardAfterTransfer.getCardBalance(1);

        assertEquals(
                secondCardBalance,
                actualSecondCardBalance
        );
    }
}
