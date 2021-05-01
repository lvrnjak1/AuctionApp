import React from 'react';
import "components/product/product.scss";
import { useHistory } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faGavel } from "@fortawesome/free-solid-svg-icons"
import CustomImage from 'components/image/image';
import { useDispatch } from 'react-redux';
import { addCategoryId } from 'state/actions/filterParamsActions';
import WishlistButton from 'components/wishlist/wishlistButton';
import { getToken } from 'util/auth/auth';

function Product(props) {
    const history = useHistory();
    const dispatch = useDispatch();

    const handleProductClick = (id) => {
        if (!props.categories) {
            history.push(`shop/item/${id}`);
        } else {
            dispatch(addCategoryId(id))
            history.push("/shop");
        }
    }

    const getButtonGroup = (iconBlack, hideButtonText) => {
        return <div className="button-group">
            <WishlistButton id={props.product.id} black={iconBlack} hideText={hideButtonText} inWishlist={props.product.wishlist} />
            <button className="bid-button" onClick={() => handleProductClick(props.product.id, props.product.name)}>
                {(!hideButtonText || !getToken()) && "Bid"} <FontAwesomeIcon icon={faGavel} className={`button-icon ${iconBlack ? "black" : ""}`} />
            </button>
        </div>
    }

    return (
        props.product &&
        <div className={`${props.grid ? "product-grid" : "product-list"} ${props.small && "small"}`}>
            <div className="image-button overlay-container" onClick={() => {
                if (!props.grid) handleProductClick(props.product.id)
            }}>
                <CustomImage className="overlayed-image" styles="product-image"
                    url={props.product.imageUrl}
                    height={250}
                    width={200}
                    crop="fill"
                    altText={"Item in grid"} />
                <div className="overlay">{getButtonGroup(true, true)}</div>
            </div>
            <div className="about">
                <p className="name">{props.product.name}</p>
                {!props.grid && <p className="description">{props.product.description}</p>}
                {props.product.price && <p className="price">{`Start from - $${props.product.price}`}</p>}
                {!props.grid && getButtonGroup()}
            </div>
        </div>
    );
}

export default Product;
