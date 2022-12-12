import { Component } from 'react';

import style from './TestButton.module.css';

export interface TestButtonProps {
  text: string;
  width?: string;
  onClick?: () => void;
  className?: string;
  alt?: string;
  src?: string;
}

export class TestButton extends Component<TestButtonProps> {
  render() {
    return (
      <div className={[style.testButton, this.props.className].join(' ')} style={{ width: this.props.width }} onClick={this.props.onClick}>
        <img src={this.props.src} className={style.imageButton} alt={this.props.alt}/>
        {this.props.text}
      </div>
    );
  }
}



