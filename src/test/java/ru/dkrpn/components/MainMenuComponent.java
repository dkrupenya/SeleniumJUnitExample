package ru.dkrpn.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.dkrpn.WebComponent;
import ru.dkrpn.WebElementList;

import java.util.List;

public class MainMenuComponent extends WebComponent {
    WebElementList menuItems = listBy().cssSelector(".menu-item");

    public void clickMenuItem(int number) {
        menuItems.findElements().get(number).click();
    }

    public String getMenuItemText(int number) {
        List<WebElement> items = menuItems.findElements();
        return items.get(number).getText();
    }
}
