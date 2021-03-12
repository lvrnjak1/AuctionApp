import React, { useEffect, useState } from 'react'
import { useHistory, useParams } from 'react-router-dom';
import "components/item/item.scss";
import { getRequest, postRequest } from 'http/requests';
import { AUCTIONS_ENDPOINT } from 'http/endpoints';
import { Image, Transformation } from 'cloudinary-react';
import CustomTable from 'components/table/table';
import { getAuthorizationConfig, getToken, logoutUser } from 'util/auth/auth';
import { useDispatch } from 'react-redux';
import { resetLoggedIn } from 'state/actions/loggedInActions';
import { getDifferenceBetweenDates } from 'util/dateTimeService';
import { updateMessage } from 'util/info_div_util';
import { getPublicId } from 'util/images_util';

function ItemPage() {

    const { id } = useParams();
    const history = useHistory();
    const [item, setItem] = useState();
    const [largeImageIndex, setLargeImageIndex] = useState(0);
    const [bid, setBid] = useState("");
    const [bids, setBids] = useState([]);
    const [paginationMetaData, setPaginationMetaData] = useState();
    const dispatch = useDispatch();

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
                (res) => setItem(res.data),
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

    const bidErrorHandler = (error) => {
        if (error.response && error.response.status === 422) {
            const message = error.response.data.type === "LOW_BID" ?
                "There are higher bids than yours. You could give a second try!" :
                error.response.data.message;
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

    return (
        item ? <div className="item-page">
            <div className="item">
                <div className="image-gallery">
                    <div className="large-image-container">
                        <Image
                            className="large-image"
                            cloudName="lvrnjak"
                            publicId={getPublicId(item.product.images[largeImageIndex].imageUrl)}
                        >
                            <Transformation height={500} width={400} crop="scale" quality="auto" flags="lossy" />
                        </Image>
                    </div>
                    <div className="image-grid">
                        {item.product.images.map(image => {
                            const index = item.product.images.indexOf(image);
                            return <button key={image.id} className="image-button" onClick={() => setLargeImageIndex(index)}>
                                <Image className="small-image" cloudName="lvrnjak" publicId={getPublicId(image.imageUrl)} >
                                    <Transformation height={150} width={150} crop="scale" quality="auto" flags="lossy" />
                                </Image>
                            </button>
                        })}
                    </div>
                </div>
                <div className="item-info">
                    <p className="name">{item.product.name}</p>
                    <p className="price">{`Start from - $${item.startPrice.toFixed(2)}`}</p>
                    <div className="bid-info">
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
                        <ul className="bid-info-text">
                            <li>{`Highest bid:`}
                                <p className="highest-bid"> {`${bids.length > 0 ? `$${bids[0].amount.toFixed(2)}` : ""}`}</p>
                            </li>
                            <li>{`No bids: ${paginationMetaData ? paginationMetaData.available : "0"}`}</li>
                            <li>{`Time left: ${getTimeLeft()}`}</li>
                        </ul>
                    </div>
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
