import React, { useState, useEffect } from 'react';
import "components/shop/shop.scss";
import Categories from 'components/categories/categories';
import ProductGrid from 'components/product_grid/productGrid';
import { AUCTIONS_ENDPOINT, CATEGORIES_ENDPOINT } from 'http/endpoints';
import { getRequest, sendMultipleGetRequests } from 'http/requests';

function Shop() {
    const [categories, setCategories] = useState([]);
    const [products, setProducts] = useState([]);
    const [page, setPage] = useState(0);
    const [limit] = useState(3);
    const [hasNext, setHasNext] = useState(true);

    const handleNewProducts = (responseData) => {
        let oldProducts = products;
        const newProducts = oldProducts.concat(responseData.data);
        setProducts(newProducts);
        if (!responseData.pagination.hasNext) {
            setHasNext(false);
            setTimeout(() => setHasNext(true), 5000);
        }
    }

    const loadMore = async () => {
        setPage(page + 1);
        await fetchProducts();
    };

    const fetchProducts = async () => {
        await getRequest(AUCTIONS_ENDPOINT,
            { page, limit },
            (response) => handleNewProducts(response.data)
        );
    }

    useEffect(() => {
        async function fetchData() {
            const requests = [];
            requests.push({
                endpoint: CATEGORIES_ENDPOINT,
                successHandler: (response) => setCategories(response.data)
            });


            requests.push({
                endpoint: AUCTIONS_ENDPOINT,
                params: { page, limit },
                successHandler: (response) => handleNewProducts(response.data)
            });

            await sendMultipleGetRequests(requests);
        }

        fetchData();
    }, [limit, page]);

    return (
        <div className="shop-page">
            <div className="side-bar">
                <Categories expandable items={categories} border />
            </div>
            <div className="content">
                <ProductGrid nrows={Math.ceil(products.length / 3)} items={products} col3 />
                <button onClick={loadMore}>Explore more</button>
                <p className={`${!hasNext ? "visible-text" : "invisible-text"}`}>No more products to show</p>
            </div>
        </div>
    );
}

export default Shop;
