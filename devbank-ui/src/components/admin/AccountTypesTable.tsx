import  { useEffect, useState } from 'react';
import { Table, ScrollArea, Text } from '@mantine/core';
import { AccountType } from '../../types/DashboardTypes.ts'; // Türü import ediyoruz

const AccountTypesTable = () => {
    const [accountTypes, setAccountTypes] = useState<AccountType[]>([]); // Tür tanımlandı

    useEffect(() => {
        const fetchAccountTypes = async () => {
            try {
                const response = await fetch('http://localhost:5000/api/dashboard/accounts-by-type');
                const data: AccountType[] = await response.json(); // Tür burada da belirtiliyor
                setAccountTypes(data);
            } catch (error) {
                console.error('Error fetching account types:', error);
            }
        };

        fetchAccountTypes();
    }, []);

    return (
        <div>
            <Text size="lg" w={500} mb="md">
                Account Types
            </Text>
            <ScrollArea>
                <Table highlightOnHover border={2} withColumnBorders>
                    <thead style={{ backgroundColor: '#f9f9f9' }}>
                    <tr>
                        <th>Account Type</th>
                        <th>Count</th>
                    </tr>
                    </thead>
                    <tbody style={{ textAlign : "center"}}>
                    {accountTypes.map((type) => (
                        <tr key={type._id}>
                            <td>{type._id}</td>
                            <td>{type.count}</td>
                        </tr>
                    ))}
                    </tbody>
                </Table>
            </ScrollArea>
        </div>
    );
};

export default AccountTypesTable;
