import { useEffect, useState } from 'react';

const useAuth = () => {
    const [isAuthenticated, setIsAuthenticated] = useState(false); // Giriş durumu
    const [role, setRole] = useState<string | null>(null); // Kullanıcı rolü

    useEffect(() => {
        const validateSession = async () => {
            const userId = localStorage.getItem('userId');
            const userRole = localStorage.getItem('role');

            if (!userId || !userRole) {
                setIsAuthenticated(false);
                localStorage.clear(); // Eksik bilgi varsa oturumu temizle
                return;
            }

            try {
                // Backend'de oturum doğrulama isteği gönder
                const response = await fetch(`http://localhost:2001/api/users/validate/${userId}`);
                if (response.ok) {
                    setIsAuthenticated(true);
                    setRole(userRole);
                } else {
                    throw new Error('Invalid session');
                }
            } catch (error) {
                console.error('Session validation failed:', error);
                setIsAuthenticated(false);
                localStorage.clear();
            }
        };

        validateSession();
    }, []);

    const logout = () => {
        localStorage.clear(); // Oturumu temizle
        setIsAuthenticated(false); // Giriş durumunu kapat
        window.location.href = '/login'; // Login sayfasına yönlendir
    };

    return { isAuthenticated, role , logout };
};

export default useAuth;
