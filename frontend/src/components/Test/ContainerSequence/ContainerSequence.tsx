import { Component, HTMLAttributes } from 'react';
import { useState } from 'react';
import style from './ContainerSequence.module.css';
import SequenceBox from '@components/Test/SequenceBox/SequenceBox';

export interface ContainerSequenceProps {
    className?: string,
    children?: HTMLAttributes<HTMLDivElement>['children'];
}

export default class ContainerSequence extends Component<ContainerSequenceProps> {
  render() {
    return (
      <div className={[style.ContainerSequence, this.props.className].join(' ')}>
        {this.props.children}
      </div>
    );
  }
}
