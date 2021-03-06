import React, { useEffect, useState } from 'react';
import "components/wishlist/wishlistButton.scss";
import { faHeart } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { putRequest } from "http/requests";
import { AUCTIONS_ENDPOINT } from 'http/endpoints';
import { updateMessage } from 'util/info_div_util';
import { getAuthorizationConfig, getToken } from 'util/auth/auth';
import { useDispatch, useSelector } from 'react-redux';
import { setPage } from 'state/actions/filterParamsActions';

function WishlistButton(props) {
    const [isInWishlist, setIsInWishlist] = useState(false);
    const [message, setMessage] = useState("");
    const [messageVisible, setMessageVisible] = useState(false);
    const dispatch = useDispatch();
    const filterParams = useSelector(state => state.filterParams)

    useEffect(() => {
        setIsInWishlist(props.inWishlist);
    }, [props.inWishlist]);

    useEffect(() => {
        setMessageVisible(props.message);
    }, [props.message]);

    const setInfoMessage = (mess) => {
        setMessage(mess);
        setTimeout(() => setMessage(""), 2000);
    }

    const handleClick = async (e) => {
        e.preventDefault();
        const endpoint = `${AUCTIONS_ENDPOINT}/${props.id}/wishlist`;
        await putRequest(endpoint,
            {},
            (response) => {
                setIsInWishlist(response.data.wishlist);
                if (response.data.wishlist) { setInfoMessage("Item added to wishlist!"); }
                else { setInfoMessage("Item removed from wishlist!"); }
            },
            () => { updateMessage("Something went wrong, try again", "error") },
            getAuthorizationConfig());

        dispatch(setPage(filterParams.page));
    }

    return (
        getToken() && <div className="wishlist-button-container">
            <button className={`wishlist-button ${isInWishlist && "purple-button"}`} onClick={handleClick}>
                {props.showButtonText && "Wishlist"}
                <FontAwesomeIcon
                    icon={faHeart}
                    className={`wishlist-icon ${props.black && "black"} ${props.showButtonText && "margin-left"}`} />
            </button>
            {props.message && messageVisible && <p className="info-message">{message}</p>}
        </div>
    );
}

export default WishlistButton;
