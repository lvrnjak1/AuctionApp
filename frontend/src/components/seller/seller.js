import React, { useState, useEffect, useCallback } from 'react';
import "components/profile/profile.scss";
import "components/seller/seller.scss";
import NewItem from './new_item/newItem';
import { useHistory } from 'react-router-dom';
import Image from 'cloudinary-react/lib/components/Image';
import Transformation from 'cloudinary-react/lib/components/Transformation';
import { getPublicId } from 'util/images_util';
import { getTimeLeft } from 'util/dateTimeService';
import { SELLER_BIDS_ENDPOINT } from 'http/endpoints';
import { getRequest } from 'http/requests';
import { getAuthorizationConfig } from 'util/auth/auth';
import CustomTable from 'components/custom_table/customTable';
import { updateMessage } from 'util/info_div_util';

const getHeadings = () => {
    return [
        <p className="p-reset center-align">Item</p>,
        <p className="p-reset">Name</p>,
        <p className="p-reset">Time Left</p>,
        <p className="p-reset center-align">Start Price</p>,
        <p className="p-reset center-align">No. Bids</p>,
        <p className="p-reset center-align ">Highest Bid</p>,
        ""
    ];
}

function Seller(props) {
    const [page, setPage] = useState(1);
    const [limit, setLimit] = useState(4);
    const [pagination, setPagination] = useState(null);
    const [data, setData] = useState(null);
    const headings = getHeadings();
    const history = useHistory();
    const [isSeller, setIsSeller] = useState(false);

    const handleResponse = useCallback((responseData) => {
        setPagination(responseData.pagination);
        setData(responseData.data);
        setIsSeller(true);
    }, []);

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
            <Transformation height={80} width={80} crop="fill" quality="auto" flags="lossy" />
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
            <p className="p-reset center-align">{`$${el.bidMetadata.yourPrice.toFixed(2)}`}</p>,
            <p className="p-reset center-align">{el.bidMetadata.numberOfBids}</p>,
            <p className="bold-text p-reset blue-text center-align">{`$${el.bidMetadata.highestBid.toFixed(2)}`}</p>,
            <button className="table-button" onClick={() => handleOnViewClick(el)}>View</button>
            ]);
        });

        return rows;
    }

    useEffect(() => {
        async function getData() {
            await getRequest(SELLER_BIDS_ENDPOINT,
                { page, limit },
                (response) => { handleResponse(response.data) },
                (error) => {
                    if (error.response.data.status !== 403) {
                        updateMessage("Something went wrong, try refreshing the page", "error");
                    }
                },
                getAuthorizationConfig()
            );
        }

        getData();
    }, [page, limit, setData])

    return (
        !isSeller ? <form className="seller">
            <div className="form not-seller">
                <p className="form-title">SELL</p>
                <div className="form-content">
                    <div>
                        <img className="shopping-bag-icon" src={process.env.PUBLIC_URL + '/images/shopping-bag.png'} alt="shopping bag logo" />
                        <p>You do not have any items scheduled for sale</p>
                    </div>
                    <button className="btn center-btn" type="submit">{"Start selling >"}</button>
                </div>
            </div>
            {/* <NewItem /> */}
        </form> :
            (data !== null && <CustomTable headings={headings} data={getTableRows()} pagination={pagination} />)
    );
}

export default Seller;
