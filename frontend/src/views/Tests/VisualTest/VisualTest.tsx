import style from './VisualTest.module.css';
import Test, { TestProps, TestState } from '@components/Test/Test';
import { useState } from 'react';
import ButtonMedium from '@components/Buttons/ButtonMedium';
import TestStart from '@components/Test/TestStart';
import logo from '@components/TestButtons/visualTest.svg';
import TestEnd from '@components/Test/TestEnd';

const testDescription='Every level, a number of tiles will flash white. Memorize them, and pick them again after the tiles are reset!'+
'Levels get progressively more difficult, to challenge your skills. If you miss 3 tiles on a level, you lose one life.'+
'You have three lives.Make it as far as you can!';

const shortTestDescription='Memorize the squares';

export default function VisualTest() {
  const [state, updateState] = useState<TestState>('start');
  const [userScore, updateScore] = useState<null | number>(null);

  const [chartData, updateChart] = useState<TestProps>({
    data: Array(30).fill(0).map(() => Math.random() * 100 + 10),
    range: [10, 30],
  });

  const resultString = userScore === null ? '' : `${userScore} Point${userScore === 1 ? '' : 's'}`;

  return (<Test testName='Visual Memory' testDescription={testDescription} chartData={chartData} userScore={userScore}>

    {state === 'start' && <TestStart shortDescription={shortTestDescription} logoUrl={logo} updateState={updateState} />}

    {state === 'end' && <TestEnd logoUrl={logo} result={resultString} updateState={updateState} updateScore={updateScore} />}

    {state === 'playing' &&
      <>
        <ButtonMedium text='Change Score' onClick={() => updateScore(Math.round(Math.random() * 30))} />
        <ButtonMedium text='Change Chart Data' onClick={() => updateChart({
          data: Array(30).fill(0).map(() => Math.random() * 100 + 10),
          range: [10, 30],
        })} />

        <ButtonMedium text='End Test' onClick={() => {
          updateState('end');
          updateScore(Math.round(Math.random() * 50));
        }} />
      </>
    }
  </Test>);
}
