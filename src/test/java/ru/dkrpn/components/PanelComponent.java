package ru.dkrpn.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.dkrpn.ComObj;

public class PanelComponent extends ComObj {

    public By title = By.cssSelector(".blog-shortcode-post-title.entry-title");
    public By header = By.cssSelector("header");

    public By answers = By.cssSelector(".fusion-meta-info .fusion-alignright a");

}
