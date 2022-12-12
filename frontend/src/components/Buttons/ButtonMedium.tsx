import { Component } from 'react';

import style from './ButtonMedium.module.css';


export interface ButtonMediumProps {
  text: string;
  width?: string;
  onClick?: () => void;
  className?: string;
  id?: string;
}

export default class ButtonMedium extends Component<ButtonMediumProps> {
  render() {
    return (
      <button className={[style.button, this.props.className].join(' ')} style={{ width: this.props.width }} onClick={this.props.onClick} type='button' id={this.props.id}>
        {this.props.text}
      </button>
    );
  }
}

