import style from './Test.module.css';

import { Component, HTMLAttributes } from 'react';
import Page from '@components/Page/Page';
import TestChart, { TestChartProps } from '@components/TestChart/TestChart';


export type TestState = 'start' | 'playing' | 'end' | 'numberInput' | 'numberCorrect' | 'numberIncorrect';

export type TestProps = Exclude<TestChartProps, 'userScore'>; // Something something compatibility

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
            <TestChart {...this.props.chartData} userScore={this.props.userScore} />
          </div>
        </div>
      </Page>
    );
  }

  private joinClasses(...classes: string[]) {
    return classes.join(' ');
  }
}
