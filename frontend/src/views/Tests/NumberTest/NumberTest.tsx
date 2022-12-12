import style from './NumberTest.module.css';
import Test, { TestProps, TestState } from '@components/Test/Test';
import { useEffect, useState } from 'react';
import ButtonMedium from '@components/Buttons/ButtonMedium';
import TestStart from '@components/Test/TestStart';
import logo from '@resources/img/numberTest.svg';
import TestEnd from '@components/Test/TestEnd';
import { NumberProperties } from '@components/Test/NumberComponent/NumberComponent';
import Input from '@components/Input/Input';

const testDescription='The average person can only remember 7 digit numbers reliably, but it\'s possible to do much better using mnemonic techniques.';

const shortTestDescription='Remember the longest number you can.';

export default function NumberTest() {
  const [state, updateState] = useState<TestState>('start');
  const [userScore, updateScore] = useState<null | number>(0);
  const [currentLevel, updateLevel] = useState<number>(1);
  const [currentNumber, updateNumber] = useState<number>(Math.floor(Math.random() * 10));
  let [yourNumber, updateYourNumber] = useState<number>(0);

  const [chartData, updateChart] = useState<TestProps>({
    data: Array(30).fill(0).map(() => Math.random() * 100 + 10),
    range: [10, 30],
  });
  const move = () => {
    const elem = document.getElementById('inner');
    if (elem) elem.style.width = '100%';

    setTimeout(() => {
      if (elem) elem.style.width = '0%';
    });

    if (elem) elem.style.transition = 'width ' + 2 * currentLevel* 0.5 +'s linear';
    setTimeout(() => {
      updateState('numberInput');
    }, 2000 * currentLevel*0.5);
  };

  useEffect(() => {
    if (state === 'playing') {
      move();
    }
  }, [state]);

  useEffect(() => {
    if (state === 'start') {
      updateLevel(1);
      updateScore(0);
      updateNumber(Math.floor(Math.random() * 10));
    }
  }, [state]);

  const handleSubmitClick = () => {
    updateYourNumber(yourNumber = (document.getElementById('numberToCheck') as HTMLInputElement).value as unknown as number);
    if (yourNumber == currentNumber) {
      updateState('numberCorrect');
    } else {
      updateState('numberIncorrect');
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

  const resultString = userScore === null ? '' : `${userScore} Point${userScore === 1 ? '' : 's'}`;

  return (<Test testName='Number Memory' testDescription={testDescription} chartData={chartData} userScore={userScore}>

    {state === 'start' && <TestStart logoUrl={logo} shortDescription={shortTestDescription} updateState={updateState} />}

    {state === 'end' && <TestEnd logoUrl={logo} result={resultString} updateState={updateState} updateScore={updateScore} />}

    {state === 'playing' &&
      <>
        <span className={style.levelText}>Level {currentLevel}</span>
        <div className={style.container}>
          <div className={style.testNumber}>
            <NumberProperties text={currentNumber} fontSize={'7rem'}/>
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
          <NumberProperties text={'Enter the showed number'} fontSize={'4rem'} />
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
          <NumberProperties text={'You entered wrong number'} fontSize={'4rem'} />
          <div className={style.buttons}>
            <ButtonMedium text='save score' onClick={() => {
              updateState('end');
            }} />
            <ButtonMedium text='try again' onClick={() => updateState('start')} />
          </div>
        </div>
      </>
    }
  </Test>);
}

