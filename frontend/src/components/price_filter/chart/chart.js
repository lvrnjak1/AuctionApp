import React, { useEffect, useRef, useState, useCallback } from 'react';
import Chart from "chart.js";
import "components/price_filter/chart/chart.scss";
import { scaleData, getChartConfig } from './chartUtils';


function PriceChart(props) {
    const chartContainer = useRef(null);
    const [chartInstance, setChartInstance] = useState(null);

    const updateDataset = useCallback((newChart) => {
        if (chartInstance) {
            chartInstance.data.datasets[0].data = newChart.data;
            chartInstance.data.labels = newChart.labels;
            chartInstance.update();
        }
    }, [chartInstance]);

    useEffect(() => {
        if (chartContainer && chartContainer.current) {
            if (chartInstance !== null) {
                updateDataset(props.chart)
            } else {
                const myData = scaleData({ data: props.chart.data, labels: props.chart.labels });
                const newChartInstance = new Chart(chartContainer.current, getChartConfig(myData));
                setChartInstance(newChartInstance);
            }
        }
    }, [props, chartInstance, updateDataset]);

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
