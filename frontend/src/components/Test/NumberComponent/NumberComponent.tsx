import { Component } from 'react';

import style from './NumberComponent.module.css';

export interface NumberComponentProps {
    text: string;
    width?: string;
    className?: string;
    fontSize: string;
}

export class NumberProperties extends Component<NumberComponentProps> {
  render() {
    return (
      <div className={[style.testProperties, this.props.className].join(' ')} style={{ width: this.props.width, fontSize: this.props.fontSize }}>
        {this.props.text}
      </div>
    );
  }
}
