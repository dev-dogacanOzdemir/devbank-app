package com.devbank.transfer.api.enums;

public enum TransferStatus {
    PENDING, // İşlem sırasındaki transferler
    COMPLETED, // Başarıyla tamamlanmış transferler
    FAILED // Hatalı veya eksik işlem nedeniyle başarısız olan transferler
}
