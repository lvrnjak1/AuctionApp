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

const getChartConfig = (myData) => {
    return {
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
                    left: -10,
                    bottom: -10
                }
            },
        }
    }
}


export {
    scaleData,
    getChartConfig
}