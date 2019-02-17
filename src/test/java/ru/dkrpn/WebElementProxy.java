package ru.dkrpn;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class WebElementProxy implements InvocationHandler {

    public static WebElement define(By locator, WebComponent rootElement, boolean useCache){
        return (WebElement) Proxy.newProxyInstance(
                WebElement.class.getClassLoader(),
                new Class[] { WebElement.class },
                new WebElementProxy(locator, rootElement, useCache));
    }

    private By locator;
    private WebComponent rootElement;
    private boolean useCache;

    private WebElement element;

    private WebElementProxy(By locator, WebComponent rootElement, boolean useCache) {
        this.locator = locator;
        this.rootElement = rootElement;
        this.useCache = useCache;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (this.useCache) {
            if (this.element == null) {
                this.element = this.rootElement.element(this.locator);
            }
            return method.invoke(this.element, args);
        } else {
           // no cache
            WebElement tmpElement = this.rootElement.element(this.locator);
            return method.invoke(tmpElement, args);
        }
    }

}
