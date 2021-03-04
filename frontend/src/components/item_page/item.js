import React, { useEffect, useState } from 'react'
import { useHistory, useParams } from 'react-router-dom';
import "components/item_page/item.scss";
import { getRequest } from 'http/requests';
import { AUCTIONS_ENDPOINT } from 'http/endpoints';
import { Image, Transformation } from 'cloudinary-react';

function ItemPage() {

    const { id } = useParams();
    const history = useHistory();
    const [item, setItem] = useState();
    const [largeImageIndex, setLargeImageIndex] = useState(0);

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
                    <div className="bid-info"></div>
                    <div className="details">
                        <p className="details-title">Details</p>
                        <p className="details-content">{item.product.description || "There are no details about this item."}</p>
                    </div>
                </div>
            </div>
            <div className="bids-table"></div>
        </div> : ""

    );
}

export default ItemPage;
