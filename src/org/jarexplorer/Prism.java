package org.jarexplorer;


import jfk.prism.*;

public class Prism {

    private String name;

    private ICallable callable;
    private Description description;


    public Prism(ICallable callable, Description description, String name) {
        this.callable = callable;
        this.description = description;
        this.name = name;
    }

    public ICallable getCallable() {
        return callable;
    }

    public Description getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

}
