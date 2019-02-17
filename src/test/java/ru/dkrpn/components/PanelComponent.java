package ru.dkrpn.components;

import org.openqa.selenium.WebElement;
import ru.dkrpn.WebComponent;

public class PanelComponent extends WebComponent {

    public WebElement title = elementBy().cssSelector(".blog-shortcode-post-title.entry-title");

    public WebElement answers = elementBy().cssSelector(".fusion-meta-info .fusion-alignright a");

}
