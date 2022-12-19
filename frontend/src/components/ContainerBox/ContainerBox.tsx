import { Component, HTMLAttributes } from 'react';

import style from './ContainerBox.module.css';


export interface ContainerBoxProps extends HTMLAttributes<HTMLDivElement> {
  background?: boolean,
  shadows?: boolean,
  width?: string,
  className?: string;
}

export default class ContainerBox extends Component<ContainerBoxProps> {
  private static defaultProps: ContainerBoxProps = {
    background: true,
    shadows: true,
  };

  render() {
    return (
      <section
        className={[style.content, this.props.background ? style.contentBackground : '', this.props.className].join(' ')}
        style={{ width: this.props.width }}>
        {this.props.children}
      </section>
    );
  }
}

