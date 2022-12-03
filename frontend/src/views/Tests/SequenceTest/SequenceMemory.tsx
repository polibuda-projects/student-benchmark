import Test, { TestProps, TestState } from '@components/Test/Test';
import { useState } from 'react';
import style from './SequenceMemory.module.css';
import ButtonMedium from '@components/Buttons/ButtonMedium';
import TestStart from '@components/Test/TestStart';
import logo from '@components/TestButtons/sequenceTest.svg';
import TestEnd from '@components/Test/TestEnd';
import SequenceBox from '@components/Test/SequenceBox/SequenceBox';
import ContainerSequence from '@components/Test/ContainerSequence/ContainerSequence';

const testDescription = 'Memorize the sequence of buttons that light up, then press them in order. '+
'Every time you finish the pattern, it gets longer. '+
'Make a mistake, and the test is over.';

const shortTestDescription = 'Memorize the pattern.';

export default function SequenceTest() {
  const [state, updateState] = useState<TestState>('start');
  const [userScore, updateScore] = useState<null | number>(0);
  const [sequenceList, updateSequence] = useState<number[]>([]);
  const [chartData, updateChart] = useState<TestProps>({
    data: Array(30).fill(0).map(() => Math.random() * 100 + 10),
    range: [10, 30],
  });

  const resultString = userScore === null ? '' : `${userScore} Point${userScore === 1 ? '' : 's'}`;

  return (<Test testName='Sequence Memory' chartData={chartData} userScore={userScore} testDescription={testDescription}>

    {state === 'start' && <TestStart logoUrl={logo} updateState={updateState} shortDescription={shortTestDescription}/>}

    {state === 'end' && <TestEnd logoUrl={logo} result={resultString} updateState={updateState} updateScore={updateScore} />}

    {state === 'playing' &&
      <>
        <div className={style.testScore}>Score | 6</div>
        <ContainerSequence >
          <SequenceBox fill={'none'} id={1}/>
          <SequenceBox fill={'none'} id={2}></SequenceBox>
          <SequenceBox fill={'none'} id={3}></SequenceBox>
          <SequenceBox fill={'none'} id={4}></SequenceBox>
          <SequenceBox fill={'none'} id={5}></SequenceBox>
          <SequenceBox fill={'none'} id={6}></SequenceBox>
          <SequenceBox fill={'none'} id={7}></SequenceBox>
          <SequenceBox fill={'none'} id={8}></SequenceBox>
          <SequenceBox fill={'none'} id={9}></SequenceBox>
        </ContainerSequence>
        { /* end test tylko żeby przejsc dalej wizualnie */ }
        <ButtonMedium text='End Test' onClick={() => {
          updateState('end');
          updateScore(Math.round(Math.random() * 50));
        }} />

        {/*
          <ButtonMedium text='Change Score' onClick={() => updateScore(Math.round(Math.random() * 30))} />
          <ButtonMedium text='Change Chart Data' onClick={() => updateChart({
            data: Array(30).fill(0).map(() => Math.random() * 100 + 10),
            range: [10, 30],
          })} />

          <ButtonMedium text='End Test' onClick={() => {
            updateState('end');
            updateScore(Math.round(Math.random() * 50));
          }} />*
        */}
      </>
    }
  </Test>);
}
