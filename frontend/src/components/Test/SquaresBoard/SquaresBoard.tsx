import { Component } from 'react';

import style from './SquaresBoard.module.css';


export interface SquaresBoardProps {
  size: number,
}

export default class SquaresBoard extends Component<SquaresBoardProps> {
  private static defaultProps: SquaresBoardProps = {
    size: 3,
  };

  render() {
    const squares = [];

    for (let i = 0; i < this.props.size; i++) {
      for (let j = 0; j < this.props.size; j++) {
        squares.push(<div className={style.square}></div>);
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
