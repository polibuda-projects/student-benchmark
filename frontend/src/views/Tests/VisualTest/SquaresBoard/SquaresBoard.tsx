import { Component } from 'react';

import style from './SquaresBoard.module.css';
import Square from '@views/Tests/VisualTest/Square/Square';
import { TestActiveState } from '@views/Tests/VisualTest/VisualTest';


export interface SquaresBoardProps {
  size: number,
  randomWinnersIdx: Set<number>,
  correctTileRespond: (isCorrect: boolean) => void,
  testActiveState: boolean,
}

export default class SquaresBoard extends Component<SquaresBoardProps> {
  private static defaultProps: SquaresBoardProps = {
    size: 3,
    randomWinnersIdx: new Set(),
    correctTileRespond: () => {},
    testActiveState: true,
  };

  state = {
    activeIndex: null,
    reset: false,
  };

  handleClick = (index: number) => {
    this.setState({ activeIndex: index });
    if (this.props.randomWinnersIdx.has(index)) {
      this.props.correctTileRespond(true);
    } else {
      this.props.correctTileRespond(false);
    }
  };

  render() {
    const squares = [];

    let idx = 1;

    for (let i = 0; i < this.props.size; i++) {
      for (let j = 0; j < this.props.size; j++) {
        squares.push(<Square key={idx} winner={this.props.randomWinnersIdx.has(idx)} index={idx}
          getIndex={this.handleClick} testActiveState={this.props.testActiveState}></Square>);
        idx += 1;
      }
    }
    document.documentElement.style.setProperty('--squares-count', this.props.size.toString());

    return (
      <section className={style.squaresBoard}>
        {squares}
      </section>
    );
  }
}
