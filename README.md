# devbank

<p align="center">
  <img width="800" height="400" src="src/assets/devbankLogoTitleWhite.png">
</p>

## 📌 **Proje Açıklaması**

**DevBank**, bankacılık işlemlerini yönetmek için geliştirilmiş, mikroservis mimarisiyle tasarlanmış kapsamlı bir bankacılık uygulamasıdır. Uygulama, ölçeklenebilir ve modüler bir yapıya sahiptir ve çeşitli finansal işlemleri yönetmek için tasarlanmıştır.

**Özellikler:**
- Mikroservis tabanlı mimari
- Kullanıcı ve yönetici panelleri
- Hesap, kart, transfer ve para birimi yönetimi
- Yetkilendirme ve oturum yönetimi (JWT Authentication)
- API ile tam entegrasyon
- Planlanmış işlemler ve zamanlanmış görevler
- Hata yönetim modülü

---

## 📌 **Kullanılan Teknolojiler**

### **Backend Teknolojileri**
- **Java 17** - Uygulamanın temel dili
- **Spring Boot** - Mikroservislerin geliştirilmesi
- **Spring Security** - Yetkilendirme ve kimlik doğrulama
- **Spring Data MongoDB** - NoSQL veritabanı yönetimi
- **Lombok** - Boilerplate kodlardan kaçınmak için

### **Veritabanı & Depolama**
- **MongoDB** - NoSQL tabanlı veritabanı

### **Diğer Araçlar**
- **Maven** - Proje yönetimi ve bağımlılık yönetimi
- **JUnit & Mockito** - Test otomasyonu

---

## 📌 **Gereksinimler**

- **Java 17**
- **Maven**
- **MongoDB**
- **Docker** (Opsiyonel, konteynerizasyon için)

---

## 📌 **Kurulum**

1. **Depoyu Klonlayın:**
   ```bash
   git clone https://github.com/dev-dogacanOzdemir/devbank-app.git
   cd devbank-app
   ```

2. **Her Bir Mikroservisin Bağımlılıklarını Yükleyin:**
   ```bash
   mvn clean install
   ```
---

## 📌 **Uygulamayı Çalıştırma**

Her bir mikroservisi başlatmak için ilgili klasöre gidip aşağıdaki komutu çalıştırın:
```bash
mvn spring-boot:run
```

Mikroservisler ve varsayılan portları:

- **Kullanıcı Yönetimi Servisi**: `http://localhost:2001`
- **Hesap Yönetimi Servisi**: `http://localhost:2002`
- **Döviz ve Altın Kurları Servisi**: `http://localhost:2003`
- **Transfer Servisi**: `http://localhost:2004`
- **Kart Yönetimi Servisi**: `http://localhost:2005`
- **Kredi Yönetimi Servisi**: `http://localhost:2006`


**Not:** Port numaraları `application.properties` dosyalarında belirtilmiştir.

---

## 📌 **Geliştirme Süreci**

Bu proje, aktif olarak geliştirilmektedir ve yeni özellikler eklenmeye devam etmektedir. Kullanıcı geri bildirimleri doğrultusunda iyileştirmeler yapılacak ve proje daha kararlı bir hale getirilecektir. Güncellemeleri takip etmek için depoyu izleyebilirsiniz.

---

## 📌 **Lisans**

Bu proje **MIT Lisansı** ile lisanslanmıştır.

---


