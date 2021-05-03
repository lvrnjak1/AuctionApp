import React, { useCallback, useEffect, useState } from 'react';
import "components/wishlist/wishlist.scss";
import { useHistory } from 'react-router-dom';
import CustomImage from 'components/image/image';
import CustomTable from 'components/custom_table/customTable';
import { WISHLIST_ENDPOINT } from 'http/endpoints';
import { getRequest } from 'http/requests';
import { updateMessage } from 'util/info_div_util';
import { getAuthorizationConfig } from 'util/auth/auth';
import { getTimeLeft } from 'util/dateTimeService';

const getHeadings = () => {
    return [
        <p className="p-reset left-margin">Item</p>,
        <p className="p-reset">Name</p>,
        <p className="p-reset">Time Left</p>,
        <p className="p-reset center-align ">Highest Bid</p>,
        <p className="p-reset center-align ">Status</p>,
        ""
    ];
}

function Wishlist(props) {
    const [page, setPage] = useState(1);
    const [limit, setLimit] = useState(10);
    const [pagination, setPagination] = useState(null);
    const [data, setData] = useState(null);
    const headings = getHeadings();
    const history = useHistory();

    const handleResponse = useCallback((responseData) => {
        setPagination(responseData.pagination);
        setData(responseData.data);
    }, []);

    const isMyHighestBid = (el) => {
        return el.yourPrice === el.highestBid && el.highestBid !== 0;
    }

    const handleOnViewClick = (el) => {
        history.push(`shop/item/${el.auction.id}`);
    }

    const getImage = (el) => {
        if (el.auction.product.images.length === 0) {
            return <img
                src={process.env.PUBLIC_URL + '/images/image-placeholder.png'}
                alt="product"
                className="image left-margin"
            />
        }
        const imageUrl = el.auction.product.images[0].imageUrl;
        return <CustomImage styles="image left-margin"
            url={imageUrl}
            height={80}
            width={80}
            crop="fill"
            altText={"Item in table"} />
    };

    const getTableRows = () => {
        let rows = [];
        data.forEach(el => {
            const endDate = new Date(el.auction.endDateTime);
            let time;
            let status = "OPEN";
            if (endDate - new Date() < 0) {
                time = "0s";
                status = "CLOSED";
            } else {
                time = getTimeLeft(el.auction.endDateTime);
            }
            rows.push([getImage(el),
            <p className="bold-text p-reset">{el.auction.product.name}</p>,
            <p className="p-reset">{time}</p>,
            <p className={`bold-text p-reset center-align ${isMyHighestBid(el) ? "green-text" : "blue-text"}`}>
                {`$${el.highestBid.toFixed(2)}`}
            </p>,
            <p className={`center-align status ${status === "CLOSED" && "status-closed"}`}>{status}</p>,
            <button className="table-button" onClick={() => handleOnViewClick(el)}>View</button>
            ]);
        });

        return rows;
    }

    useEffect(() => {
        async function getData() {
            await getRequest(WISHLIST_ENDPOINT,
                { page, limit },
                (response) => { handleResponse(response.data); },
                () => { updateMessage("Something went wrong, try refreshing the page", "error") },
                getAuthorizationConfig()
            );
        }

        getData();
    }, [page, limit, handleResponse])


    return (
        data !== null && <div className="custom-table-container" >
            <CustomTable
                headings={headings}
                data={getTableRows()}
                pagination={pagination}
                onChangePage={() => setPage(page + 1)}
                onChangeRowsPerPage={(event) => {
                    setLimit(parseInt(event.target.value));
                    setPage(1)
                }}
                rowsPerPage={limit} />
        </div>
    );
}

export default Wishlist;
