import React from 'react';
import "components/wishlist/wishlistButton.scss";
import { faHeart } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

function WishlistButton(props) {

    return (
        <button className="wishlist-button">
            {!props.hideText && "Wishlist"}
            <FontAwesomeIcon
                icon={faHeart}
                className={`wishlist-icon ${props.black && "black"} ${!props.hideText && "margin-right"}`} />
        </button>
    );
}

export default WishlistButton;
