import React, { useState, useEffect, useCallback } from 'react';
import CustomTable from 'components/custom_table/customTable';
import { getRequest } from 'http/requests';
import { DETAILED_BIDS_ENDPOINT } from 'http/endpoints';
import { getAuthorizationConfig } from 'util/auth/auth';
import { updateMessage } from 'util/info_div_util';
import { getTimeLeft } from 'util/dateTimeService';
import { getPublicId } from 'util/images_util';
import Image from 'cloudinary-react/lib/components/Image';
import Transformation from 'cloudinary-react/lib/components/Transformation';
import { useHistory } from 'react-router-dom';

const getHeadings = () => {
    return [
        <p className="p-reset center-align">Item</p>,
        <p className="p-reset">Name</p>,
        <p className="p-reset">Time Left</p>,
        <p className="p-reset center-align">Your Price</p>,
        <p className="p-reset center-align">No. Bids</p>,
        <p className="p-reset center-align ">Highest Bid</p>,
        ""
    ];
}

function Bids() {
    const [page, setPage] = useState(1);
    const [limit, setLimit] = useState(4);
    const [pagination, setPagination] = useState(null);
    const [data, setData] = useState(null);
    const headings = getHeadings();
    const history = useHistory();

    const handleResponse = useCallback((responseData) => {
        setPagination(responseData.pagination);
        setData(responseData.data);
    }, [])

    const isMyHighestBid = (el) => {
        return el.bidMetadata.yourPrice === el.bidMetadata.highestBid;
    }

    const getImage = (el) => {
        if (el.auction.product.images.length === 0) {
            return <img
                src={process.env.PUBLIC_URL + '/images/image-placeholder.png'}
                alt="product"
                className="image"
            />
        }
        const imageUrl = el.auction.product.images[0].imageUrl;
        return <Image className="image" cloudName="lvrnjak" publicId={getPublicId(imageUrl)} >
            <Transformation height={80} width={80} crop="fill" quality="auto" />
        </Image>
    }

    const handleOnViewClick = (el) => {
        history.push(`shop/item/${el.auction.id}`);
    }

    const getTableRows = () => {
        let rows = [];
        data.forEach(el => {
            rows.push([getImage(el),
            <p className="bold-text p-reset">{el.auction.product.name}</p>,
            <p className="p-reset">{getTimeLeft(el.auction.endDateTime)}</p>,
            <p className={`p-reset center-align ${isMyHighestBid(el) ? "green-text bold-text" : ""}`}>
                {`$${el.bidMetadata.yourPrice.toFixed(2)}`}
            </p>,
            <p className="p-reset center-align">{el.bidMetadata.numberOfBids}</p>,
            <p className={`bold-text p-reset center-align ${isMyHighestBid(el) ? "green-text" : "blue-text"}`}>
                {`$${el.bidMetadata.highestBid.toFixed(2)}`}
            </p>,
            <button className="table-button" onClick={() => handleOnViewClick(el)}>View</button>
            ]);
        });

        return rows;
    }

    useEffect(() => {
        async function getData() {
            await getRequest(DETAILED_BIDS_ENDPOINT,
                { page, limit },
                (response) => { handleResponse(response.data) },
                () => { updateMessage("Something went wrong, try refreshing the page", "error") },
                getAuthorizationConfig()
            );
        }

        getData();
    }, [page, limit, handleResponse])

    return (
        data !== null && <CustomTable headings={headings} data={getTableRows()} pagination={pagination} />
    );
}

export default Bids;
