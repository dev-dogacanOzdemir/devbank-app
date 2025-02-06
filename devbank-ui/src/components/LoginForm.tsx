import React, { useState } from 'react';
import {
    Anchor,
    Button,
    Checkbox,
    Container,
    Group,
    Paper,
    PasswordInput,
    TextInput,
    useMantineTheme,
    Image,
} from '@mantine/core';

const LoginForm: React.FC = () => {
    const theme = useMantineTheme();
    const richGoldElement = theme.colors.richGold[4];
    const [tcNumber, setTcNumber] = useState('');
    const [phoneNumber, setPhoneNumber] = useState('');
    const [password, setPassword] = useState('');

    const handleLogin = async () => {
        try {
            const response = await fetch('http://localhost:2001/api/users/login', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ tcNumber, phoneNumber, password }),
            });

            if (!response.ok) {
                throw new Error('Login failed');
            }

            const data = await response.json();
            console.log('Login successful:', data);

            // Kullanıcı bilgilerini localStorage'a kaydet
            localStorage.setItem('userId', data.id);
            localStorage.setItem('role', data.role);

            // Kullanıcıyı yönlendir
            window.location.href = '/dashboard';
        } catch (error) {
            console.error('Login error:', error);
            alert('Login failed. Please check your credentials.');
        }
    };

    return (
        <div>
            <Container size={420} my={40}>
                <Image src={'/src/assets/devBankLogoBlack.png'} width={250} height={250} className="logo" />
                <Paper withBorder shadow="md" p={30} mt={5} radius="md">
                    <TextInput
                        label="Turkish Identification Number (T.C.)"
                        placeholder="11122233444"
                        required
                        value={tcNumber}
                        onChange={(event) => setTcNumber(event.currentTarget.value)}
                    />
                    <TextInput
                        mt={15}
                        label="Phone Number"
                        placeholder="05xxxxxxxxx"
                        required
                        value={phoneNumber}
                        onChange={(event) => setPhoneNumber(event.currentTarget.value)}
                    />
                    <PasswordInput
                        label="Password"
                        placeholder="Enter your password."
                        required
                        mt="md"
                        value={password}
                        onChange={(event) => setPassword(event.currentTarget.value)}
                    />
                    <Group justify="space-between" mt="lg">
                        <Checkbox label="Remember me" />
                        <Anchor component="button" size="sm">
                            Forgot password?
                        </Anchor>
                    </Group>
                    <Button color={richGoldElement} fullWidth mt="xl" onClick={handleLogin}>
                        Login
                    </Button>
                </Paper>
            </Container>
        </div>
    );
};

export default LoginForm;
