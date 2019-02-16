package ru.dkrpn;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

public abstract class ComObj {
    public WebDriver driver;
    public ComObj parent;
    public By componentSelector;
    WebElement componentElement;

    public static <T extends ComObj> T definePage(Class<T> classDef, WebDriver driver) {
        return ComObj.define(classDef, driver, null, By.cssSelector("body"), null);
    }

    public static <T extends ComObj> T define(Class<T> classDef, By componentSelector, ComObj parent) {
        return ComObj.define(classDef, parent.driver, parent, componentSelector, null);
    }

    public static <T extends ComObj> T define(Class<T> classDef, By componentSelector, WebDriver driver) {
        return ComObj.define(classDef, driver, null, componentSelector, null);
    }

    public static <T extends ComObj> T define(Class<T> classDef, WebElement componentElement, ComObj parent) {
        return ComObj.define(classDef, parent.driver, parent, null, componentElement);
    }

    public static <T extends ComObj> T define(Class<T> classDef, WebElement componentElement, WebDriver driver) {
        return ComObj.define(classDef, driver, null, null, componentElement);
    }

    public static <T extends ComObj> T define(Class<T> classDef,
                                              WebDriver driver,
                                              ComObj parent,
                                              By componentSelector,
                                              WebElement componentElement) {
        try {
            T obj = classDef.newInstance();
            obj.driver = driver;
            obj.parent = parent;
            obj.componentSelector = componentSelector;
            obj.componentElement = componentElement;
            return obj;

        } catch (InstantiationException | IllegalAccessException  e) {
            throw new RuntimeException("can't instantiate " + classDef.getName());
        }
    }

    public boolean isCached() {
        return this.componentElement != null;
    }

    public WebElement element(By locator) {
        return this.getComponentElement().findElement(locator);
    }

    public List<WebElement> elements(By locator) {
        return this.getComponentElement().findElements(locator);
    }

    public <T extends ComObj> List<T> frozenComponents(Class<T> clazz, By locator) {
        return elements(locator)
                .stream()
                .map(panel -> ComObj.define(clazz, panel, this))
                .collect(Collectors.toList());
    }

    public <T extends ComObj> ComObjBy<ComObjList<T>> by(Class<T> clazz) {
        class InnerComObjBy implements ComObjBy<ComObjList<T>> {
            private ComObj parent;
            private Class<T> clazz;

            InnerComObjBy(ComObj parent, Class<T> clazz) {
                this.parent = parent;
                this.clazz = clazz;
            }

            public ComObjList<T> id(String id) {
                return construct(By.id(id));
            }

            public ComObjList<T> linkText(String linkText) {
                return construct(By.linkText(linkText));
            }

            public ComObjList<T> partialLinkText(String partialLinkText) {
                return construct(By.partialLinkText(partialLinkText));
            }

            public ComObjList<T> name(String name) {
                return construct(By.name(name));
            }

            public ComObjList<T> tagName(String tagName) {
                return construct(By.tagName(tagName));
            }

            public ComObjList<T> xpath(String xpathExpression) {
                return construct(By.xpath(xpathExpression));
            }

            public ComObjList<T> className(String className) {
                return construct(By.className(className));
            }

            public ComObjList<T> cssSelector(String cssSelector) {
                return construct(By.cssSelector(cssSelector));
            }

            private ComObjList<T> construct(By by) {
                return new ComObjList<T>(clazz, parent, by);
            }
        }

        return new InnerComObjBy(this, clazz);
    }

    public WebElement getComponentElement() {
        if (this.componentElement != null) {
            return this.componentElement;
        }
        if (this.componentSelector == null) {
            throw new RuntimeException("component element or component selector should be defined: " + this.getClass().getName());
        }
        return this.parent != null ?
                this.parent.getComponentElement().findElement(this.componentSelector) : driver.findElement(this.componentSelector);
    }

    public WebElement weProxy(By selector) {
        return WebElementProxy.define(selector, this, false);
    }
    public ComObjBy<WebElement> by() {
        return this.by(false);
    }

    public ComObjBy<WebElement> by(boolean useCache) {
        class InnerComObjBy implements ComObjBy<WebElement> {
            private ComObj comObj;
            private boolean useCache;

            InnerComObjBy(ComObj comObj, boolean useCache) {
                this.comObj = comObj;
                this.useCache = useCache;
            }

            public WebElement id(String id) {
                return construct(By.id(id));
            }

            public WebElement linkText(String linkText) {
                return construct(By.linkText(linkText));
            }

            public WebElement partialLinkText(String partialLinkText) {
                return construct(By.partialLinkText(partialLinkText));
            }

            public WebElement name(String name) {
                return construct(By.name(name));
            }

            public WebElement tagName(String tagName) {
                return construct(By.tagName(tagName));
            }

            public WebElement xpath(String xpathExpression) {
                return construct(By.xpath(xpathExpression));
            }

            public WebElement className(String className) {
                return construct(By.className(className));
            }

            public WebElement cssSelector(String cssSelector) {
                return construct(By.cssSelector(cssSelector));
            }

            private WebElement construct(By by) {
                return WebElementProxy.define(by, comObj, useCache);
            }
        }

        return new InnerComObjBy(this, useCache);
    }
}
