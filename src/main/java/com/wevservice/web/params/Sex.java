package com.wevservice.web.params;

public enum Sex {
    M("male"), F("female");

    Sex(String value) {
        this.value = value;
    }

    public String value;
}
