import style from './NumberTest.module.css';
import Test, { TestProps, TestState } from '@components/Test/Test';
import { useEffect, useState } from 'react';
import ButtonMedium from '@components/Buttons/ButtonMedium';
import TestStart from '@components/Test/TestStart';
import logo from '@resources/img/numberTest.svg';
import TestEnd from '@components/Test/TestEnd';
import { NumberProperties } from '@components/Test/NumberComponent/NumberComponent';
import Input from '@components/Input/Input';
import { count } from 'console';
const fetchUrlResult = `${process.env.REACT_APP_BACKEND_URL}/result/number`;
const fetchUrlChart = `${process.env.REACT_APP_BACKEND_URL}/tests/number`;

const testDescription = 'The average person can only remember 7 digit numbers reliably, but it\'s possible to do much better using mnemonic techniques.';

const shortTestDescription = 'Remember the longest number you can.';

export default function NumberTest() {
  const [state, updateState] = useState<TestState>('start');
  const [userScore, updateScore] = useState<null | number>(null);
  const [currentLevel, updateLevel] = useState<number>(1);
  const [currentNumber, updateNumber] = useState<number>(Math.floor(Math.random() * 10));
  let [yourNumber, updateYourNumber] = useState<number>(0);

  const [chartData, updateChart] = useState<TestProps>({
    data: [],
    range: [0, 0],
  });
  const move = () => {
    const elem = document.getElementById('inner');
    if (elem) elem.style.width = '100%';

    setTimeout(() => {
      if (elem) elem.style.width = '0%';
    });

    if (elem) elem.style.transition = 'width ' + 2 * currentLevel * 0.5 + 's linear';
    setTimeout(() => {
      updateState('numberInput');
    }, 2000 * currentLevel * 0.5);
  };

  useEffect(() => {
    if (state === 'playing') {
      move();
    }
  }, [state]);

  useEffect(() => {
    if (state === 'start') {
      getChartData();
      updateScore(0);
      updateLevel(1);
      updateNumber(Math.floor(Math.random() * 10));
    }
  }, [state]);

  const handleSubmitClick = () => {
    const check = (document.getElementById('numberToCheck') as HTMLInputElement).value;
    if (check.length != 0) {
      updateYourNumber(yourNumber = check as unknown as number);
      if (yourNumber == currentNumber) {
        updateState('numberCorrect');
      } else {
        updateState('numberIncorrect');
      }
    }
  };

  document.onkeypress = function(event) {
    const elem = document.getElementById('nextStageButton');
    if (elem) handleNextLevelClick();
  };

  const handleNextLevelClick = () => {
    updateScore((userScore ?? 0) + 1);
    updateLevel(currentLevel + 1);
    const min = Math.pow(10, currentLevel);
    updateNumber(Math.floor(Math.random() * (Math.pow(10, currentLevel + 1) - min) + min));
    updateState('playing');
  };

  async function sendResultRequest() {
    console.log(userScore);
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

  const resultString = userScore === null ? '' : `${userScore} Point${userScore === 1 ? '' : 's'}`;

  return (<Test testName='Number Memory' testDescription={testDescription} chartData={chartData} userScore={userScore}>

    {state === 'start' && <TestStart logoUrl={logo} shortDescription={shortTestDescription} updateState={updateState} />}

    {state === 'end' && <TestEnd logoUrl={logo} result={resultString} updateState={updateState} updateScore={updateScore} />}

    {state === 'playing' &&
      <>
        <span className={style.levelText}>Level {currentLevel}</span>
        <div className={style.container}>
          <div className={style.testNumber}>
            <NumberProperties text={currentNumber} fontSize={'7rem'} />
            <div className={style.outer}>
              <div className={style.inner} id={'inner'}>
              </div>
            </div>
          </div>
        </div>
      </>
    }
    {state === 'numberInput' &&
      <>
        <span className={style.levelText}>Level {currentLevel}</span>
        <div className={style.container}>
          <NumberProperties className={style.textToScale} text={'Enter the showed number'} />
          <form onSubmit={handleSubmitClick} className={style.numberForm}>
            <Input className={style.numberInput} focus={true} id={'numberToCheck'} autoComplete={'off'} required={true} />
            <ButtonMedium width={'auto'} text='submit' onClick={handleSubmitClick} />
          </form>
        </div>
      </>
    }
    {state === 'numberCorrect' &&
      <>
        <span className={style.levelText}>Level {currentLevel}</span>
        <div className={style.container}>
          <NumberProperties text={'Correct!'} fontSize={'4rem'} />
          <ButtonMedium text='next level' onClick={handleNextLevelClick} id={'nextStageButton'} />
        </div>
      </>
    }
    {state === 'numberIncorrect' &&
      <>
        <span className={style.levelText}>Level {currentLevel}</span>
        <div className={style.container}>
          <div>
            <NumberProperties text={'Showed number'} fontSize={'2rem'} />
            <NumberProperties text={currentNumber} fontSize={'4rem'} />
          </div>
          <div>
            <NumberProperties text={'Your answer'} fontSize={'2rem'} />
            <NumberProperties text={yourNumber} fontSize={'4rem'} />
          </div>
          <NumberProperties className={style.textToScale} text={'You entered wrong number'} />
          <div className={style.buttons}>
            <ButtonMedium text='save score' onClick={() => {
              sendResultRequest();
              updateState('end');
            }} />
            <ButtonMedium text='try again' onClick={() => updateState('start')} />
          </div>
        </div>
      </>
    }
  </Test>);
}

