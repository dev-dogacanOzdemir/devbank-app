package com.devbank.currency.gold.api.enums;

public enum GoldType {
    GRAM("Gram Altın"),
    ONS("ONS Altın"),
    QUARTER("Çeyrek Altın"),
    HALF("Yarım Altın"),
    FULL("Tam Altın"),
    CUMHURIYET("Cumhuriyet Altını"),
    GREENSE("Gremse Altın"),
    OLD_QUARTER("Çeyrek Altın Eski"),
    OLD_HALF("Yarım Altın Eski"),
    OLD_FULL("Tam Altın Eski"),
    BRACELET_22("22 Ayar Bilezik"),
    OLD_GREENSE("Gremse Altın Eski"),
    RESAT("Reşat Lira Altın"),
    RESAT_DOUBLE("Reşat İkibuçuk Altın"),
    RESAT_FIVE("Reşat Beşibiryerde"),
    ATA("Ata Altın"),
    ZIYNET("Ziynet Altın"),
    GOLD_14K("14 Ayar Altın"),
    GOLD_18K("18 Ayar Altın"),
    FIVE("Beşli Altın"),
    HAMIT("Hamit Altın"),
    DOUBLE("İkibuçuk Altın"),
    SILVER("Altın Gümüş"),
    ONS_EUR("ONS EUR"),
    SILVER_ONLY("Gümüş");

    private final String apiName;

    GoldType(String apiName) {
        this.apiName = apiName;
    }

    public String getApiName() {
        return apiName;
    }

    public static GoldType fromApiName(String apiName) {
        for (GoldType type : values()) {
            if (type.getApiName().equalsIgnoreCase(apiName)) {
                return type;
            }
        }
        return null;
    }
}
