package ru.dkrpn.pages;

import org.openqa.selenium.By;
import ru.dkrpn.WebComponent;
import ru.dkrpn.WebComponentList;
import ru.dkrpn.components.MainMenuComponent;
import ru.dkrpn.components.PanelComponent;

public class FirstPage extends WebComponent {

    public By componentLocator = By.cssSelector(".body");

    public WebComponentList<PanelComponent> panels = listBy(PanelComponent.class).cssSelector("article.fusion-post-grid.post");

    public MainMenuComponent mainMenu = WebComponent.define(MainMenuComponent.class, By.cssSelector(".fusion-main-menu"), this);

}
