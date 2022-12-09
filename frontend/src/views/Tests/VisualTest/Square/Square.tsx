import { Component } from 'react';

import './Square.css';


export interface SquareProps {
  winner?: boolean,
  index: number,
  getIndex: (index: number) => void,
  testActiveState: boolean,
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
    testActiveState: true,
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
    if (!this.state.active && !this.state.error && this.props.testActiveState) {
      if (!this.props.winner) {
        this.setState({ error: true });
      } else {
        this.setState({ active: true });
      }
      this.props.getIndex(this.props.index);
    }
  }

  render() {
    return (
      <div id={this.props.index.toString()}
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
