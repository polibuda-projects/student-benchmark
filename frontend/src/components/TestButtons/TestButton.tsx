import { Component } from 'react';

import sequence from '@components/TestButtons/sequenceTest.svg';
import visual from '@components/TestButtons/visualTest.svg';
import verbal from '@components/TestButtons/verbalTest.svg';
import number from '@components/TestButtons/numberTest.svg';
import style from './TestButton.module.css';

export interface TestButtonProps {
  text: string;
  width?: string;
  onClick?: () => void;
  className?: string;
}

export class SequenceButton extends Component<TestButtonProps> {
  render() {
    return (
      <div className={[style.testButton, this.props.className].join(' ')} style={{ width: this.props.width }} onClick={this.props.onClick}>
        <img src={sequence} className={style.imageButton} alt='sequence test'/>
        {this.props.text}
      </div>
    );
  }
}

export class VisualButton extends Component<TestButtonProps> {
  render() {
    return (
      <div className={[style.testButton, this.props.className].join(' ')} style={{ width: this.props.width }} onClick={this.props.onClick}>
        <img src={visual} className={style.imageButton} alt='visual test'/>
        {this.props.text}
      </div>
    );
  }
}

export class VerbalButton extends Component<TestButtonProps> {
  render() {
    return (
      <div className={[style.testButton, this.props.className].join(' ')} style={{ width: this.props.width }} onClick={this.props.onClick}>
        <img src={verbal} className={style.imageButton} alt='verbal test'/>
        {this.props.text}
      </div>
    );
  }
}

export class NumberButton extends Component<TestButtonProps> {
  render() {
    return (
      <div className={[style.testButton, this.props.className].join(' ')} style={{ width: this.props.width }} onClick={this.props.onClick}>
        <img src={number} className={style.imageButton} alt='number test'/>
        {this.props.text}
      </div>
    );
  }
}


