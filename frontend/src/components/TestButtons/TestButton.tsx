import { Component } from 'react';

import style from './TestButton.module.css';


export interface TestButtonProps {
  text: string;
  width?: string;
  onClick?: () => void;
  className?: string;
}

export default class TestButton extends Component<TestButtonProps> {
  render() {
    return (
      <button className={[style.button, this.props.className].join(' ')} style={{ width: this.props.width }} onClick={this.props.onClick} type='button'>
        {this.props.text}
      </button>
    );
  }
}
