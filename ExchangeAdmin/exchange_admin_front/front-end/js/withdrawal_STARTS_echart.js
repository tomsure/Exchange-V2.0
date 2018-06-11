$(function () {
        var barChart = echarts.init(document.getElementById("echarts-bar-chart"));
        var baroption = {

            tooltip: {
                trigger: 'axis'
            },
            legend: {
                data: ['BTC', 'ETH','EOS','USDT']
            },
            grid: {
                x: 30,
                x2: 40,
                y2: 24
            },
            calculable: true,
            xAxis: [
                {
                    type: 'category',
                    data: ['2018-05-01', '2018-05-02', '2018-05-03', '2018-05-04', '2018-05-05', '2018-05-06', '2018-05-07',]
                }
            ],
            yAxis: [
                {
                    type: 'value'
                }
            ],
            series: [
                {
                    name: 'BTC',
                    type: 'bar',
                    data: [50, 60, 70, 80, 90, 100, 65,]

                },
                {
                    name: 'ETH',
                    type: 'bar',
                    data: [40, 50, 72, 60, 40, 60, 70,]

                },
                {
                    name: 'EOS',
                    type: 'bar',
                    data: [60, 65, 90, 95, 100, 10, 70,]

                },
                {
                    name: 'USDT',
                    type: 'bar',
                    data: [30, 30, 60, 80, 90, 80, 65,]

                },
            ]
        };
        barChart.setOption(baroption);

        window.onresize = barChart.resize;
    }
)