import style from './TestChart.module.css';

import 'chart.js/auto'; // Fixes "Canvas already in use" error
import { Component } from 'react';
import { Chart } from 'react-chartjs-2';
import { ChartData as ChartDataJS } from 'chart.js/auto';


export interface TestChartProps {
  data: number[];
  range: [number, number];
  label?: string;
  userScore?: number | null,
  aspectRatio?: number,
};

export default class TestChart extends Component<TestChartProps> {
  private static defaultProps: TestChartProps = {
    data: [],
    range: [0, 0],
    userScore: null,
    aspectRatio: 3 / 2,
  };

  render() {
    console.log(this.props.aspectRatio);

    return (
      <Chart type='bar'
        className={style.chart}
        data={this.convertChartData(this.props)}

        options={{
          backgroundColor: 'transparent',

          aspectRatio: this.props.aspectRatio,
          events: [],

          elements: {
            bar: {
              borderWidth: 0,
              backgroundColor: '#FAF7FF',
              borderRadius: 500,
            },

            line: {
              borderColor: '#FAF7FF',
              borderWidth: 4,
              borderJoinStyle: 'round',
              borderCapStyle: 'round',
            },
          },

          plugins: {
            legend: {
              display: false,
            },
          },

          scales: {
            x: {
              title: {
                color: '#FAF7FF',
                font: {
                  family: 'Quicksand',
                },
              },
              grid: {
                display: false,
              },
            },
            y: {
              display: false,
            },
          },
        }}
      />
    );
  }

  private convertChartData(chartData: TestChartProps): ChartDataJS {
    const maxx = typeof this.props.userScore !== 'number' ? chartData.range[1]: Math.max(chartData.range[1], this.props.userScore);
    const minn = typeof this.props.userScore !== 'number' ? chartData.range[0]: Math.min(chartData.range[0], this.props.userScore);

    const lineData = [
      ...Array.from({ length: chartData.range[0] - minn }, () => 0),
      ...chartData.data,
      ...Array.from({ length: maxx - chartData.range[1] }, () => 0),
    ];

    const labels = Array.from({ length: maxx - minn + 1 }, (_, i) => minn + i);
    const datasets: ChartDataJS['datasets'] = [];

    datasets.push({
      type: 'line',
      data: lineData,
      cubicInterpolationMode: 'monotone',
      tension: 0.5,
      fill: true,
      backgroundColor: '#FAF7FF40',
      pointStyle: 'circle',
      pointRadius: 4,
      pointBorderColor: '#FFFFFF',
    });

    if (this.props.userScore !== null) {
      const barData = Array.from({ length: maxx - minn }, () => 0);
      barData[(this.props.userScore ?? 0) - minn] = Math.max(...chartData.data);

      datasets.push({
        type: 'bar',
        data: barData,
      });
    }

    return {
      datasets,
      labels: labels,
    };
  }

  private joinClasses(...classes: string[]) {
    return classes.join(' ');
  }
}
