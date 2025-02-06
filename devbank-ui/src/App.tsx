import React, {useEffect, useRef} from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate, useNavigate } from 'react-router-dom';
import LoginPage from './pages/LoginPage';
import Layout from './components/Layout';
import useAuth from './hooks/useAuth';
import AdminDashboardPage from "./pages/admin/AdminDashboardPage.tsx";

const App: React.FC = () => {
    const { isAuthenticated, role } = useAuth();
    const navigate = useNavigate();
    const isFirstRender = useRef(true); // İlk render'ı kontrol etmek için useRef

    useEffect(() => {
        if (isAuthenticated && isFirstRender.current) {
            if (role === 'ROLE_ADMIN') {
                navigate('/dashboard/admin'); // Admin için varsayılan yol
            } else if (role === 'ROLE_CUSTOMER') {
                navigate('/dashboard/customer'); // Customer için varsayılan yol
            }
            isFirstRender.current = false; // İlk render tamamlandıktan sonra false yap
        }
    }, [isAuthenticated, role, navigate]);

    return (
        <Routes>
            {/* Login Sayfası */}
            <Route path="/login" element={<LoginPage />} />

            {/* Protected Routes */}
            <Route
                path="/"
                element={
                    isAuthenticated ? <Layout /> : <Navigate to="/login" replace />
                }
            >
                {/* Admin Routes */}
                {role === 'ROLE_ADMIN' && (
                    <>
                        <Route path="dashboard/admin" element={<AdminDashboardPage />} />
                        <Route path="dashboard/admin/accounts" element={<div>Admin Accounts</div>} />
                        <Route path="dashboard/admin/cards" element={<div>Admin Cards</div>} />
                        <Route path="dashboard/admin/payments" element={<div>Admin Payments</div>} />
                        <Route path="dashboard/admin/loans" element={<div>Admin Loans</div>} />
                        <Route path="dashboard/admin/currency-gold" element={<div>Admin Currency & Gold</div>} />
                        <Route path="dashboard/admin/reports" element={<div>Admin Reports</div>} />

                    </>
                )}

                {/* Customer Routes */}
                {role === 'ROLE_CUSTOMER' && (
                    <>
                        <Route path="dashboard/customer" element={<div>Customer Dashboard</div>} />
                        <Route path="dashboard/customer/accounts" element={<div>Customer Accounts</div>} />
                        <Route path="dashboard/customer/cards" element={<div>Customer Cards</div>} />
                        <Route path="dashboard/customer/payments" element={<div>Customer Payments</div>} />
                        <Route path="dashboard/customer/loans" element={<div>Customer Loans</div>} />
                        <Route path="dashboard/customer/currency-gold" element={<div>Customer Currency & Gold</div>} />
                        <Route path="dashboard/customer/reports" element={<div>Customer Reports</div>} />
                    </>
                )}
            </Route>

            {/* 404 Not Found Redirect */}
            <Route path="*" element={<Navigate to={isAuthenticated ? "/dashboard" : "/login"} replace />} />
        </Routes>
    );
};

export default function WrappedApp() {
    return (
        <Router>
            <App />
        </Router>
    );
}
