import React from 'react';
import {NavbarSimpleColored} from './NavbarSimpleColored';

import {Outlet} from "react-router-dom";

const Layout: React.FC = () =>  {
    return (
        <div style={{display: 'flex', height: '100vh', overflow: 'hidden'}}>
            {/* Sol tarafta sabit Navbar */}
            <div style={{ flexShrink: 0}}>
                <NavbarSimpleColored/>
            </div>

            {/* Sağ tarafta dinamik içerik alanı */}
            <div style={{flex: 1, padding: '20px', overflowY: 'auto', backgroundColor: '#e5e5e5'}}>
                <Outlet/>
            </div>
        </div>
    );
};

export default Layout;
