package com.devbank.currency.gold.api.enums;

public enum GoldType {
    GRAM(1L, "GRAM", "Gram Altın"),
    OUNCE(2L, "OUNCE", "Ons Altın"),
    HALF(3L, "HALF", "Yarım Altın"),
    FULL(4L, "FULL", "Tam Altın"),
    QUARTER(5L, "QUARTER", "Çeyrek Altın"),
    REPUBLIC(6L, "REPUBLIC", "Cumhuriyet Altını");

    private final Long id;
    private final String abbreviation;
    private final String name;

    GoldType(Long id, String abbreviation, String name) {
        this.id = id;
        this.abbreviation = abbreviation;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public String getName() {
        return name;
    }
}
