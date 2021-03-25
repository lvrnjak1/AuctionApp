import React, { useEffect, useRef, useState } from 'react';
import Chart from "chart.js";
import "components/price_filter/chart/chart.scss";
import { scaleData, getChartConfig } from './chartUtils';


function PriceChart(props) {
    const chartContainer = useRef(null);
    const [chartInstance, setChartInstance] = useState(null);

    useEffect(() => {
        if (chartContainer && chartContainer.current) {
            const myData = scaleData({ data: props.data, labels: props.labels });
            const newChartInstance = new Chart(chartContainer.current, getChartConfig(myData));
            setChartInstance(newChartInstance);
        }
    }, [chartContainer]);

    const updateDataset = (datasetIndex, newData) => {
        if (chartInstance) {
            chartInstance.data.datasets[datasetIndex].data = newData;
            chartInstance.update();
        }
    };

    useEffect(() => {
        if (chartContainer && chartContainer.current) {
            const myData = scaleData({ data: props.data, labels: props.labels });
            updateDataset(0, myData.data);
        }
    }, [props.data]);

    return (
        <div className="chart-container">
            <canvas
                id="myChart"
                ref={chartContainer}
            />
        </div>
    )
}


export default PriceChart;
