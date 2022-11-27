import style from './Test.module.css';

import 'chart.js/auto'; // Fixes "Canvas already in use" error
import { Component, HTMLAttributes } from 'react';
import { Chart } from 'react-chartjs-2';
import { ChartData as ChartDataJS } from 'chart.js/auto';
import Page from '@components/Page/Page';


export type TestState = 'start' | 'playing' | 'end';

export interface TestProps {
  data: number[];
  range: [number, number];
  label?: string;
};

export interface PageProps {
  testName?: string,
  testDescription?: string,
  chartData: TestProps,
  userScore: number | null,
  children?: HTMLAttributes<HTMLDivElement>['children'];
}

export default class Test extends Component<PageProps> {
  private static defaultProps: PageProps = {
    testName: '[Test Name]',
    testDescription: '[Test Description]',
    chartData: {
      data: [],
      range: [0, 0],
    },
    userScore: null,
  };

  render() {
    return (
      <Page content={false} titlebar={false}>
        <div className={style.content}>
          <div className={this.joinClasses(style.testBackground, style.test)}>
            {this.props.children}
          </div>

          <div className={this.joinClasses(style.testBackground, style.description)}>
            <span className={style.testTitle}>
              {this.props.testName}
            </span>

            <p className={style.testDescription}>
              {this.props.testDescription}
            </p>

            <span className={style.statisticsText}>Statistics</span>
            <Chart type='bar'
              className={style.chart}
              data={this.convertChartData(this.props.chartData)}

              options={{
                backgroundColor: 'transparent',

                aspectRatio: 3 / 2,
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
          </div>
        </div>
      </Page>
    );
  }

  private convertChartData(chartData: TestProps): ChartDataJS {
    const maxx = this.props.userScore === null ? chartData.range[1]: Math.max(chartData.range[1], this.props.userScore);
    const minn = this.props.userScore === null ? chartData.range[0]: Math.min(chartData.range[0], this.props.userScore);

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
