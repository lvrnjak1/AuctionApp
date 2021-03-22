import React, { useState, useEffect, useCallback } from 'react';
import "components/shop/shop.scss";
import Categories from 'components/categories/categories';
import ProductGrid from 'components/product_grid/productGrid';
import { AUCTIONS_ENDPOINT, CATEGORIES_ENDPOINT } from 'http/endpoints';
import { getRequest } from 'http/requests';
import { MenuItem, Select } from '@material-ui/core';
import { faSortAmountUp, faSortAmountDown } from "@fortawesome/free-solid-svg-icons"
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { useDispatch, useSelector } from 'react-redux';
import { resetCurrentCategory } from 'state/actions/currentCategoryActions';
import { updateMessage } from 'util/info_div_util';
import { resetFilterParams, setCategoryId, setPage, setSort, setSortOrder } from 'state/actions/filterParamsActions';
import { setCategories } from 'state/actions/categoriesActions';

function Shop() {
    const dispatch = useDispatch();
    const [products, setProducts] = useState([]);
    const [hasNext, setHasNext] = useState(true);
    const categories = useSelector(state => state.categories);
    const filterParams = useSelector(state => state.filterParams);

    const errorHandler = () => {
        updateMessage("Try reloading the page.", "error");
    }

    const onUnmount = useCallback(() => {
        dispatch(resetCurrentCategory(false));
        dispatch(resetFilterParams());
    }, [dispatch]);

    const setCategoriesCallback = useCallback((data) => {
        dispatch(setCategories(data))
    }, [dispatch]);

    useEffect(() => {
        async function fetchCategories() {
            await getRequest(CATEGORIES_ENDPOINT, {}, (response) => setCategoriesCallback(response.data), errorHandler);
        }

        if (!categories.length) {
            fetchCategories();
        }

        return () => {
            onUnmount();
        }
    }, [categories.length, setCategoriesCallback, onUnmount]);

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
        dispatch(setPage(filterParams.page + 1));
    };

    const setCategoryFilter = (categoryId) => {
        dispatch(setCategoryId(categoryId));
    }

    const setSortingCriteria = (criteria) => {
        const sort = criteria !== "DEFAULT" ? criteria : null;
        const sortOrder = criteria !== "DEFAULT" ? filterParams.sortOrder : "ASC";
        dispatch(setSort(sort));
        dispatch(setSortOrder(sortOrder));
        dispatch(setPage(1));
    }

    const setSortingOrder = (order) => {
        dispatch(setPage(1));
        dispatch(setSortOrder(order));
    }

    return (
        <div className="shop-page">
            <div className="side-bar">
                <Categories expandable items={categories} border onFilter={setCategoryFilter} />
            </div>
            <div className="content">
                <Select
                    value={filterParams.sort || "DEFAULT"}
                    onChange={(e) => setSortingCriteria(e.target.value)}
                    className="sort-select"
                >
                    <MenuItem value="DEFAULT">Default Sorting</MenuItem>
                    <MenuItem value="PRICE">Sort by price</MenuItem>
                    <MenuItem value="DATE">Sort by newness</MenuItem>
                </Select>
                <button
                    className={`sort-order-button ${filterParams.sortOrder === "ASC" && "active"}`}
                    disabled={!filterParams.sort}
                    onClick={() => setSortingOrder("ASC")}
                >
                    <FontAwesomeIcon icon={faSortAmountUp} />
                </button>
                <button
                    className={`sort-order-button ${filterParams.sortOrder === "DESC" && "active"}`}
                    disabled={!filterParams.sort}
                    value="DESC"
                    onClick={() => setSortingOrder("DESC")}
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
