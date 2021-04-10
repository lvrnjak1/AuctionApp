import React, { useState, useMemo, useCallback } from 'react';
import "components/profile/profile.scss";
import "components/seller/seller.scss";
import { useHistory } from 'react-router-dom';
import CustomTabs from 'components/tabs/tabs';
import Active from './bids/SellerBids';

function Seller(props) {
    const history = useHistory();
    const [isSeller, setIsSeller] = useState(null);
    const [value, setValue] = useState(0);

    const handleOnViewClick = useCallback((el) => {
        history.push(`shop/item/${el.auction.id}`);
    }, [history]);

    const tabs = useMemo(() => {
        return [
            {
                label: "Active",
                index: 0,
                content: <Active status="active" handleOnViewClick={handleOnViewClick} setIsSeller={setIsSeller} />
            },
            {
                label: "Sold",
                index: 1,
                content: <Active status="closed" handleOnViewClick={handleOnViewClick} setIsSeller={setIsSeller} />
            },
            {
                label: "Scheduled",
                index: 2,
                content: <Active status="scheduled" handleOnViewClick={handleOnViewClick} setIsSeller={setIsSeller} />
            }
        ]
    }, [handleOnViewClick]);

    const handleChange = (event, newValue) => {
        setValue(newValue);
    };

    const handleStartSelling = () => {
        history.push("/new-item");
    }

    return (
        isSeller === false ? <form className="seller">
            <div className="form not-seller">
                <p className="form-title">SELL</p>
                <div className="form-content">
                    <div>
                        <img className="shopping-bag-icon" src={process.env.PUBLIC_URL + '/images/shopping-bag.png'} alt="shopping bag logo" />
                        <p>You do not have any items scheduled for sale</p>
                    </div>
                    <button
                        className="btn center-btn"
                        type="submit"
                        onClick={handleStartSelling}>
                        {"Start selling >"}
                    </button>
                </div>
            </div>
        </form> :
            <div className="tabs-container">
                <CustomTabs tabs={tabs} value={value} handleChange={handleChange} withIcon={false} />
            </div>
    );
}

export default Seller;
