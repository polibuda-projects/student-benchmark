import { Component, HTMLAttributes } from 'react';

import style from './Textarea.module.css';


export interface TextareaProps {
  type?: string,
  name?: string,
  placeholder?: string,
  shadows?: boolean,
  className?: string
}

export default class Textarea extends Component<TextareaProps> {
  private static defaultProps: TextareaProps = {
    shadows: true,
  };

  render() {
    return (
      <textarea className={[style.textarea, this.props.className, this.props.shadows ? style.shadow : ''].join(' ')}
        name={this.props.name} placeholder={this.props.placeholder} />
    );
  }
}
