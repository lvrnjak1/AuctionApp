import React, { useEffect, useState } from 'react';
import "components/wishlist/wishlistButton.scss";
import { faHeart } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { putRequest } from "http/requests";
import { AUCTIONS_ENDPOINT } from 'http/endpoints';
import { updateMessage } from 'util/info_div_util';
import { getAuthorizationConfig } from 'util/auth/auth';

function WishlistButton(props) {
    const [isInWishlist, setIsInWishlist] = useState(false);

    useEffect(() => {
        setIsInWishlist(props.inWishlist);
    }, [props.inWishlist])

    const handleClick = async (e) => {
        e.preventDefault();
        const endpoint = `${AUCTIONS_ENDPOINT}/${props.id}/wishlist`;
        await putRequest(endpoint,
            {},
            (response) => { setIsInWishlist(response.data.wishlist) },
            () => { updateMessage("Something went wrong, try again", "error") },
            getAuthorizationConfig());
    }

    return (
        <button className={`wishlist-button ${isInWishlist && "purple-button"}`} onClick={handleClick}>
            {!props.hideText && "Wishlist"}
            <FontAwesomeIcon
                icon={faHeart}
                className={`wishlist-icon ${props.black && "black"} ${!props.hideText && "margin-left"}`} />
        </button>
    );
}

export default WishlistButton;
