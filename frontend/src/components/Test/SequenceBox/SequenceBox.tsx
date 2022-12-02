import { Component, HTMLAttributes } from 'react';

import style from './SequenceBox.module.css';

export interface SequenceBoxProps {
    name?: string,
    id?: number,
    fill?: string,
    className?: string,
    onClick?: () => number;
}

export default class SequenceBox extends Component<SequenceBoxProps> {
  private static defaultProps: SequenceBoxProps = {
    fill: 'none',
  };

  render() {
    return (
      <div style={{ background: this.props.fill }} className={[style.sequenceBox, this.props.className].join(' ')} />
    );
  }
}
