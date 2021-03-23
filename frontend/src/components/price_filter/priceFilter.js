import React, { useEffect, useState } from 'react';
import "components/price_filter/priceFilter.scss";
import { Slider } from '@material-ui/core';
import { useDispatch, useSelector } from 'react-redux';
import { setMaxPrice, setMinPrice } from 'state/actions/filterParamsActions';

function PriceFilter() {
    const filterParams = useSelector(state => state.filterParams)
    const [value, setValue] = useState([20, 100]);
    const dispatch = useDispatch();

    const handleChange = (event, newValue) => {
        setValue(newValue);
    };

    function valuetext(value) {
        return `$${value.toFixed(2)}`;
    }

    function average() {
        return (value[0] + value[1]) / 2;
    }

    const handleApply = (e) => {
        dispatch(setMinPrice(value[0]));
        dispatch(setMaxPrice(value[1]));
    }

    return (
        <div className="price-filter">
            <p className="title">Filter by price</p>
            <Slider
                className="slider"
                value={value}
                onChange={handleChange}
                valueLabelDisplay="auto"
                aria-labelledby="range-slider"
                getAriaValueText={valuetext}
                max={500}
            />
            <p className="filter-text">
                {`${valuetext(value[0])} - ${valuetext(value[1])}`}
            </p>
            <p className="filter-text">
                {`Average price ${valuetext(average())}`}
            </p>
            <button className="apply-button" onClick={(e) => handleApply(e)}>Apply</button>
        </div>
    );
}

export default PriceFilter;