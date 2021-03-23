import React, { useState } from 'react';
import "components/price_filter/priceFilter.scss";
import { Slider } from '@material-ui/core';
import { useDispatch } from 'react-redux';
import { setMaxPrice, setMinPrice } from 'state/actions/filterParamsActions';
import PriceChart from './chart/chart';

function PriceFilter() {
    const min = 0;
    const max = 500;
    const step = 30;
    const [value, setValue] = useState([min, max]);
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
            <PriceChart min={min} max={max} step={step} />
            <Slider
                className="slider"
                value={value}
                onChange={handleChange}
                onChangeCommitted={handleChangeCommited}
                valueLabelDisplay="auto"
                aria-labelledby="range-slider"
                getAriaValueText={valuetext}
                max={max}
                min={min}
                step={step}
                marks
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