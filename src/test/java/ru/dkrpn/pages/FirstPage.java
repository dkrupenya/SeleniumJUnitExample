package ru.dkrpn.pages;

import org.openqa.selenium.By;
import ru.dkrpn.ComObj;
import ru.dkrpn.components.PanelComponent;

import java.util.List;
import java.util.stream.Collectors;

public class FirstPage extends ComObj {

    By panels = By.cssSelector("article.fusion-post-grid.post");

    public List<PanelComponent> getPanels() {
        return elements(panels)
                .stream()
                .map(panel -> ComObj.define(PanelComponent.class, panel, this))
                .collect(Collectors.toList());
    }


}