import React, { useState, useEffect } from 'react';
import Categories from 'components/categories/categories';
import "components/home/home.scss"
import ProductGrid from 'components/product_grid/productGrid';
import {
    FEATURED_PRODUCTS_ENDPOINT,
    FEATURED_CATEGORIES_ENDPOINT,
    NEW_PRODUCTS_ENDPOINT,
    LAST_CHANCE_PRODUCTS_ENDPOINT
} from 'http/endpoints';
import { getRequest, sendMultipleGetRequests } from 'http/requests';

function Home() {
    const [featuredProduct, setFeaturedProduct] = useState();
    const [featuredProducts, setFeaturedProducts] = useState();
    const [featuredCategories, setFeaturedCategories] = useState();
    const [products, setProducts] = useState();
    const [newArrivalsActive, setNewArrivalsActive] = useState(true);

    useEffect(() => {
        async function fetchData() {
            const requests = [];
            requests.push({
                endpoint: FEATURED_PRODUCTS_ENDPOINT,
                params: { size: 1 },
                successHandler: (response) => setFeaturedProduct(response.data.data[0])
            });

            requests.push({
                endpoint: FEATURED_PRODUCTS_ENDPOINT,
                params: { size: 4 },
                successHandler: (response) => setFeaturedProducts(response.data.data)
            });

            requests.push({
                endpoint: FEATURED_CATEGORIES_ENDPOINT,
                successHandler: (response) => setFeaturedCategories(response.data)
            });

            requests.push({
                endpoint: NEW_PRODUCTS_ENDPOINT,
                params: { size: 8 },
                successHandler: (response) => setProducts(response.data.data)
            });

            await sendMultipleGetRequests(requests);
        }

        fetchData();
    }, [])

    const toggleBottomGrid = async (e) => {
        if (e.target.id === "new" && !newArrivalsActive) {
            setNewArrivalsActive(true);
            await getRequest(NEW_PRODUCTS_ENDPOINT,
                { size: 8 },
                (response) => setProducts(response.data.data)
            );
        } else if (e.target.id === "lastChance" && newArrivalsActive) {
            setNewArrivalsActive(false);
            await getRequest(LAST_CHANCE_PRODUCTS_ENDPOINT,
                { size: 8 },
                (response) => setProducts(response.data.data)
            );
        }
    }

    return (
        <div className="home">
            {featuredProduct ?
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
                            src={featuredProduct.product.images[0].imageUrl}
                            alt={featuredProduct.name}
                        />
                    </div>
                </div>
                : ""}

            <div className="featured-collections">
                <p className="title">Featured Categories</p>
                <div className="title-line"></div>
                {featuredCategories ?
                    <ProductGrid nrows={1} items={featuredCategories} categories />
                    : ""}
            </div>


            <div className="featured-products">
                <p className="title">Featured Products</p>
                <div className="title-line"></div>
                {featuredProducts ?
                    <ProductGrid nrows={1} items={featuredProducts} />
                    : ""}
            </div>


            <div className="bottom">
                <div className="button-bar">
                    <button id="new" onClick={toggleBottomGrid} autoFocus>New Arrivals</button>
                    <button id="lastChance" onClick={toggleBottomGrid}>Last Chance</button>
                </div>
                {products ?
                    <ProductGrid nrows={2} small items={products} />
                    : ""}
            </div>
        </div>
    );
}

export default Home;
