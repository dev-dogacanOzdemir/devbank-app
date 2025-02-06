import {
    IconBuildingBank, IconCreditCard, IconCreditCardPay,
    IconFileDescription, IconHome, IconLogout,
    IconSwitchHorizontal, IconUser, IconUsersGroup,
} from '@tabler/icons-react';
import { Group } from '@mantine/core';
import classes from './NavbarSimpleColored.module.css';
import { DevbankIcon } from "../assets/DevbankIcon.tsx";
import { NavLink } from "react-router-dom"; // React Router'ın NavLink bileşeni
import useAuth from '../hooks/useAuth';


export function NavbarSimpleColored() {
    const { logout, role } = useAuth(); // Auth hook'tan çıktıları al
    const data = role === 'ROLE_ADMIN'
        ? [
            { link: '/dashboard/admin', label: 'Home Page', icon: IconHome },
            { link: '/dashboard/admin/accounts', label: 'Accounts', icon: IconUsersGroup },
            { link: '/dashboard/admin/cards', label: 'Cards', icon: IconCreditCard },
            { link: '/dashboard/admin/payments', label: 'Payments', icon: IconCreditCardPay },
            { link: '/dashboard/admin/loans', label: 'Loans', icon: IconBuildingBank },
            { link: '/dashboard/admin/currency-gold', label: 'Currency & Gold', icon: IconSwitchHorizontal },
            { link: '/dashboard/admin/reports', label: 'Reports', icon: IconFileDescription },
        ]
        : [
            { link: '/dashboard/customer', label: 'Home Page', icon: IconHome },
            { link: '/dashboard/customer/accounts', label: 'Accounts', icon: IconUsersGroup },
            { link: '/dashboard/customer/cards', label: 'Cards', icon: IconCreditCard },
            { link: '/dashboard/customer/payments', label: 'Payments', icon: IconCreditCardPay },
            { link: '/dashboard/customer/loans', label: 'Loans', icon: IconBuildingBank },
            { link: '/dashboard/customer/currency-gold', label: 'Currency & Gold', icon: IconSwitchHorizontal },
            { link: '/dashboard/customer/reports', label: 'Reports', icon: IconFileDescription },
        ];
    return (
        <nav className={classes.navbar}>
            <div className={classes.navbarMain}>
                <Group className={classes.header} justify="space-between">
                    <DevbankIcon />
                </Group>

                {/* Navigasyon bağlantıları */}
                {data.map((item) => (
                    <NavLink
                        to={item.link}
                        key={item.label}
                        className={({ isActive }) =>
                            `${classes.link} ${isActive ? "data-active" : ""}`
                        }
                    >
                        <item.icon className={classes.linkIcon} stroke={1.5} />
                        <span>{item.label}</span>
                    </NavLink>
                ))}
            </div>

            <div className={classes.footer}>
                <NavLink to="/profile" className={classes.link}>
                    <IconUser className={classes.linkIcon} stroke={1.5} />
                    <span>Profile</span>
                </NavLink>

                <NavLink to="#" onClick={logout} className={classes.link}>
                    <IconLogout className={classes.linkIcon} stroke={1.5} />
                    <span>Logout</span>
                </NavLink>
            </div>
        </nav>
    );
}
