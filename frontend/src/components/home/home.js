import React from 'react';
import Categories from 'components/categories/categories';
import "components/home/home.scss"
import ProductGrid from 'components/product_grid/productGrid';

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
                    <p className="featured-product-price">{`Start from - $${featuredProduct.startPrice.toFixed(2)}`}</p>
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
            <div className="featured-collections">
                <p className="title">Featured Categories</p>
                <div className="title-line"></div>
                <ProductGrid nrows={1} categories />
            </div>
            <div className="featured-products">
                <p className="title">Featured Products</p>
                <div className="title-line"></div>
                <ProductGrid nrows={1} />
            </div>
            <div className="bottom">
                <button>New Arrivals</button>
                <button>Last Chance</button>
            </div>
        </div>
    );
}

export default Home;
