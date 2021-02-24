import React, { useState, useEffect } from 'react';
import Categories from 'components/categories/categories';
import "components/home/home.scss"
import ProductGrid from 'components/product_grid/productGrid';
import { FEATURED_PRODUCTS_ENDPOINT } from 'http/endpoints';
import { sendMultipleGetRequests } from 'http/requests';

function Home() {
    const [featuredProduct, setFeaturedProduct] = useState();

    useEffect(() => {
        async function fetchData() {
            const requests = [];
            requests.push({
                endpoint: FEATURED_PRODUCTS_ENDPOINT,
                params: { size: 1 },
                successHandler: (response) => setFeaturedProduct(response.data.data[0])
            });

            await sendMultipleGetRequests(requests);
        }
        console.log("sent");
        fetchData();

    }, [])

    return (
        featuredProduct ?
            <div className="home">
                <div className="top">
                    <div className="categories"><Categories /></div>
                    <div className="featured-product">
                        <p className="featured-product-name">{featuredProduct.product.name}</p>
                        <p className="featured-product-price">{`Start from - $${featuredProduct.startPrice.toFixed(2)}`}</p>
                        <p className="featured-product-desc">{featuredProduct.product.description}</p>
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
                    <div className="button-bar">
                        <button>New Arrivals</button>
                        <button>Last Chance</button>
                    </div>
                    <ProductGrid nrows={2} small />
                </div>
            </div>
            : ""
    );
}

export default Home;
