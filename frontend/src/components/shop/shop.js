import React, { useState, useEffect } from 'react';
import "components/shop/shop.scss";
import Categories from 'components/categories/categories';
import ProductGrid from 'components/product_grid/productGrid';
import { AUCTIONS_ENDPOINT, CATEGORIES_ENDPOINT } from 'http/endpoints';
import { getRequest } from 'http/requests';
import { MenuItem, Select } from '@material-ui/core';

function Shop() {
    const [categories, setCategories] = useState([]);
    const [products, setProducts] = useState([]);
    const [hasNext, setHasNext] = useState(true);
    const [filterParams, setFilterParams] = useState({ categoryId: null, sort: null, page: 1, limit: 3 });

    useEffect(() => {
        async function fetchCategories() {
            await getRequest(CATEGORIES_ENDPOINT, {}, (response) => setCategories(response.data));
        }

        fetchCategories();
    }, []);

    const handleNewProducts = (responseData) => {
        let oldProducts = products;
        let newProducts;

        if (filterParams.page === 1) {
            newProducts = responseData.data;
        } else {
            newProducts = oldProducts.concat(responseData.data);
            newProducts = [...new Map(newProducts.map(p => [p.id, p])).values()];
        }

        setProducts(newProducts);
        if (!responseData.pagination.hasNext) {
            setHasNext(false);
            setTimeout(() => setHasNext(true), 5000);
        }
    }

    useEffect(() => {
        async function fetchProducts() {
            await getRequest(AUCTIONS_ENDPOINT, filterParams, (response) => handleNewProducts(response.data));
        }

        fetchProducts();
    }, [filterParams]);

    const loadMore = () => {
        setFilterParams({ ...filterParams, page: filterParams.page + 1 });
    };

    const setCategoryFilter = (categoryId) => {
        setFilterParams({ ...filterParams, categoryId, page: 1 });
    }

    const setSortCriteria = (criteria) => {
        const sort = criteria !== "DEFAULT" ? criteria : null;
        setFilterParams({ ...filterParams, sort, page: 1 });
    }

    return (
        <div className="shop-page">
            <div className="side-bar">
                <Categories expandable items={categories} border onFilter={setCategoryFilter} />
            </div>
            <div className="content">
                <Select
                    value={filterParams.sort || "DEFAULT"}
                    onChange={(e) => setSortCriteria(e.target.value)}
                    className="sort-select"
                >
                    <MenuItem value="DEFAULT">Default Sorting</MenuItem>
                    <MenuItem value="PRICE">Sort by price</MenuItem>
                    <MenuItem value="DATE">Sort by newness</MenuItem>
                </Select>
                <ProductGrid nrows={Math.ceil(products.length / 3)} items={products} col3 />
                <button onClick={loadMore}>Explore more</button>
                <p className={`${!hasNext && products.length > 0 ? "visible-text" : "invisible-text"}`}>
                    No more products to show
                </p>
            </div>
        </div>
    );
}

export default Shop;
