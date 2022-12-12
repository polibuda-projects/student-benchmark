import { Component } from 'react';

import style from './ButtonGrey.module.css';


export interface ButtonGreyProps {
    text: string;
    width?: string;
    onClick?: () => void;
    className?: string;
}

export default class ButtonMedium extends Component<ButtonGreyProps> {
  render() {
    return (
      <button className={[style.button, this.props.className].join(' ')} style={{ width: this.props.width }} onClick={this.props.onClick} type='button'>
        {this.props.text}
      </button>
    );
  }
}

