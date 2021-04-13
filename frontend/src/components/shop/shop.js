import React, { useState, useEffect, useCallback } from 'react';
import Categories from 'components/categories/categories';
import ProductGrid from 'components/product_grid/productGrid';
import { AUCTIONS_ENDPOINT, CATEGORIES_ENDPOINT } from 'http/endpoints';
import { getRequest } from 'http/requests';
import { MenuItem, Select } from '@material-ui/core';
import { faSortAmountUp, faSortAmountDown, faThList, faTh } from "@fortawesome/free-solid-svg-icons"
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { useDispatch, useSelector } from 'react-redux';
import { resetCurrentCategory } from 'state/actions/currentCategoryActions';
import { updateMessage } from 'util/info_div_util';
import { addCategoryId, resetFilterParams, setPage, setSort, setSortOrder } from 'state/actions/filterParamsActions';
import { setCategories } from 'state/actions/categoriesActions';
import AppliedFilters from 'components/applied_filters/appliedFilters';
import PriceFilter from 'components/price_filter/priceFilter';
import "components/shop/shop.scss";
import { getFormattedParams } from 'util/filterParams';
import { setGrid, setList } from 'state/actions/displayPreferenceActions';
import { setSuggestion } from 'state/actions/suggestionActions';

function Shop() {
    const dispatch = useDispatch();
    const [products, setProducts] = useState([]);
    const [hasNext, setHasNext] = useState(true);
    const categories = useSelector(state => state.categories);
    const filterParams = useSelector(state => state.filterParams);
    const grid = useSelector(state => state.grid)

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
            const params = getFormattedParams(filterParams);
            await getRequest(AUCTIONS_ENDPOINT, params, (response) => {
                handleNewProducts(response.data.auctions);
                dispatch(setSuggestion(response.data.suggestion));
            }, errorHandler);
        }
        fetchProducts();
    }, [filterParams, dispatch]);

    const loadMore = () => {
        dispatch(setPage(filterParams.page + 1));
    };

    const setCategoryFilter = (categoryId) => {
        dispatch(addCategoryId(categoryId));
        dispatch(setPage(1));
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

    const handleViewChange = (view) => {
        view === "grid" ? dispatch(setGrid()) : dispatch(setList());
    }

    return (
        <div className="shop-page">
            <div className="side-bar">
                <Categories expandable items={categories} border onFilter={setCategoryFilter} />
                <PriceFilter />
            </div>
            <div className="content">
                <div className="top">
                    <div>
                        <Select
                            value={filterParams.sort || "DEFAULT"}
                            onChange={(e) => setSortingCriteria(e.target.value)}
                            className="sort-select"
                        >
                            <MenuItem value="DEFAULT">Sort alphabetically</MenuItem>
                            <MenuItem value="PRICE">Sort by price</MenuItem>
                            <MenuItem value="DATE">Sort by date added</MenuItem>
                            <MenuItem value="TIME_LEFT">Sort by time left</MenuItem>
                        </Select>
                        <button
                            className={`sort-order-button ${filterParams.sortOrder === "ASC" && "active"} shop-page-button`}
                            onClick={() => setSortingOrder("ASC")}
                        >
                            <FontAwesomeIcon icon={faSortAmountUp} />
                        </button>
                        <button
                            className={`sort-order-button ${filterParams.sortOrder === "DESC" && "active"} shop-page-button`}
                            value="DESC"
                            onClick={() => setSortingOrder("DESC")}
                        >
                            <FontAwesomeIcon icon={faSortAmountDown} />
                        </button>
                    </div>
                    <div className="list-grid">
                        <button
                            className={`list-grid-button ${grid && "active"} shop-page-button`}
                            autoFocus
                            onClick={() => handleViewChange("grid")}
                        >
                            <FontAwesomeIcon icon={faTh} />
                            <span>Grid</span>
                        </button>
                        <button
                            className={`list-grid-button ${!grid && "active"} shop-page-button`}
                            onClick={() => handleViewChange("list")}
                        >
                            <FontAwesomeIcon icon={faThList} />
                            <span>List</span>
                        </button>
                    </div>
                </div>
                <div className="filters">
                    <AppliedFilters />
                </div>
                <div className="center-content">
                    <ProductGrid nrows={Math.ceil(products.length / 3)} items={products} col3 grid={grid} />
                    {hasNext && <button className="shop-page-button" onClick={loadMore}>Explore more</button>}
                </div>
            </div>
        </div>
    );
}

export default Shop;
