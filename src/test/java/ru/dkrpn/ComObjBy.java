package ru.dkrpn;

public interface ComObjBy<T> {
    public T id(String id);

    public T linkText(String linkText);

    public T partialLinkText(String partialLinkText);

    public T name(String name);

    public T tagName(String tagName);

    public T xpath(String xpathExpression);

    public T className(String className);

    public T cssSelector(String cssSelector);
}
