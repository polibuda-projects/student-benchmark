import style from './VisualTest.module.css';
import Test, { TestProps, TestState } from '@components/Test/Test';
import { useEffect, useState } from 'react';
import ButtonMedium from '@components/Buttons/ButtonMedium';
import TestStart from '@components/Test/TestStart';
import logo from '@components/TestButtons/visualTest.svg';
import TestEnd from '@components/Test/TestEnd';
import SquaresBoard from '@views/Tests/VisualTest/SquaresBoard/SquaresBoard';

const testDescription = 'Every level, a number of tiles will flash white. Memorize them, and pick them again after the tiles are reset!' +
  'Levels get progressively more difficult, to challenge your skills. If you miss 3 tiles on a level, you lose one life.' +
  'You have three lives.Make it as far as you can!';

const shortTestDescription = 'Memorize the squares';
export type TestActiveState = 'show' | 'resolve';

export default function VisualTest() {
  const [state, updateState] = useState<TestState>('start');
  const [userScore, updateScore] = useState<null | number>(null);

  const maxNumberOfLives = 3;
  const [userLives, updateLives] = useState<number>(maxNumberOfLives);

  // Count of squares in row and number of correct squares to click (this will be in database?)
  const numberOfSquaresSize = [3, 4, 4, 5, 5, 5, 5, 5, 5, 6, 6, 6];
  const numberOfWinners = [3, 4, 5, 5, 6, 6, 7, 8, 9, 10, 10, 11];
  const [randomWinnersIdx, updateRandomWinnersIdx] = useState<Set<number>>(randomWinnersIdxGen(numberOfWinners[0], numberOfSquaresSize[0]));

  const [testActiveState, updateTestActiveState] = useState<TestActiveState>();

  const [chartData, updateChart] = useState<TestProps>({
    data: Array(30).fill(0).map(() => Math.random() * 100 + 10),
    range: [10, 30],
  });

  const resultString = userScore === null ? '' : `${userScore} Point${userScore === 1 ? '' : 's'}`;

  function randomWinnersIdxGen(numOfWinnerSquares : number, squaresSize : number) {
    const nums = new Set<number>();
    while (nums.size < numOfWinnerSquares) {
      nums.add(Math.floor(Math.random() * ((squaresSize * squaresSize) - 1 + 1) + 1));
    }
    return nums;
  }

  // How many times user clicked correct squares
  let activatedWinnersCount = 0;
  // When user click square
  const UserChoose = (isCorrect: boolean) => {
    if (testActiveState === 'resolve') {
      if (!isCorrect) {
        if (userLives === 1) {
          updateState('end');
        } else {
          updateLives(userLives - 1);
        }
      } else {
        activatedWinnersCount += 1;
        // Go to next Level
        if (activatedWinnersCount === numberOfWinners[userScore ? userScore : 0]) {
          activatedWinnersCount = 0;
          const actualScore = (userScore ? userScore : 0) + 1;
          updateScore(actualScore);
          updateLives(maxNumberOfLives);
          updateRandomWinnersIdx(randomWinnersIdxGen(numberOfWinners[actualScore], numberOfSquaresSize[actualScore]));
          updateTestActiveState('show');
        }
      }
    }
  };

  useEffect(() => {
    if (state === 'playing') {
      updateScore(0);
      updateLives(maxNumberOfLives);
      updateRandomWinnersIdx(randomWinnersIdxGen(numberOfWinners[0], numberOfSquaresSize[0]));
      updateTestActiveState('show');
    }
  }, [state]);

  useEffect(() => {
    if (testActiveState === 'show') {
      updateLives(maxNumberOfLives);
      showWinnerSquares();
    }
  }, [testActiveState]);

  const showWinnerSquares = () => {
    randomWinnersIdx.forEach((val) => {
      setTimeout(() => {
        const highlightBox : HTMLInputElement = (document.getElementById(val.toString()) as HTMLInputElement);
        highlightBox.classList.add('active');
        setTimeout(() => {
          highlightBox.classList.remove('active');
          highlightBox.classList.add('neutral');
          setTimeout(() => {
            updateTestActiveState('resolve');
          }, 250);
        }, 800);
      }, 600);
    });
  };

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
            <SquaresBoard size={numberOfSquaresSize[userScore ? userScore : 0]} randomWinnersIdx={randomWinnersIdx}
              correctTileRespond={UserChoose} testActiveState={false}/>
          }

          {testActiveState === 'resolve' &&
            <SquaresBoard size={numberOfSquaresSize[userScore ? userScore : 0]} randomWinnersIdx={randomWinnersIdx}
              correctTileRespond={UserChoose} testActiveState={true}/>
          }

        </section>
      </section>
    }
  </Test>);
}
