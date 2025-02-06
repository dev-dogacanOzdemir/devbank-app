

import { Container, Grid, Card, Text } from '@mantine/core';
import AccountTypesTable from "../../components/admin/AccountTypesTable.tsx";
import UserRolesTable from "../../components/admin/UserRolesTable.tsx";
import TransferStatusTable from "../../components/admin/TransferStatusTable.tsx";

const AdminDashboardPage = () => {
    return (
        <Container size="lg">
            <Text size="xl" w={700} ml={175} style={{ textAlign: 'center', }} mb="xl">
                Admin Dashboard
            </Text>
            <Grid>
                <Grid.Col span={12}>
                    <Card shadow="sm" padding="lg" radius="md" withBorder>
                        <AccountTypesTable />
                    </Card>
                </Grid.Col>
                <Grid.Col span={12}>
                    <Card shadow="sm" padding="lg" radius="md" withBorder>
                        <UserRolesTable />
                    </Card>
                </Grid.Col>
                <Grid.Col span={12}>
                    <Card shadow="sm" padding="lg" radius="md" withBorder>
                        <TransferStatusTable />
                    </Card>
                </Grid.Col>
            </Grid>
        </Container>
    );
};

export default AdminDashboardPage;
