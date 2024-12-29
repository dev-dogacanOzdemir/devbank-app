// Hesap Türleri için tür tanımı
export type AccountType = {
    _id: string; // Hesap türü, örneğin "CURRENT" veya "SAVINGS"
    count: number; // Bu türden kaç hesap var
};

// Kullanıcı Rolleri için tür tanımı
export type UserRole = {
    _id: string; // Kullanıcı rolü, örneğin "ROLE_ADMIN" veya "ROLE_USER"
    count: number; // Bu rolden kaç kullanıcı var
};

// Transfer Durumları için tür tanımı
export type TransferStatus = {
    _id: string; // Transfer durumu, örneğin "COMPLETED", "PENDING", "FAILED"
    count: number; // Bu durumdaki transferlerin sayısı
};
