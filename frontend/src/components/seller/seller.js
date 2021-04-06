import React, { useState } from 'react';
import "components/profile/profile.scss";
import "components/seller/seller.scss";
import NewItem from './new_item/newItem';

function Seller(props) {
    return (
        <form className="seller">
            <div className="form not-seller">
                <p className="form-title">SELL</p>
                <div className="form-content">
                    <div>
                        <img className="shopping-bag-icon" src={process.env.PUBLIC_URL + '/images/shopping-bag.png'} alt="shopping bag logo" />
                        <p>You do not have any items scheduled for sale</p>
                    </div>
                    <button className="btn center-btn" type="submit">{"Start selling >"}</button>
                </div>
            </div>
            <NewItem />
        </form>
    );
}

export default Seller;
