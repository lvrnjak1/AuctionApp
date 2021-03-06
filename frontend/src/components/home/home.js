import React, { useState, useEffect, useCallback } from 'react';
import Categories from 'components/categories/categories';
import "components/home/home.scss"
import ProductGrid from 'components/product_grid/productGrid';
import { getRequest, sendMultipleGetRequests } from 'http/requests';
import {
    FEATURED_PRODUCTS_ENDPOINT,
    FEATURED_CATEGORIES_ENDPOINT,
    CATEGORIES_ENDPOINT,
    AUCTIONS_ENDPOINT
} from 'http/endpoints';
import { useHistory } from 'react-router-dom';
import { updateMessage } from 'util/info_div_util';
import { useDispatch, useSelector } from 'react-redux';
import { setCategories } from 'state/actions/categoriesActions';

function Home() {
    const [featuredProduct, setFeaturedProduct] = useState();
    const [featuredProducts, setFeaturedProducts] = useState();
    const [featuredCategories, setFeaturedCategories] = useState();
    const [products, setProducts] = useState();
    const [newArrivalsActive, setNewArrivalsActive] = useState(true);
    const categories = useSelector(state => state.categories)
    const history = useHistory();
    const dispatch = useDispatch();

    const setCategoriesCallback = useCallback((data) => {
        dispatch(setCategories(data))
    }, [dispatch]);

    useEffect(() => {
        async function fetchData() {
            const requests = [];

            requests.push({
                endpoint: CATEGORIES_ENDPOINT,
                successHandler: (response) => setCategoriesCallback(response.data)
            });

            requests.push({
                endpoint: FEATURED_PRODUCTS_ENDPOINT,
                params: { limit: 1 },
                successHandler: (response) => setFeaturedProduct(response.data.data[0])
            });

            requests.push({
                endpoint: FEATURED_PRODUCTS_ENDPOINT,
                params: { limit: 4 },
                successHandler: (response) => setFeaturedProducts(response.data.data)
            });

            requests.push({
                endpoint: FEATURED_CATEGORIES_ENDPOINT,
                params: { limit: 4 },
                successHandler: (response) => setFeaturedCategories(response.data)
            });

            requests.push({
                endpoint: AUCTIONS_ENDPOINT,
                params: { limit: 4, sort: "DATE", sortOrder: "DESC" },
                successHandler: (response) => setProducts(response.data.auctions.data)
            });

            await sendMultipleGetRequests(requests);
        }

        fetchData();
    }, [setCategoriesCallback])

    const toggleBottomGrid = async (e) => {
        let endpoint = AUCTIONS_ENDPOINT;
        let params;

        if (e.target.id === "new" && !newArrivalsActive) {
            setNewArrivalsActive(true);
            params = { limit: 4, sort: "DATE", sortOrder: "DESC" }
        } else if (e.target.id === "lastChance" && newArrivalsActive) {
            setNewArrivalsActive(false);
            params = { limit: 4, minutesLeft: 1440 }
        }

        if (endpoint) {
            await getRequest(endpoint,
                params,
                (response) => setProducts(response.data.auctions.data),
                () => updateMessage("Try reloading the page.", "error")
            );
        }
    }

    const handleBidNow = () => {
        history.push(`/shop/item/${featuredProduct.id}`);
    }

    return (
        <div className="home">
            {featuredProduct && <div className="top">
                <div className="categories"><Categories items={categories} /></div>
                <div className="featured-product">
                    <p className="featured-product-name">{featuredProduct.product.name}</p>
                    <p className="featured-product-price">{`Start from - $${featuredProduct.startPrice.toFixed(2)}`}</p>
                    <p className="featured-product-desc">{featuredProduct.product.description}</p>
                    <button className="bid-now-button" onClick={() => handleBidNow()}>{`BID NOW >`}</button>
                </div>
                <div className="featured-product-image">
                    <img
                        src={featuredProduct.product.images[0].imageUrl}
                        alt={featuredProduct.name} />
                </div>
            </div>}
            <div className="home-main">
                <div className="featured-collections">
                    <p className="title">Featured Categories</p>
                    <div className="title-line"></div>
                    {featuredCategories && <ProductGrid nrows={1} items={featuredCategories} categories grid small />}
                </div>

                <div className="featured-products">
                    <p className="title">Featured Products</p>
                    <div className="title-line"></div>
                    {featuredProducts && <ProductGrid items={featuredProducts} grid small />}
                </div>

                <div className="bottom">
                    <div className="button-bar">
                        <button id="new" onClick={toggleBottomGrid} autoFocus>New Arrivals</button>
                        <button id="lastChance" onClick={toggleBottomGrid}>Last Chance</button>
                    </div>
                    {products && <ProductGrid items={products} grid small />}
                </div>
            </div>
        </div>
    );
}

export default Home;
