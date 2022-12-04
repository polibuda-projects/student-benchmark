import { Component } from 'react';

import style from './SquaresBoard.module.css';
import Square from '@views/Tests/VisualTest/Square/Square';


export interface SquaresBoardProps {
  size: number,
  howManyWinners: number,
  randomWinnersIdx: Set<number>,
}

export default class SquaresBoard extends Component<SquaresBoardProps> {
  private static defaultProps: SquaresBoardProps = {
    size: 3,
    howManyWinners: 0,
    randomWinnersIdx: new Set(),
  };

  state = {
    activeIndex: null,
    // randomUniqueNum: Set,
  };
  handleClick = (index: number) => this.setState({ activeIndex: index });

  render() {
    const squares = [];

    // const randomUniqueIdx = new Set(this.randomUniqueNum());
    // this.setState({ randomUniqueNum: randomUniqueIdx });
    let idx = 1;
    for (let i = 0; i < this.props.size; i++) {
      for (let j = 0; j < this.props.size; j++) {
        squares.push(<Square key={idx} winner={this.props.randomWinnersIdx.has(idx)} index={idx}
          getIndex={this.handleClick}></Square>);
        idx += 1;
      }
    }
    document.documentElement.style.setProperty('--squares-count', this.props.size.toString());

    return (
      <section className={style.squaresBoard}>
        {squares}
        {this.state.activeIndex}
      </section>
    );
  }
}
