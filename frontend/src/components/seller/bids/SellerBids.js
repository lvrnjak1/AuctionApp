import React, { useEffect, useState, useCallback } from 'react';
import { getHeadings, getTableRows } from './bidUtils';
import { SELLER_BIDS_ENDPOINT } from 'http/endpoints';
import { getRequest } from 'http/requests';
import { updateMessage } from 'util/info_div_util';
import { getAuthorizationConfig } from 'util/auth/auth';
import CustomTable from 'components/custom_table/customTable';

function SellerBids({ setIsSeller, status, handleOnViewClick }) {
    const [page, setPage] = useState(1);
    const [limit, setLimit] = useState(10);
    const [pagination, setPagination] = useState(null);
    const [data, setData] = useState(null);
    const headings = getHeadings(status);

    const handleResponse = useCallback((responseData) => {
        setPagination(responseData.pagination);
        setData(responseData.data);
        setIsSeller(true);
    }, [setIsSeller]);

    useEffect(() => {
        let isSubscribed = true;
        async function getData() {
            await getRequest(SELLER_BIDS_ENDPOINT,
                { page, limit, status },
                (response) => { if (isSubscribed) handleResponse(response.data) },
                (error) => {
                    if (!isSubscribed) return;
                    if (error.response && error.response.data.status !== 403) {
                        updateMessage("Something went wrong, try refreshing the page", "error");
                    } else {
                        setIsSeller(false);
                    }
                },
                getAuthorizationConfig()
            );
        }
        getData();
        return () => (isSubscribed = false)
    }, [page, limit, handleResponse, status, setIsSeller]);

    return (
        data !== null && <CustomTable
            headings={headings}
            data={getTableRows(data, handleOnViewClick, status)}
            pagination={pagination}
            onChangePage={() => setPage(page + 1)}
            onChangeRowsPerPage={(event) => {
                setLimit(parseInt(event.target.value));
                setPage(1)
            }}
            rowsPerPage={limit}
        />
    );
}

export default SellerBids;
