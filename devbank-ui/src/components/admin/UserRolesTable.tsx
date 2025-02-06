import  { useEffect, useState } from 'react';
import { Table, ScrollArea, Text } from '@mantine/core';
import { UserRole } from '../../types/DashboardTypes.ts'; // Türü import ediyoruz

const UserRolesTable = () => {
    const [userRoles, setUserRoles] = useState<UserRole[]>([]);

    useEffect(() => {
        const fetchUserRoles = async () => {
            try {
                const response = await fetch('http://localhost:5000/api/dashboard/users-by-role');
                const data: UserRole[] = await response.json();
                setUserRoles(data);
            } catch (error) {
                console.error('Error fetching user roles:', error);
            }
        };

        fetchUserRoles();
    }, []);

    return (
        <div>
            <Text size="lg" w={500} mb="md">
                User Roles
            </Text>
            <ScrollArea>
                <Table highlightOnHover border={2} withColumnBorders>
                    <thead style={{ backgroundColor: '#f9f9f9' }}>
                    <tr>
                        <th>Role</th>
                        <th>Count</th>
                    </tr>
                    </thead>
                    <tbody style={{ textAlign : "center"}}>
                    {userRoles.map((role) => (
                        <tr key={role._id}>
                            <td>{role._id}</td>
                            <td>{role.count}</td>
                        </tr>
                    ))}
                    </tbody>
                </Table>
            </ScrollArea>
        </div>
    );
};

export default UserRolesTable;
