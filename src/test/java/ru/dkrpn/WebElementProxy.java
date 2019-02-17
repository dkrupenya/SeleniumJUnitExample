package ru.dkrpn;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public interface WebElementProxy extends WebElement {
    By getLocator();
    WebElement getLastElement();
    boolean usingCache();
}
