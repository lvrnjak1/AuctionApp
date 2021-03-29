import React, { useState, useEffect } from 'react';
import "components/price_filter/priceFilter.scss";
import { Slider } from '@material-ui/core';
import { useDispatch, useSelector } from 'react-redux';
import { setMaxPrice, setMinPrice } from 'state/actions/filterParamsActions';
import PriceChart from './chart/chart';
import { getRequest } from 'http/requests';
import { PRICE_CHART_ENDPOINT } from "http/endpoints";

function PriceFilter() {
    const [min, setMin] = useState(0);
    const [max, setMax] = useState(500);
    const [step, setStep] = useState(20);
    const [value, setValue] = useState([]);
    const [labels, setLabels] = useState([]);
    const [data, setData] = useState([]);
    const dispatch = useDispatch();
    const filterParams = useSelector(state => state.filterParams)

    useEffect(() => {
        async function fetchChartData() {
            const ids = (filterParams.categoryId && filterParams.categoryId.length > 0) ?
                filterParams.categoryId.map(id => `${id}`).join(',') :
                null;
            const params = { ...filterParams, categoryId: ids }
            await getRequest(PRICE_CHART_ENDPOINT,
                params,
                (response) => {
                    setMin(response.data.min);
                    setMax(response.data.max);
                    setStep(response.data.step);
                    if (value.length === 0) setValue([response.data.min, response.data.max]);
                    setLabels(response.data.labels);
                    setData(response.data.data);
                },
                (error) => console.log(error.response)
            );
        }
        fetchChartData();
    }, [filterParams, value.length])

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
        data.length > 0 &&
        <div className="price-filter">
            <p className="title">Filter by price</p>
            <PriceChart min={min} max={max} step={step} labels={labels} data={data} />
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
                step={5}
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