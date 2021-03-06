import React, { useEffect, useState } from 'react'
import { useHistory, useParams } from 'react-router-dom';
import "components/item_page/item.scss";
import { getRequest, postRequest } from 'http/requests';
import { AUCTIONS_ENDPOINT } from 'http/endpoints';
import { Image, Transformation } from 'cloudinary-react';
import CustomTable from 'components/table/table';
import { getAuthorizationConfig, getToken, logoutUser } from 'util/auth/auth';
import { useDispatch } from 'react-redux';
import { resetInfoMessage, setInfoMessage } from 'state/actions/infoMessageActions';

function ItemPage() {

    const { id } = useParams();
    const history = useHistory();
    const [item, setItem] = useState();
    const [largeImageIndex, setLargeImageIndex] = useState(0);
    const [bid, setBid] = useState("");
    const dispatch = useDispatch();

    useEffect(() => {
        async function fetchAuctionById() {
            const endpoint = `${AUCTIONS_ENDPOINT}/${id}`;
            await getRequest(
                endpoint,
                {},
                (res) => setItem(res.data),
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
            dispatch(setInfoMessage("Login before you start bidding", "info"));
            setTimeout(() => dispatch(resetInfoMessage()), 3000);
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

    const bidSuccessHandler = (responseData) => {
        dispatch(setInfoMessage("Congrats! You are the highest bidder!", "success"));
    }

    const bidErrorHandler = (error) => {
        console.log(error.response);
        if (error.response && error.response.status === 422) {
            dispatch(setInfoMessage("There are higher bids than yours. You could give a second try!", "warn"));
        } else if (error.response && error.response.status === 401) {
            dispatch(setInfoMessage("Your session has expired, please login again. We will redirect you in 3 seconds", "error"));
            setTimeout(() => {
                logoutUser();
                history.push("/login");
            });
        } else {
            dispatch(setInfoMessage("Something went wrong, come back again soon!", "error"));
        }

    }

    return (
        item ? <div className="item-page">
            <div className="item">
                <div className="image-gallery">
                    <div className="large-image-container">
                        <Image className="large-image" cloudName="lvrnjak" publicId={item.product.images[largeImageIndex].imageUrl}>
                            <Transformation height="500" width="400" crop="fit" />
                        </Image>
                    </div>
                    <div className="image-grid">
                        {item.product.images.map(image => {
                            const index = item.product.images.indexOf(image);
                            return <button key={image.id} className="image-button" onClick={() => setLargeImageIndex(index)}>
                                <Image className="small-image" cloudName="lvrnjak" publicId={image.imageUrl} >
                                    <Transformation height="150" width="150" crop="fit" />
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
                                onChange={(e) => handleInputChange(e)}
                            />
                            <button className="bid-button" type="submit">{`Place bid >`}</button>
                        </form>
                        <p className="input-label">{`Enter -- or more`}</p>
                        <ul className="bid-info-text">
                            <li>Highest bid:</li>
                            <li>No bids:</li>
                            <li>Time left:</li>
                        </ul>
                    </div>
                    <div className="details">
                        <p className="details-title">Details</p>
                        <p className="details-content">{item.product.description || "There are no details about this item."}</p>
                    </div>
                </div>
            </div>
            <div className="bids-table">
                <CustomTable />
            </div>
        </div> : ""

    );
}

export default ItemPage;
