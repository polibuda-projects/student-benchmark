import ButtonMedium from '@components/Buttons/ButtonMedium';
import { Component } from 'react';
import { TestState } from './Test';
import style from './Test.module.css';


export interface TestEndProps {
  logoUrl?: string,
  result?: string,

  updateState: (state: TestState) => void,
}

export default class TestEnd extends Component<TestEndProps> {
  private static defaultProps: TestEndProps = {
    logoUrl: '',
    result: '[Test Summary]',

    updateState: () => {},
  };

  public render() {
    return (
      <div className={style.testContainer}>
        <img className={style.testLogo} src={this.props.logoUrl} alt='[Test Logo]' />
        <div className={style.testInfo}>
          <span className={style.testResult}>{this.props.result}</span>
          <span className={style.testShortDescription}>Test results have been saved!</span>
        </div>

        <ButtonMedium className={style.testButton} text='Try Again' onClick={this.buttonAction} />
      </div>
    );
  }

  private buttonAction =() => {
    this.props.updateState('start');
  };

  private joinClasses(...classes: string[]) {
    return classes.join(' ');
  }
}

