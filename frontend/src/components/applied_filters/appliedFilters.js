import React, { useCallback, useEffect, useState } from 'react';
import { Chip } from '@material-ui/core';
import "components/applied_filters/appliedFilters.scss";
import { useDispatch, useSelector } from 'react-redux';
import { removeCategoryId, setName } from 'state/actions/filterParamsActions';
import { resetSearch } from 'state/actions/searchActions';


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
        dispatch(removeCategoryId(chip.key));
    }, [dispatch]);

    const handleRemoveSearch = useCallback(() => {
        dispatch(resetSearch(""));
        dispatch(setName(""));
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
                    key: cat,
                    filter: nameMapping.categoryId,
                    value: getCategoryName(cat),
                    removeHandler: handleRemoveCategory
                });
            })
        }

        if (filterParams.name) {
            newData.push({ filter: nameMapping.name, value: filterParams.name, removeHandler: handleRemoveSearch });
        }

        setChipData(newData);
    }, [filterParams, nameMapping.categoryId, nameMapping.name, handleRemoveCategory, handleRemoveSearch, getCategoryName]);

    return (
        <ul className="filter-list">
            {chipData.map(data => {
                return (
                    <li key={data.key || data.value}>
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
