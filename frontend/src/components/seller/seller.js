import React, { useState } from 'react';
import "components/profile/profile.scss";
import "components/seller/seller.scss";

function Seller(props) {
    const [productName, setProductName] = useState("");
    const [category, setCategory] = useState("");
    const [subcategory, setSubcategory] = useState("");
    const [productDescription, setProductDescription] = useState("");
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
            <div className="form">
                <p className="form-title">INFORMATION ABOUT PRODUCT</p>
                <div className="form-content">
                    <div className="input-label-group">
                        <label>What do you sell?</label>
                        <input
                            className="input"
                            required
                            type="text"
                            value={productName}
                            onChange={e => setProductName(e.target.value)} />
                    </div>
                    <div className="flex-form-group">
                        <div className="input-label-group">
                            <select className="input" value={category} onChange={e => setCategory(e.target.value)}>
                                <option value="" disabled>Category</option>
                                <option value="M">Male</option>
                                <option value="F">Female</option>
                            </select>
                        </div>
                        <div className="input-label-group">
                            <select className="input" value={subcategory} onChange={e => setSubcategory(e.target.value)}>
                                <option value="" disabled>Subcategory</option>
                                <option value="M">Male</option>
                                <option value="F">Female</option>
                            </select>
                        </div>
                    </div>
                    <div className="input-label-group">
                        <label>Description</label>
                        <textarea
                            maxLength="700"
                            className="textarea"
                            value={productDescription}
                            onChange={e => setProductDescription(e.target.value)} />
                        <p className="textarea-info">100 words (700 characters)</p>
                    </div>
                </div>
            </div>
        </form>
    );
}

export default Seller;
