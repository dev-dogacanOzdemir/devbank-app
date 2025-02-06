const express = require('express');
const { MongoClient } = require('mongodb');
const cors = require('cors');

// Uygulama Ayarları
const app = express();
const port = 5000; // Proxy API'nin çalışacağı port
const mongoURI = 'mongodb://localhost:27017/devbank'; // MongoDB bağlantı URI'si
const dbName = 'devbank'; // MongoDB veritabanı adı

app.use(cors()); // CORS sorunlarını çözmek için
app.use(express.json()); // JSON verileri işlemek için

// MongoDB Bağlantı
let db;
MongoClient.connect(mongoURI, { useUnifiedTopology: true })
    .then((client) => {
        db = client.db(dbName);
        console.log(`MongoDB bağlantısı kuruldu: ${dbName}`);
    })
    .catch((error) => {
        console.error('MongoDB bağlantı hatası:', error);
    });

// Toplam Kullanıcı Sayısı
app.get('/api/dashboard/total-users', async (req, res) => {
    try {
        const count = await db.collection('users').countDocuments();
        res.json({ totalUsers: count });
    } catch (error) {
        res.status(500).json({ error: 'Hata: Kullanıcı sayısı alınamadı.' });
    }
});

// Toplam Hesap Sayısı
app.get('/api/dashboard/total-accounts', async (req, res) => {
    try {
        const count = await db.collection('accounts').countDocuments();
        res.json({ totalAccounts: count });
    } catch (error) {
        res.status(500).json({ error: 'Hata: Hesap sayısı alınamadı.' });
    }
});

// Hesap Türlerine Göre Sayılar
app.get('/api/dashboard/accounts-by-type', async (req, res) => {
    try {
        const result = await db.collection('accounts').aggregate([
            { $group: { _id: '$accountType', count: { $sum: 1 } } }
        ]).toArray();
        res.json(result);
    } catch (error) {
        res.status(500).json({ error: 'Hata: Hesap türleri alınamadı.' });
    }
});

// Kullanıcı Rollerine Göre Sayılar
app.get('/api/dashboard/users-by-role', async (req, res) => {
    try {
        const result = await db.collection('users').aggregate([
            { $group: { _id: '$role', count: { $sum: 1 } } }
        ]).toArray();
        res.json(result);
    } catch (error) {
        res.status(500).json({ error: 'Hata: Kullanıcı rolleri alınamadı.' });
    }
});

// Transfer Durumlarına Göre Sayılar
app.get('/api/dashboard/transfers-by-status', async (req, res) => {
    try {
        const result = await db.collection('transfers').aggregate([
            { $group: { _id: '$status', count: { $sum: 1 } } }
        ]).toArray();
        res.json(result);
    } catch (error) {
        res.status(500).json({ error: 'Hata: Transfer durumları alınamadı.' });
    }
});

// Toplam Giriş Sayısı
app.get('/api/dashboard/total-logins', async (req, res) => {
    try {
        const count = await db.collection('loginInfo').countDocuments();
        res.json({ totalLogins: count });
    } catch (error) {
        res.status(500).json({ error: 'Hata: Giriş sayısı alınamadı.' });
    }
});

app.get('/api/dashboard/summary', async (req, res) => {
    try {
        const totalUsers = await db.collection('users').countDocuments();
        const totalAccounts = await db.collection('accounts').countDocuments();
        const totalTransfers = await db.collection('transfers').countDocuments();
        const totalLogins = await db.collection('loginInfo').countDocuments();

        res.json({
            totalUsers,
            totalAccounts,
            totalTransfers,
            totalLogins,
        });
    } catch (error) {
        console.error('Error fetching summary:', error);
        res.status(500).json({ error: 'Internal server error' });
    }
});


// Sunucuyu Başlat
app.listen(port, () => {
    console.log(`Proxy API, http://localhost:${port} adresinde çalışıyor.`);
});
