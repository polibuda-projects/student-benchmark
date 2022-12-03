import style from './VerbalTest.module.css';
import Test, { TestProps, TestState } from '@components/Test/Test';
import { useState } from 'react';
import ButtonMedium from '@components/Buttons/ButtonMedium';
import TestStart from '@components/Test/TestStart';
import logo from '@components/TestButtons/verbalTest.svg';
import TestEnd from '@components/Test/TestEnd';
import { VerbalComponent, VerbalProperties } from '@components/Test/VerbalComponent/VerbalComponent';


const shortTestDescription='You will be shown words, one at a time. If you\'ve seen a word during the test, click SEEN. If it\'s a new word, click NEW.';

const testDescription='This test measures how many words you can keep in short term memory at once.'+
'Go as long as you can. You have 3 strikes until game over'+
'Your score is how many turns you lasted.';


export default function VerbalTest() {
  const [state, updateState] = useState<TestState>('start');
  const [userScore, updateScore] = useState<null | number>(null);

  const [chartData, updateChart] = useState<TestProps>({
    data: Array(30).fill(0).map(() => Math.random() * 100 + 10),
    range: [10, 30],
  });

  const resultString = userScore === null ? '' : `${userScore} Point${userScore === 1 ? '' : 's'}`;

  return (<Test testName='Verbal Memory' testDescription={testDescription} chartData={chartData} userScore={userScore}>

    {state === 'start' && <TestStart logoUrl={logo} updateState={updateState} shortDescription={shortTestDescription}/>}

    {state === 'end' && <TestEnd logoUrl={logo} result={resultString} updateState={updateState} updateScore={updateScore} />}

    {state === 'playing' &&
        <>
          {/* <ButtonMedium text='Change Score' onClick={() => updateScore(Math.round(Math.random() * 30))} />
          <ButtonMedium text='Change Chart Data' onClick={() => updateChart({
            data: Array(30).fill(0).map(() => Math.random() * 100 + 10),
            range: [10, 30],
          })} /> */}
          <div className={style.verbalProps}>
            <VerbalProperties className={style.verbalProperties} text='Lives | 3'/>

            <VerbalProperties className={style.verbalProperties} text='Score | 2'/>
          </div>
          <VerbalComponent className={style.testWord} text={randomWord()}/>
          <div className={style.verbalButtons}>
            <ButtonMedium className={style.seenButton} text='SEEN' onClick={() => VerbalComponent}/>

            <ButtonMedium className={style.newButton} text='NEW' onClick={() => VerbalComponent} />
          </div>
          <ButtonMedium text='End Test' onClick={() => {
            updateState('end');
            updateScore(Math.round(Math.random() * 50));
          }} />
        </>
    }
  </Test>);
}


function randomWord() {
  const exampleWords:Array<string> = ['dupajana', 'heja', 'piwo', 'polibuda', 'marek', 'jarek', 'satan'];
  return (
    exampleWords[Math.floor(Math.random()*7)]
  );
}
