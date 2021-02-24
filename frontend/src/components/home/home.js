import React from 'react';
import Categories from 'components/categories/categories';
import "components/home/home.scss"

function Home() {
    const featuredProduct = {
        "name": "Running shoes",
        "startPrice": 240,
        "description": "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat."
    }
    return (
        <div className="home">
            <div className="top">
                <div className="categories"><Categories /></div>
                <div className="featured-product">
                    <p className="featured-product-name">{featuredProduct.name}</p>
                    <p className="featured-product-price">{`Start from - $${featuredProduct.startPrice}`}</p>
                    <p className="featured-product-desc">{featuredProduct.description}</p>
                    <button className="bid-now-button">{`BID NOW >`}</button>
                </div>
                <div className="featured-product-image">
                    <img
                        src={process.env.PUBLIC_URL + '/images/shoes.jpg'}
                        alt={featuredProduct.name}
                    />
                </div>
            </div>
            <div className="featured-collections">Featured Collections</div>
            <div className="featured-products">Featured Products</div>
            <div className="bottom">Bottom</div>
        </div>
    );
}

export default Home;
