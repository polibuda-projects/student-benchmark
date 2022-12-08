import style from './VisualTest.module.css';
import Test, { PageProps, TestProps, TestState } from '@components/Test/Test';
import { Component, HTMLAttributes, useState } from 'react';
import ButtonMedium from '@components/Buttons/ButtonMedium';
import TestStart from '@components/Test/TestStart';
import logo from '@components/TestButtons/visualTest.svg';
import TestEnd from '@components/Test/TestEnd';
import SquaresBoard from '@views/Tests/VisualTest/SquaresBoard/SquaresBoard';

const testDescription = 'Every level, a number of tiles will flash white. Memorize them, and pick them again after the tiles are reset!' +
  'Levels get progressively more difficult, to challenge your skills. If you miss 3 tiles on a level, you lose one life.' +
  'You have three lives.Make it as far as you can!';

const shortTestDescription = 'Memorize the squares';

export default function VisualTest() {
  const [state, updateState] = useState<TestState>('start');
  const [userScore, updateScore] = useState<null | number>(null);
  const [userLives, updateLives] = useState<number>(3);
  // Count of squares in row
  const [squaresSize, updateSquaresSize] = useState<number>(3);
  const [countOfWinnerSquares, updateCountOfWinnerSquares] = useState<number>(3);

  const [chartData, updateChart] = useState<TestProps>({
    data: Array(30).fill(0).map(() => Math.random() * 100 + 10),
    range: [10, 30],
  });

  const resultString = userScore === null ? '' : `${userScore} Point${userScore === 1 ? '' : 's'}`;
  const livesString = `Lives | ${userLives}`;
  const scoreString = `Score | ${userScore}`;

  type TestActiveState = 'show' | 'resolve';
  const [testActiveState, updateTestActiveState] = useState<TestActiveState>('show');

  function randomWinnersIdx() {
    const nums = new Set<number>();
    while (nums.size < countOfWinnerSquares) {
      nums.add(Math.floor(Math.random() * ((squaresSize * squaresSize) - 1 + 1) + 1));
    }
    return nums;
  }

  return (<Test testName='Visual Memory' testDescription={testDescription} chartData={chartData} userScore={userScore}>

    {state === 'start' && <TestStart shortDescription={shortTestDescription} logoUrl={logo} updateState={updateState}/>}

    {state === 'end' &&
      <TestEnd logoUrl={logo} result={resultString} updateState={updateState} updateScore={updateScore}/>}

    {state === 'playing' &&
      <section>
        <div className={style.testButtons}>
          <ButtonMedium text='Change Score' onClick={() => updateScore(Math.round(Math.random() * 30))}/>
          <ButtonMedium text='Change Chart Data' onClick={() => updateChart({
            data: Array(30).fill(0).map(() => Math.random() * 100 + 10),
            range: [10, 30],
          })}/>

          <ButtonMedium text='End Test' onClick={() => {
            updateState('end');
            updateScore(Math.round(Math.random() * 50));
          }}/>
        </div>

        <section>

          <div className={style.testInfo}>
            <span className={style.testLives}>
              Lives | {userLives}
            </span>
            <span className={style.testScore}>
              Score | {userScore === null ? 0 : userScore}
            </span>
          </div>

          {testActiveState === 'show' &&
            <div></div>
          }

          {testActiveState === 'resolve' &&
            <div></div>
          }
          <SquaresBoard size={3} howManyWinners={3} randomWinnersIdx={randomWinnersIdx()}/>

        </section>
      </section>
    }
  </Test>);
}
