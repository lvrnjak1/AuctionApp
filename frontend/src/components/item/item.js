import React, { useEffect, useState } from 'react'
import { useHistory, useParams } from 'react-router-dom';
import "components/item/item.scss";
import { getRequest, postRequest } from 'http/requests';
import { AUCTIONS_ENDPOINT } from 'http/endpoints';
import CustomTable from 'components/table/table';
import { getAuthorizationConfig, getToken, getUser, logoutUser } from 'util/auth/auth';
import { useDispatch } from 'react-redux';
import { resetLoggedIn } from 'state/actions/loggedInActions';
import { formatDate, getDifferenceBetweenDates } from 'util/dateTimeService';
import { updateMessage } from 'util/info_div_util';
import CustomImage from 'components/image/image';
import WishlistButton from 'components/wishlist/wishlistButton';

function ItemPage() {

    const { id } = useParams();
    const history = useHistory();
    const [item, setItem] = useState();
    const [largeImageIndex, setLargeImageIndex] = useState(0);
    const [bid, setBid] = useState("");
    const [bids, setBids] = useState([]);
    const [paginationMetaData, setPaginationMetaData] = useState();
    const dispatch = useDispatch();

    const isClosed = () => {
        const now = new Date();
        const end = new Date(item.endDateTime);
        return end - now < 0;
    }

    const isScheduled = (date) => {
        const now = new Date();
        const start = new Date(date);
        return now - start < 0;
    };

    const isSeller = (sellerId) => {
        return getUser() ? getUser().id === (sellerId) : false;
    };

    const getBids = async () => {
        const bidsEndpoint = `${AUCTIONS_ENDPOINT}/${id}/bids`;
        await getRequest(bidsEndpoint,
            { limit: 5 },
            (res) => {
                setBids(res.data.data);
                setPaginationMetaData(res.data.pagination);
            }
        );
    }

    useEffect(() => {
        async function fetchAuctionById() {
            const endpoint = `${AUCTIONS_ENDPOINT}/${id}`;
            await getRequest(endpoint,
                {},
                (res) => {
                    if (isScheduled(res.data.startDateTime) && !isSeller(res.data.sellerId)) {
                        history.push("/404")
                    }
                    setItem(res.data);
                },
                (err) => history.push("/404")
            );

            const bidsEndpoint = `${AUCTIONS_ENDPOINT}/${id}/bids`;
            await getRequest(bidsEndpoint,
                { limit: 5 },
                (res) => {
                    setBids(res.data.data);
                    setPaginationMetaData(res.data.pagination);
                },
                (err) => history.push("/404")
            );
        }

        fetchAuctionById();
    }, [id, history]);

    const handleInputChange = (e) => {
        e.preventDefault();
        setBid(e.target.value);
    }

    const handlePlaceBid = async (e) => {
        e.preventDefault();
        setBid("");
        if (!getToken()) {
            updateMessage("Login before you start bidding!", "info");
        } else {
            await placeBid();
        }
    }

    const placeBid = async () => {
        const endpoint = `${AUCTIONS_ENDPOINT}/${id}/bids`;
        const body = { dateTime: new Date().getTime(), amount: bid }
        const config = getAuthorizationConfig();
        await postRequest(endpoint, body, (res) => bidSuccessHandler(res.data), (err) => bidErrorHandler(err), config);
    }

    const bidSuccessHandler = async (responseData) => {
        await getBids();
        updateMessage("Congrats! You are the highest bidder!", "success");
    }

    const bidErrorHandler = async (error) => {
        if (error.response && error.response.status === 422) {
            const message = error.response.data.type === "LOW_BID" ?
                "There are higher bids than yours. You could give a second try!" :
                error.response.data.message;
            await getBids();
            updateMessage(message, "info");
        } else if (error.response && error.response.status === 401) {
            updateMessage("Your session has expired, please login again. We will redirect you in 3 seconds", "error");
            setTimeout(() => {
                dispatch(resetLoggedIn());
                logoutUser();
                history.push("/login");
            }, 3000);
        } else {
            updateMessage("Try reloading the page.", "error");
        }

    }

    const getTimeLeft = () => {
        const now = new Date();
        const end = new Date(item.endDateTime);
        const { dif, unit } = getDifferenceBetweenDates(now, end);
        return `${dif.toFixed(0)} ${unit}`;
    }

    const getLowestPossibleBidFormated = () => {
        if (bids.length > 0) {
            return `Enter more than $${bids[0].amount.toFixed(2)}`;
        } else {
            return `Enter at least $${item.startPrice.toFixed(2)}`;
        }
    }

    const getInactiveMessage = () => {
        if (isClosed()) return `Auction closed on ${formatDate(new Date(item.endDateTime))}.`;
        if (isScheduled(item.startDateTime)) return `Auction starts on ${formatDate(new Date(item.startDateTime))}.`;
        if (isSeller(item.sellerId)) return `You are selling this item.`;
    }

    return (
        item ? <div className="item-page">
            <div className="item">
                <div className="image-gallery">
                    <div className="large-image-container">
                        <CustomImage styles="large-image"
                            url={item.product.images[largeImageIndex].imageUrl}
                            height={500}
                            width={400}
                            crop="scale"
                            altText={"Item large"} />
                    </div>
                    <div className="image-grid">
                        {item.product.images.map(image => {
                            const index = item.product.images.indexOf(image);
                            return <button key={image.id} className="image-button" onClick={() => setLargeImageIndex(index)}>
                                <CustomImage styles="small-image"
                                    url={image.imageUrl}
                                    height={150}
                                    width={150}
                                    crop="scale"
                                    altText={"Item small"} />
                            </button>
                        })}
                    </div>
                </div>
                <div className="item-info">
                    <p className="name">{item.product.name}</p>
                    <p className="price">{`Start from - $${item.startPrice.toFixed(2)}`}</p>
                    <div className="bid-info">
                        {!isClosed() && !isScheduled(item.startDateTime) && !isSeller(item.sellerId) ? <>
                            <form onSubmit={handlePlaceBid}>
                                <input
                                    className="bid-input"
                                    required
                                    type="number"
                                    step=".01"
                                    value={bid}
                                    min={0}
                                    max={1000000}
                                    onChange={(e) => handleInputChange(e)}
                                />
                                <button className="bid-button" type="submit">{`Place bid >`}</button>
                            </form>
                            <p className="input-label">
                                {getLowestPossibleBidFormated()}
                            </p>
                        </> : <p>{getInactiveMessage()}</p>
                        }
                        {!isScheduled(item.startDateTime) && <ul className="bid-info-text">
                            <li>{`Highest bid:`}
                                <p className="highest-bid"> {`${bids.length > 0 ? `$${bids[0].amount.toFixed(2)}` : ""}`}</p>
                            </li>
                            <li>{`No bids: ${paginationMetaData ? paginationMetaData.available : "0"}`}</li>
                            {!isClosed() && <li>{`Time left: ${getTimeLeft()}`}</li>}
                        </ul>
                        }
                    </div>
                    <WishlistButton id={id} inWishlist={item.wishlist} message={true} />
                    <div className="details">
                        <p className="details-title">Details</p>
                        <p className="details-content">{item.product.description || "There are no details about this item."}</p>
                    </div>
                </div>
            </div>
            <div className="bids-table">
                <CustomTable items={bids} />
            </div>
        </div> : ""

    );
}

export default ItemPage;
