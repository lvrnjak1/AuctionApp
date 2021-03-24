import React from 'react';
import Chart from "chart.js";
import "components/price_filter/chart/chart.scss";


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

    componentDidUpdate() {
        const myChartRef = this.chartRef.current.getContext("2d");
        const myData = scaleData({ data: this.props.data, labels: this.props.labels });
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
                animation: { duration: 0 },
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
                },
                layout: {
                    padding: {
                        left: -10
                    }
                },
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
