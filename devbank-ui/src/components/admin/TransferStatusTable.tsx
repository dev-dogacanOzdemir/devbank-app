import  { useEffect, useState } from 'react';
import { Table, ScrollArea, Text } from '@mantine/core';
import { TransferStatus } from '../../types/DashboardTypes.ts';; // Türü import ediyoruz

const TransferStatusTable = () => {
    const [transferStatuses, setTransferStatuses] = useState<TransferStatus[]>([]);

    useEffect(() => {
        const fetchTransferStatuses = async () => {
            try {
                const response = await fetch('http://localhost:5000/api/dashboard/transfers-by-status');
                const data: TransferStatus[] = await response.json();
                setTransferStatuses(data);
            } catch (error) {
                console.error('Error fetching transfer statuses:', error);
            }
        };

        fetchTransferStatuses();
    }, []);

    return (
        <div>
            <Text size="lg" w={500} mb="md">
                Transfer Status
            </Text>
            <ScrollArea>
                <Table highlightOnHover border={2} withColumnBorders>
                    <thead style={{ backgroundColor: '#f9f9f9' }}>
                    <tr>
                        <th>Status</th>
                        <th>Count</th>
                    </tr>
                    </thead>
                    <tbody style={{ textAlign : "center"}}>
                    {transferStatuses.map((status) => (
                        <tr key={status._id}>
                            <td>{status._id}</td>
                            <td>{status.count}</td>
                        </tr>
                    ))}
                    </tbody>
                </Table>
            </ScrollArea>
        </div>
    );
};

export default TransferStatusTable;
