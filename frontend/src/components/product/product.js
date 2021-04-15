import React from 'react';
import "components/product/product.scss";
import { useHistory } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faGavel } from "@fortawesome/free-solid-svg-icons"
import CustomImage from 'components/image/image';
import { useDispatch } from 'react-redux';
import { addCategoryId } from 'state/actions/filterParamsActions';

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

    return (
        props.product &&
        <div className={`${props.grid ? "product-grid" : "product-list"} ${props.small && "small"}`}>
            <button className="image-button" onClick={() => handleProductClick(props.product.id)}>
                <CustomImage styles="product-image"
                    url={props.product.imageUrl}
                    height={250}
                    width={200}
                    crop="fill"
                    altText={"Item in grid"} />
            </button>
            <div className="about">
                <p className="name">{props.product.name}</p>
                {!props.grid && <p className="description">{props.product.description}</p>}
                {props.product.price && <p className="price">{`Start from - $${props.product.price}`}</p>}
                {!props.grid && <div className="button-group">
                    <button className="bid-button" onClick={() => handleProductClick(props.product.id, props.product.name)}>
                        Bid <FontAwesomeIcon icon={faGavel} className="button-icon" />
                    </button>
                </div>
                }
            </div>
        </div>
    );
}

export default Product;
