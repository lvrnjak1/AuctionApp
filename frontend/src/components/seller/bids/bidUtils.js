import Image from "cloudinary-react/lib/components/Image";
import Transformation from "cloudinary-react/lib/components/Transformation";
import { formatDate, getTimeLeft } from "util/dateTimeService";
import { getPublicId } from "util/images_util";

const getImage = (el) => {
    if (el.auction.product.images.length === 0) {
        return <img
            src={process.env.PUBLIC_URL + '/images/image-placeholder.png'}
            alt="product"
            className="image left-margin"
        />
    }
    const imageUrl = el.auction.product.images[0].imageUrl;
    return <Image className="image left-margin" cloudName="lvrnjak" publicId={getPublicId(imageUrl)} >
        <Transformation height={80} width={80} crop="fill" quality="auto" flags="lossy" />
    </Image>
}

const getTableRows = (data, handleOnViewClick, status) => {
    let rows = [];
    if (!data) return rows;
    data.forEach(el => {
        let time;
        const endDate = new Date(el.auction.endDateTime);
        const startDate = new Date(el.auction.startDateTime);
        if (status === "active") time = <p className="p-reset">{getTimeLeft(el.auction.endDateTime)}</p>;
        else if (status === "closed") time = <p className="p-reset">{formatDate(endDate)}</p>;
        else if (status === "scheduled") time = <p className="p-reset">{formatDate(startDate)}</p>;

        rows.push([getImage(el),
        <p className="bold-text p-reset">{el.auction.product.name}</p>,
            time,
        <p className="p-reset center-align">{`$${el.bidMetadata.yourPrice.toFixed(2)}`}</p>,
        <p className="p-reset center-align">{el.bidMetadata.numberOfBids}</p>,
        <p className="bold-text p-reset blue-text center-align">{`$${el.bidMetadata.highestBid.toFixed(2)}`}</p>,
        <button className="table-button" onClick={() => handleOnViewClick(el)}>View</button>
        ]);
    });

    return rows;
};

const getHeadings = (status) => {
    let time;
    if (status === "active") time = <p className="p-reset">Time Left</p>;
    else if (status === "closed") time = <p className="p-reset">Closed on</p>;
    else if (status === "scheduled") time = <p className="p-reset">Starts on</p>;
    return [
        <p className="p-reset left-margin">Item</p>,
        <p className="p-reset">Name</p>,
        time,
        <p className="p-reset center-align">Start Price</p>,
        <p className="p-reset center-align">No. Bids</p>,
        <p className="p-reset center-align ">Highest Bid</p>,
        ""
    ];
}

export {
    getTableRows,
    getHeadings
}