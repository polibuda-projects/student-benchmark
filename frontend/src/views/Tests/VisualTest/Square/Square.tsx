import { Component, useState } from 'react';

import './Square.css';
import { TestState } from '@components/Test/Test';


export interface SquareProps {
  winner?: boolean,
  index: number,
  getIndex: (index: number) => void,
}

export interface SquareStates {
  active: boolean,
  error: boolean,
}

export default class Square extends Component<SquareProps, SquareStates> {
  private static defaultProps: SquareProps = {
    index: 0,
    winner: false,
    getIndex: () => {},
  };

  constructor(props: SquareProps) {
    super(props);
    this.state = {
      active: false,
      error: false,
    };

    this.clickCheck = this.clickCheck.bind(this);
  }

  clickCheck() {
    if (!this.props.winner) {
      this.setState({ error: true });
    } else {
      this.setState({ active: true });
    }

    this.props.getIndex(this.props.index);
  }

  render() {
    return (
      <div
        className={this.joinClasses('square', this.state.active ? 'active' : '',
          this.state.error ? 'error' : '')}
        onClick={this.clickCheck}
      >
      </div>
    );
  }

  private joinClasses(...classes: string[]) {
    return classes.join(' ');
  }
}
