import React, { useState, useEffect } from 'react';
import "components/price_filter/priceFilter.scss";
import { Slider } from '@material-ui/core';
import { useDispatch, useSelector } from 'react-redux';
import { setMaxPrice, setMinPrice } from 'state/actions/filterParamsActions';
import PriceChart from './chart/chart';
import { getRequest } from 'http/requests';
import { PRICE_CHART_ENDPOINT } from "http/endpoints";
import { getFormattedParams } from 'util/filterParams';

function PriceFilter() {
    const [min, setMin] = useState(0);
    const [max, setMax] = useState(0);
    const [step, setStep] = useState(20);
    const [sliderValue, setSliderValue] = useState([0, 0]);
    const [chart, setChart] = useState({ data: [], labels: [] });
    const dispatch = useDispatch();
    const filterParams = useSelector(state => state.filterParams)
    const filterChanged = useSelector(state => state.filterChanged)

    useEffect(() => {
        async function fetchChartData() {
            const params = getFormattedParams(filterParams);
            await getRequest(PRICE_CHART_ENDPOINT, params, (response) => {
                setChart(response.data);
                setMin(response.data.min);
                setMax(response.data.max);
                setStep(response.data.step);
                setSliderValue([response.data.min, response.data.max]);
                setMinPrice(response.data.min);
                setMaxPrice(response.data.max);
            })
        }
        if (filterChanged) {
            fetchChartData();
        }
    }, [filterParams, filterChanged, min, max]);

    const handleChange = (event, newValue) => {
        setSliderValue(newValue);
    };

    const handleChangeCommited = (event, commited) => {
        dispatch(setMinPrice(commited[0]));
        dispatch(setMaxPrice(commited[1]));
    }

    function valuetext(value) {
        return `$${value.toFixed(2)}`;
    }

    function average() {
        return (sliderValue[0] + sliderValue[1]) / 2;
    }

    return (
        chart !== {} &&
        <div className="price-filter">
            <p className="title">Filter by price</p>
            <PriceChart chart={chart} />
            <Slider
                className="slider"
                value={sliderValue}
                onChange={handleChange}
                onChangeCommitted={handleChangeCommited}
                valueLabelDisplay="auto"
                aria-labelledby="range-slider"
                getAriaValueText={valuetext}
                max={max}
                min={min}
                step={step}
                disabled={min === max}
            />
            <p className="filter-text">
                {`${valuetext(sliderValue[0])} - ${valuetext(sliderValue[1])}`}
            </p>
            <p className="filter-text">
                {`Average price ${valuetext(average())}`}
            </p>
        </div>
    );
}

export default PriceFilter;