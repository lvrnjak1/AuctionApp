import React, { useEffect, useState } from 'react';
import "components/price_filter/priceFilter.scss";
import { debounce, Slider } from '@material-ui/core';
import { useDispatch, useSelector } from 'react-redux';
import { setMaxPrice, setMinPrice } from 'state/actions/filterParamsActions';

function PriceFilter() {
    const [value, setValue] = useState([20, 100]);
    const dispatch = useDispatch();

    const handleChange = (event, newValue) => {
        setValue(newValue);
    };

    const handleChangeCommited = (event, commited) => {
        dispatch(setMinPrice(commited[0]));
        dispatch(setMaxPrice(commited[1]));
    }

    function valuetext(value) {
        return `$${value.toFixed(2)}`;
    }

    function average() {
        return (value[0] + value[1]) / 2;
    }

    return (
        <div className="price-filter">
            <p className="title">Filter by price</p>
            <Slider
                className="slider"
                value={value}
                onChange={handleChange}
                onChangeCommitted={handleChangeCommited}
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
        </div>
    );
}

export default PriceFilter;