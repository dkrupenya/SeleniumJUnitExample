package ru.dkrpn.components;

import org.openqa.selenium.WebElement;
import ru.dkrpn.ComObj;

public class PanelComponent extends ComObj {

    public WebElement title = by().cssSelector(".blog-shortcode-post-title.entry-title");

    public WebElement answers = by().cssSelector(".fusion-meta-info .fusion-alignright a");

}
