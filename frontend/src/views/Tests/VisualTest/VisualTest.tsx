import style from './VisualTest.module.css';
import Test, { TestProps, TestState } from '@components/Test/Test';
import { useEffect, useState } from 'react';
import TestStart from '@components/Test/TestStart';
import logo from '@resources/img/visualTest.svg';
import TestEnd from '@components/Test/TestEnd';
import SquaresBoard from '@views/Tests/VisualTest/SquaresBoard/SquaresBoard';
const fetchUrlResult = `${process.env.REACT_APP_BACKEND_URL}/result/visual`;
const fetchUrlChart = `${process.env.REACT_APP_BACKEND_URL}/tests/visual`;

const testDescription = 'Every level, a number of tiles will flash white. Memorize them, and pick them again after the tiles are reset! ' +
  'Levels get progressively more difficult, to challenge your skills. You have three lives. Make it as far as you can!';

const shortTestDescription = 'Memorize the squares';
export type TestActiveState = 'generate' | 'show' | 'resolve';

export default function VisualTest() {
  const [state, updateState] = useState<TestState>('start');
  const [userScore, updateScore] = useState<null | number>(null);

  const maxNumberOfLives = 3;
  const [userLives, updateLives] = useState<number>(maxNumberOfLives);

  // Count of squares in row and number of correct squares to click
  const numberOfSquaresSize =
    [3, 3, 4, 4, 4, 5, 5, 5, 5, 6,
      6, 6, 6, 6, 7, 7, 7, 7, 7, 7,
      8, 8, 8, 8, 8, 8, 8, 9, 9, 9];
  const numberOfWinners =
    [3, 4, 5, 6, 7, 8, 9, 10, 11, 12,
      13, 14, 15, 16, 17, 18, 19, 20, 21, 22,
      23, 24, 25, 26, 27, 28, 29, 30, 31, 32];

  const [randomWinnersIdx, updateRandomWinnersIdx] = useState<Set<number>>(randomWinnersIdxGen(numberOfWinners[0], numberOfSquaresSize[0]));
  const [testActiveState, updateTestActiveState] = useState<TestActiveState>('generate');

  const [chartData, updateChart] = useState<TestProps>({
    data: [],
    range: [0, 0],
  });

  const resultString = userScore === null ? '' : `${userScore} Point${userScore === 1 ? '' : 's'}`;

  function randomWinnersIdxGen(numOfWinnerSquares: number, squaresSize: number) {
    const nums = new Set<number>();
    while (nums.size < numOfWinnerSquares) {
      nums.add(Math.floor(Math.random() * ((squaresSize * squaresSize) - 1 + 1) + 1));
    }
    return nums;
  }

  // How many times user clicked correct squares
  const [activatedWinnersCount, updateActivatedWinnersCount] = useState<number>(0);

  // Do not let user make another click after:
  //  - chose that redirects to the next level,
  //  - chose that end's the game.
  type TestActiveStateClick = 'resolve' | 'check';
  const [testActiveStateClick, updateTestActiveStateClick] = useState<TestActiveStateClick>('resolve');

  // When user click square
  // Timeouts are for animations come to an end before reset the squares board
  const UserChoose = (isCorrect: boolean) => {
    if (testActiveStateClick === 'resolve' && testActiveState === 'resolve' && state === 'playing') {
      if (!isCorrect) {
        if (userLives === 1) {
          updateTestActiveStateClick('check');
          setTimeout(() => {
            updateState('end');
            updateTestActiveStateClick('resolve');
          }, 250);
        } else {
          updateLives(userLives - 1);
        }
      } else {
        updateActivatedWinnersCount(activatedWinnersCount + 1);
        // Go to next Level
        if ((activatedWinnersCount + 1) === numberOfWinners[userScore ? userScore : 0]) {
          updateTestActiveStateClick('check');
          setTimeout(() => {
            updateScore((userScore ? userScore : 0) + 1);
            updateTestActiveState('generate');
            updateTestActiveStateClick('resolve');
          }, 250);
        }
      }
    }
  };

  useEffect(() => {
    if (state === 'playing') {
      updateScore(0);
      updateLives(maxNumberOfLives);
      updateTestActiveState('generate');
    }
  }, [state]);

  useEffect(() => {
    if (testActiveState === 'generate') {
      updateRandomWinnersIdx(randomWinnersIdxGen(numberOfWinners[userScore ? userScore : 0], numberOfSquaresSize[userScore ? userScore : 0]));
      updateActivatedWinnersCount(0);
      updateTestActiveState('show');
    } else if (testActiveState === 'show') {
      showWinnerSquares();
    }
  }, [testActiveState]);

  async function sendResultRequest() {
    await fetch(fetchUrlResult, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        score: userScore,
      }),
    });
  }

  async function getChartData() {
    const response = await fetch(fetchUrlChart, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
      },
    });
    if (!response.ok) {
      throw new Error('Error! status: ${response.status}');
    }

    const result = (await response.json()) as number[];

    const max = Math.max(...result);
    const counts = new Map();

    for (let i = 0; i <= max; i++) {
      counts.set(i, 0);
    }

    for (const n of result) {
      counts.set(n, counts.get(n) + 1);
    }

    const countArray = Array.from(counts.values());
    console.log(result);
    console.log(countArray);

    updateChart({
      data: countArray,
      range: [0, countArray.length - 1],
    });
  }

  useEffect(() => {
    if (state === 'end') {
      sendResultRequest();
    }
  }, [state]);

  useEffect(() => {
    if (state === 'start') {
      getChartData();
    }
  }, [state]);

  const showWinnerSquares = () => {
    randomWinnersIdx.forEach((val) => {
      setTimeout(() => {
        const highlightBox: HTMLInputElement = (document.getElementById(val.toString()) as HTMLInputElement);
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

        <div className={style.testInfo}>
          <span className={style.testLives}>
            <p>Lives |</p> {userLives}
          </span>
          <span className={style.testScore}>
            <p>Score |</p> {userScore === null ? 0 : userScore}
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
    }
  </Test>);
}

