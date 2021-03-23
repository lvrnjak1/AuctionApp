import React, { useEffect } from 'react';
import Chart from "chart.js";
import "components/price_filter/chart/chart.scss";

const generateData = (start, end, step) => {
    const labels = [];
    const data = [];
    for (let i = start; i < end; i += step) {
        labels.push(i);
        data.push(Math.random() * 100);
    }
    return { labels, data };
};

const scaleData = (DS) => {
    const min = Math.min(DS.data);
    const max = Math.max(DS.data);
    const targetMin = 0;
    const targetMax = 100;
    DS.data.map(el => {
        return ((el - min) / (max - min)) * (targetMax - targetMin) + targetMin;
    });

    return DS;
}

export default class PriceChart extends React.Component {
    chartRef = React.createRef();

    componentDidMount() {
        const myChartRef = this.chartRef.current.getContext("2d");
        const myData = scaleData(generateData(0, 500, 30));
        new Chart(myChartRef, {
            type: 'bar',
            data: {
                labels: myData.labels,
                datasets: [
                    {
                        barPercentage: 1,
                        categoryPercentage: 1,
                        backgroundColor: "#E4E5EC",
                        minBarLength: 2,
                        hoverBorderColor: "#E4E5EC",
                        hoverBackgroundColor: "#E4E5EC",
                        data: myData.data
                    }
                ]
            },
            options: {
                legend: { display: false },
                responsive: true,
                maintainAspectRatio: false,
                tooltips: {
                    enabled: false
                },
                scales: {
                    xAxes: [{
                        ticks: {
                            display: false
                        },
                        gridLines: {
                            display: false,
                            drawBorder: false
                        }
                    }],
                    yAxes: [{
                        ticks: { display: false },
                        gridLines: {
                            display: false,
                            drawBorder: false
                        }
                    }]
                }
            }
        });
    }

    render() {
        return (
            <div className="chart-container">
                <canvas
                    id="myChart"
                    ref={this.chartRef}
                />
            </div>
        )
    }
}
