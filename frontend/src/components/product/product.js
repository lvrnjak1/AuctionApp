import React from 'react';
import "components/product/product.scss";
import { useHistory } from 'react-router-dom';
import { getPublicId } from 'util/images_util';
import Transformation from 'cloudinary-react/lib/components/Transformation';
import Image from 'cloudinary-react/lib/components/Image';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faGavel } from "@fortawesome/free-solid-svg-icons"

function Product(props) {
    const history = useHistory();

    const handleProductClick = (id, name) => {
        if (!props.categories) {
            history.push(`shop/item/${id}`);
        } else {
            history.push("/shop", { categoryId: id, categoryName: name });
        }
    }

    return (
        props.product &&
        <div className={`${props.grid ? "product-grid" : "product-list"} ${props.small && "small"}`}>
            <button className="image-button" onClick={() => handleProductClick(props.product.id, props.product.name)}>
                <Image className="product-image" cloudName="lvrnjak" publicId={getPublicId(props.product.imageUrl)} >
                    <Transformation height={250} width={200} crop="fill" quality="auto" flags="lossy" />
                </Image>
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
