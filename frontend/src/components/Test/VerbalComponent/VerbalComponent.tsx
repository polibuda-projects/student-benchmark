import { Component } from 'react';

import style from './VerbalComponent.module.css';

export interface VerbalComponentProps {
  textScore?: string,
  textLives?: string,
  testWord?: string,
  width?: string,
  lives: number,
  score: number,
}

export default class VerbalComponent extends Component<VerbalComponentProps> {
  render() {
    return (
      <>
        <div className={style.verbalContainer}>
          <span className={style.testProperties} style={{ width: this.props.width }}>
            <div className={style.testValues}>
              {this.props.textLives}
            </div>
            <div className={style.number}>
              {this.props.lives}
            </div>

            <div className={style.testValues}>
              {this.props.textScore}
            </div>

            <div className={style.number}>
              {this.props.score}
            </div>
          </span>
          <span className={style.testWord} style={{ width: this.props.width }}>
            {this.props.testWord}
          </span>
        </div>
      </>
    );
  }
}



