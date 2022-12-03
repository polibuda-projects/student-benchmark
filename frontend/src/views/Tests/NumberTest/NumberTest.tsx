import style from './NumberTest.module.css';
import Test, { TestProps, TestState } from '@components/Test/Test';
import { useState } from 'react';
import ButtonMedium from '@components/Buttons/ButtonMedium';
import TestStart from '@components/Test/TestStart';
import logo from '@components/TestButtons/numberTest.svg';
import TestEnd from '@components/Test/TestEnd';
import { NumberProperties } from '@components/Test/NumberComponent/NumberComponent';
import Input from '@components/Input/Input';

const testDescription='The average person can only remember 7 digit numbers reliably, but it\'s possible to do much better using mnemonic techniques.';

const shortTestDescription='Remember the longest number you can.';

export default function NumberTest() {
  const [state, updateState] = useState<TestState>('start');
  const [userScore, updateScore] = useState<null | number>(null);

  const [chartData, updateChart] = useState<TestProps>({
    data: Array(30).fill(0).map(() => Math.random() * 100 + 10),
    range: [10, 30],
  });

  const resultString = userScore === null ? '' : `${userScore} Point${userScore === 1 ? '' : 's'}`;

  return (<Test testName='Number Memory' testDescription={testDescription} chartData={chartData} userScore={userScore}>

    {state === 'start' && <TestStart logoUrl={logo} shortDescription={shortTestDescription} updateState={updateState} />}

    {state === 'end' && <TestEnd logoUrl={logo} result={resultString} updateState={updateState} updateScore={updateScore} />}

    {state === 'playing' &&
      <>
        <span className={style.levelText}>Level 2</span>
        <div className={style.container}>
          <div className={style.testNumber}>
            <NumberProperties text={'2137'} fontSize={'7rem'}/>
            <div className={style.outer}>
              <div className={style.inner}>
              </div>
            </div>
          </div>

          <ButtonMedium text='Next Stage' onClick={() => { // jak wyjebie przyciski to bedzie r�wno
            updateState('numberInput');
          }} />
        </div>
      </>
    }
    {state === 'numberInput' &&
      <>
        <span className={style.levelText}>Level 2</span>
        <div className={style.container}>
          <NumberProperties text={'Enter the showed number'} fontSize={'4rem'} />
          <Input />

          <ButtonMedium text='submit' onClick={() => updateState('numberCorrect')} />
        </div>
      </>
    }
    {state === 'numberCorrect' &&
      <>
        <span className={style.levelText}>Level 2</span>
        <div className={style.container}>
          <NumberProperties text={'Correct!'} fontSize={'4rem'} />
          <ButtonMedium text='next level' onClick={() => updateState('numberIncorrect')} />
        </div>
      </>
    }
    {state === 'numberIncorrect' &&
      <>
        <span className={style.levelText}>Level 2</span>
        <div className={style.container}>
          <div>
            <NumberProperties text={'Showed number'} fontSize={'2rem'} />
            <NumberProperties text={'45'} fontSize={'4rem'} />
          </div>
          <div>
            <NumberProperties text={'Your answer'} fontSize={'2rem'} />
            <NumberProperties text={'46'} fontSize={'4rem'} />
          </div>
          <NumberProperties text={'You entered wrong number'} fontSize={'4rem'} />
          <div className={style.buttons}>
            <ButtonMedium text='save score' onClick={() => {
              updateState('end');
              updateScore(Math.round(Math.random() * 50));
            }} />
            <ButtonMedium text='try again' onClick={() => updateState('start')} />
          </div>
        </div>
      </>
    }
  </Test>);
}
