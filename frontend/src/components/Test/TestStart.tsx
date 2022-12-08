import ButtonMedium from '@components/Buttons/ButtonMedium';
import { Component } from 'react';
import { TestState } from './Test';
import style from './Test.module.css';


export interface TestStartProps {
  logoUrl?: string,
  shortDescription?: string,

  updateState: (state: TestState) => void,
}

export default class TestStart extends Component<TestStartProps> {
  private static defaultProps: TestStartProps = {
    logoUrl: '',
    shortDescription: '[Short Test Description]',
    updateState: () => {},
  };

  public render() {
    return (
      <div className={style.testContainer}>
        <img className={style.testLogo} src={this.props.logoUrl} alt='[Test Logo]' />
        <span className={style.testShortDescription}>{this.props.shortDescription}</span>

        <ButtonMedium className={style.testButton} text='Start' onClick={() => this.props.updateState('playing')} />
      </div>
    );
  }


  private joinClasses(...classes: string[]) {
    return classes.join(' ');
  }
}
