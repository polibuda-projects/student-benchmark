import { Component, HTMLAttributes } from 'react';

import style from './Input.module.css';


export interface InputProps {
  type?: string,
  name?: string,
  placeholder?: string,
  shadows?: boolean,
  className?: string,
}

export default class Inputs extends Component<InputProps> {
  private static defaultProps: InputProps = {
    shadows: true,
  };

  render() {
    return (
      <input className={[style.input, this.props.className, this.props.shadows ? style.shadow : ''].join(' ')}
        type={this.props.type} name={this.props.name} placeholder={this.props.placeholder} />
    );
  }
}
