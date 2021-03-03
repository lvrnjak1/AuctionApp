import React, { useState, useEffect } from 'react';
import "components/shop/shop.scss";
import Categories from 'components/categories/categories';
import ProductGrid from 'components/product_grid/productGrid';
import { AUCTIONS_ENDPOINT, CATEGORIES_ENDPOINT } from 'http/endpoints';
import { getRequest } from 'http/requests';
import { MenuItem, Select } from '@material-ui/core';
import { faSortAmountUp, faSortAmountDown } from "@fortawesome/free-solid-svg-icons"
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { useDispatch } from 'react-redux';
import { initializeCurrentCategory, resetCurrentCategory } from 'state/actions/currentCategoryActions';
import { setInfoMessage } from 'state/actions/infoMessageActions';

function Shop() {
    const [categories, setCategories] = useState([]);
    const [products, setProducts] = useState([]);
    const [hasNext, setHasNext] = useState(true);
    const [filterParams, setFilterParams] = useState({ categoryId: null, sort: null, sortOrder: "ASC", page: 1, limit: 3 });
    const dispatch = useDispatch();

    const errorHandler = () => {
        dispatch(setInfoMessage("Something went wrong, come back soon", "error"));
    }

    useEffect(() => {
        async function fetchCategories() {
            await getRequest(CATEGORIES_ENDPOINT, {}, (response) => setCategories(response.data), errorHandler);
        }

        fetchCategories();
        dispatch(initializeCurrentCategory());

        return () => dispatch(resetCurrentCategory(false));
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
        setHasNext(responseData.pagination.hasNext);
    }

    useEffect(() => {
        async function fetchProducts() {
            await getRequest(AUCTIONS_ENDPOINT, filterParams, (response) => handleNewProducts(response.data), errorHandler);
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
        const sortOrder = criteria !== "DEFAULT" ? filterParams.sortOrder : "ASC";
        setFilterParams({ ...filterParams, sort, sortOrder, page: 1 });
    }

    const setSortOrder = (order) => {
        setFilterParams({ ...filterParams, sortOrder: order, page: 1 });
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
                <button
                    className={`sort-order-button ${filterParams.sortOrder === "ASC" && "active"}`}
                    disabled={!filterParams.sort}
                    onClick={() => setSortOrder("ASC")}
                >
                    <FontAwesomeIcon icon={faSortAmountUp} />
                </button>
                <button
                    className={`sort-order-button ${filterParams.sortOrder === "DESC" && "active"}`}
                    disabled={!filterParams.sort}
                    value="DESC"
                    onClick={() => setSortOrder("DESC")}
                >
                    <FontAwesomeIcon icon={faSortAmountDown} />
                </button>
                <div className="center-content">
                    <ProductGrid nrows={Math.ceil(products.length / 3)} items={products} col3 />
                    {hasNext && <button onClick={loadMore}>Explore more</button>}
                </div>
            </div>
        </div>
    );
}

export default Shop;
