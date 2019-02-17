package ru.dkrpn;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class WebElementProxyInvocationHandler implements InvocationHandler {

    public static WebElementProxy define(By locator, WebComponent rootElement, boolean useCache){
        return (WebElementProxy) Proxy.newProxyInstance(
                WebElementProxy.class.getClassLoader(),
                new Class[] { WebElementProxy.class },
                new WebElementProxyInvocationHandler(locator, rootElement, useCache));
    }

    private By locator;
    private WebComponent rootElement;
    private boolean useCache;

    private WebElement element;

    private WebElementProxyInvocationHandler(By locator, WebComponent rootElement, boolean useCache) {
        this.locator = locator;
        this.rootElement = rootElement;
        this.useCache = useCache;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getName().equals("getLocator")) {
            return this.locator;
        }
        if (method.getName().equals("getLastElement")) {
            return this.element;
        }
        if (method.getName().equals("usingCache")) {
            return this.useCache;
        }
        if (!this.useCache || this.element == null) {
            // we don't use cache or no element in cache - recall element
            this.element = this.rootElement.realElement(this.locator);
        }
        return method.invoke(this.element, args);
    }
}
