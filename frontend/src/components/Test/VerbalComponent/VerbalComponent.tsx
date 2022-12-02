import { Component } from 'react';

import style from './VerbalComponent.module.css';

export interface VerbalComponentProps {
  text: string;
  width?: string;
  className?: string;
}

export class VerbalComponent extends Component<VerbalComponentProps> {
  render() {
    return (
      <div className={[style.testWord, this.props.className].join(' ')} style={{ width: this.props.width }}>
        {this.props.text}
      </div>
    );
  }
  private buttonAction =() => {};
}

export class VerbalProperties extends Component<VerbalComponentProps> {
  render() {
    return (
      <div className={[style.testProperties, this.props.className].join(' ')} style={{ width: this.props.width }}>
        {this.props.text}
      </div>
    );
  }
}

