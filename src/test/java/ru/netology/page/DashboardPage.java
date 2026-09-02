package ru.netology.page;

import com.codeborne.selenide.ElementsCollection;

import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {

    private final ElementsCollection cards = $$(".list__item div");
    private int extractBalance(String text) {
        int start = text.indexOf("баланс: ");
        int finish = text.indexOf(" р.");
        String value = text.substring(start + "баланс: ".length(), finish);
        return Integer.parseInt(value);
    }

    public int getCardBalance(int index) {
        String text = cards.get(index).text();
        return extractBalance(text);
    }

    public TransferPage selectCardToTopUp(int index) {
        cards.get(index).$("button").click();
        return new TransferPage();
    }

}
