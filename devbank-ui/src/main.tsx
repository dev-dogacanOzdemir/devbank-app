import React from 'react';
import ReactDOM from 'react-dom/client';
import App from './App';

import '@mantine/core/styles.css';

import {createTheme, MantineProvider, rem} from "@mantine/core";

const mainTheme = createTheme({
    colors: {
        deepBlue: [
            '#eaf3f9',
            '#cddbe6',
            '#aac4d5',
            '#88aec4',
            '#6898b3',
            '#4f86a6',
            '#3e7494',
            '#2e617f',
            '#1f4e6a',
            '#113a54',
        ],
        // Rich Gold
        richGold: [
            '#fff7e8',
            '#f7ebc6',
            '#eedfa1',
            '#e5d37b',
            '#dbc757',
            '#c9a646',
            '#b68d3d',
            '#9d7433',
            '#845c2a',
            '#6c4420',
        ],
        // Light Platinum
        lightPlatinum: [
            '#f9f9f9',
            '#f1f1f1',
            '#e6e6e6',
            '#dcdcdc',
            '#d2d2d2',
            '#bfbfbf',
            '#adadad',
            '#979797',
            '#828282',
            '#6e6e6e',
        ],
        // Emerald Green
        emeraldGreen: [
            '#e5f8f4',
            '#c3ece3',
            '#9addd0',
            '#70cebd',
            '#49bfa9',
            '#34a890',
            '#2e927e',
            '#257863',
            '#1e5f4d',
            '#164637',
        ],
        // Midnight Black
        midnightBlack: [
            '#f2f2f2',
            '#e5e5e5',
            '#cccccc',
            '#b2b2b2',
            '#999999',
            '#808080',
            '#666666',
            '#4d4d4d',
            '#333333',
            '#1a1a1a',
        ],
    },
    primaryColor : "deepBlue",


    shadows: {
        md: '1px 1px 3px rgba(0, 0, 0, .25)',
        xl: '5px 5px 3px rgba(0, 0, 0, .25)',
    },

    headings: {
        fontFamily: 'Open Sans, sans-serif',
        sizes: {
            h1: {fontSize: rem(36)},
        },
    },
});

ReactDOM.createRoot(document.getElementById('root')!).render(
    <MantineProvider theme={mainTheme}>
        <React.StrictMode>
            <App/>
        </React.StrictMode>
    </MantineProvider>

);
