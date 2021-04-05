import React, { useState, useEffect } from 'react';
import "components/price_filter/priceFilter.scss";
import { Slider } from '@material-ui/core';
import { useDispatch, useSelector } from 'react-redux';
import { setMaxPrice, setMinPrice } from 'state/actions/filterParamsActions';
import PriceChart from './chart/chart';
import { getRequest } from 'http/requests';
import { PRICE_CHART_ENDPOINT } from "http/endpoints";
import { getFormattedParams } from 'util/filterParams';
import { setSliderValue, setSlider } from 'state/actions/sliderActions';

function PriceFilter() {
    const [min, setMin] = useState(0);
    const [max, setMax] = useState(0);
    const [step, setStep] = useState(20);
    const [chart, setChart] = useState({ data: [], labels: [] });
    const dispatch = useDispatch();
    const filterParams = useSelector(state => state.filterParams);
    const filterChanged = useSelector(state => state.filterChanged);
    const slider = useSelector(state => state.slider)

    useEffect(() => {
        async function fetchChartData() {
            const params = getFormattedParams(filterParams);
            await getRequest(PRICE_CHART_ENDPOINT, params, (response) => {
                setChart(response.data);
                setMin(response.data.min);
                setMax(response.data.max);
                setStep(response.data.step);

                if (filterParams.priceMax !== null &&
                    (filterParams.priceMax > response.data.max ||
                        filterParams.priceMax < response.data.min)) {
                    dispatch(setMaxPrice(response.data.max));
                }
                if (filterParams.priceMin !== null &&
                    (filterParams.priceMin < response.data.min ||
                        filterParams.priceMin > response.data.max)) {
                    dispatch(setMinPrice(response.data.min));
                }

                let newMin = response.data.min;
                let newMax = response.data.max;
                if (filterParams.priceMin !== null) {
                    newMin = filterParams.priceMin;
                }
                if (filterParams.priceMax !== null) {
                    newMax = filterParams.priceMax;
                }

                dispatch(setSlider([newMin, newMax], response.data.min, response.data.max));
            }, () => { })
        }
        if (filterChanged) {
            fetchChartData();
        }
    }, [filterParams, filterChanged, min, max, dispatch]);

    const handleChange = (event, newValue) => {
        dispatch(setSliderValue(newValue));
    };

    const handleChangeCommited = (event, commited) => {
        dispatch(setMinPrice(commited[0]));
        dispatch(setMaxPrice(commited[1]));
    }

    function valuetext(value) {
        return `$${value.toFixed(2)}`;
    }

    function average() {
        return (slider.value[0] + slider.value[1]) / 2;
    }

    return (
        chart !== {} &&
        <div className="price-filter">
            <p className="title">Filter by price</p>
            <div className="main">
                <PriceChart chart={chart} />
                <Slider
                    className="slider"
                    value={slider.value}
                    onChange={handleChange}
                    onChangeCommitted={handleChangeCommited}
                    valueLabelDisplay="auto"
                    aria-labelledby="range-slider"
                    getAriaValueText={valuetext}
                    max={slider.max}
                    min={slider.min}
                    step={step}
                    disabled={min === max}
                />
            </div>
            <div className="filter-text">
                <p>
                    {`${valuetext(slider.value[0])} - ${valuetext(slider.value[1])}`}
                </p>
                <p>
                    {`Average price ${valuetext(average())}`}
                </p>
            </div>
        </div>
    );
}

export default PriceFilter;