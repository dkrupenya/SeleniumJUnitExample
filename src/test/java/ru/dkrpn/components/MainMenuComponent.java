package ru.dkrpn.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.dkrpn.WebComponent;

import java.util.List;

public class MainMenuComponent extends WebComponent {
    By menuItems = By.cssSelector(".menu-item");

    public void clickMenuItem(int number) {
        getComponentElement().findElements(menuItems).get(number).click();
    }

    public String getMenuItemText(int number) {
        WebElement component =  getComponentElement();
        List<WebElement> items =component.findElements(menuItems);
        return items.get(number).getText();
    }
}
