import React, { useCallback, useEffect, useState } from 'react';
import { Chip } from '@material-ui/core';
import "components/applied_filters/appliedFilters.scss";
import { useDispatch, useSelector } from 'react-redux';
import { removeCategoryId, setMaxPrice, setMinPrice, setName } from 'state/actions/filterParamsActions';
import { resetSearch } from 'state/actions/searchActions';
import { resetMin, resetMax } from 'state/actions/sliderActions';

function getRandomKey() {
    return Math.random() * 1000;
}

function AppliedFilters() {
    const filterParams = useSelector(state => state.filterParams);
    const categories = useSelector(state => state.categories);
    const nameMapping = { categoryId: "Category", name: "Search", priceMin: "Min price", priceMax: "Max price" };
    const [chipData, setChipData] = useState([]);
    const dispatch = useDispatch();

    const handleDelete = (chipToDelete) => {
        chipToDelete.removeHandler(chipToDelete);
    };

    const handleRemoveCategory = useCallback((chip) => {
        dispatch(removeCategoryId(chip.id));
    }, [dispatch]);

    const handleRemoveSearch = useCallback(() => {
        dispatch(resetSearch(""));
        dispatch(setName(""));
    }, [dispatch]);

    const handleRemovePrice = useCallback((min) => {
        if (min) {
            dispatch(setMinPrice(null));
            dispatch(resetMin());
        } else {
            dispatch(setMaxPrice(null));
            dispatch(resetMax());
        }

    }, [dispatch]);

    const getCategoryName = useCallback((id) => {
        let category = categories.find(c => {
            if (c.id === id) return true;
            return c.subcategories.find(sc => sc.id === id);
        });
        let name = category.name;
        if (category.id !== id) {
            category = category.subcategories.find(sc => sc.id === id);
            name += " " + category.name;
        }

        return name;
    }, [categories]);

    useEffect(() => {
        let newData = [];
        if (filterParams.categoryId) {
            filterParams.categoryId.forEach(cat => {
                newData.push({
                    key: getRandomKey(),
                    id: cat,
                    filter: nameMapping.categoryId,
                    value: getCategoryName(cat),
                    removeHandler: handleRemoveCategory
                });
            })
        }

        if (filterParams.name) {
            newData.push({ key: getRandomKey(), filter: nameMapping.name, value: filterParams.name, removeHandler: handleRemoveSearch });
        }

        if (filterParams.priceMax) {
            newData.push({ key: getRandomKey(), filter: nameMapping.priceMax, value: `$${filterParams.priceMax}`, removeHandler: () => handleRemovePrice(false) });
        }

        if (filterParams.priceMin) {
            newData.push({ key: getRandomKey(), filter: nameMapping.priceMin, value: `$${filterParams.priceMin}`, removeHandler: () => handleRemovePrice(true) });
        }

        setChipData(newData);
    }, [filterParams,
        nameMapping.categoryId,
        nameMapping.name,
        nameMapping.priceMax,
        nameMapping.priceMin,
        dispatch,
        handleRemoveCategory,
        handleRemoveSearch,
        handleRemovePrice,
        getCategoryName]);

    return (
        <ul className="filter-list">
            {chipData.map(data => {
                return (
                    <li key={data.key}>
                        <Chip
                            label={`${data.filter}: ${data.value}`}
                            onDelete={() => handleDelete(data)}
                            className="chip"
                        />
                    </li>
                )
            })}
            {(!filterParams.categoryId || filterParams.categoryId.length === 0) ?
                <li key={0}>
                    <Chip
                        label={`${nameMapping.categoryId}: ${"All"}`}
                        className="chip"
                    />
                </li> : ""}
        </ul>
    );
}

export default AppliedFilters;
