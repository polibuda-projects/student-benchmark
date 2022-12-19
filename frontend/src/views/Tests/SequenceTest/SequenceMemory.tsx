/* eslint-disable react-hooks/exhaustive-deps */
import Test, { TestProps, TestState } from '@components/Test/Test';
import { useEffect, useState } from 'react';
import style from './SequenceMemory.module.css';
import TestStart from '@components/Test/TestStart';
import logo from '@resources/img/sequenceTest.svg';
import TestEnd from '@components/Test/TestEnd';
const fetchUrlResult = `${process.env.REACT_APP_BACKEND_URL}/result/sequence`;
const fetchUrlChart = `${process.env.REACT_APP_BACKEND_URL}/tests/sequence`;

const testDescription = 'Memorize the sequence of buttons that light up, then press them in order. '+
'Every time you finish the pattern, it gets longer. '+
'Make a mistake, and the test is over.';

const shortTestDescription = 'Memorize the pattern.';

export default function SequenceTest() {
  const [state, updateState] = useState<TestState>('start');
  const [sequenceList, updateSequenceList] = useState<number[]>([]);
  const [inputList, updateInputList] = useState<number[]>([]);
  const [userScore, updateScore] = useState<number>(0);
  const [chartScore, updateChartScore] = useState<number | null>(null);

  const [chartData, updateChart] = useState<TestProps>({
    data: [],
    range: [0, 0],
  });

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
      throw new Error(`Error! status: ${response.status}`);
    }

    const result = (await response.json()) as number[];

    updateChart({
      data: result,
      range: [0, result.length - 1],
    });
  }

  useEffect(() => {
    if (state === 'start') {
      updateScore(0);
      updateChartScore(null);
      getChartData();
    } else if (state === 'playing') {
      updateScore(0);
      const tmp: number[] = sequenceList;
      tmp.push(Math.round(Math.random() * 8) + 1);
      updateSequenceList(tmp);
      updateInputList([]);
      showSequence();
    } else if (state === 'end') {
      sendResultRequest();
      updateChartScore(userScore);
    }
  }, [state]);

  const handleClick = (e: React.MouseEvent<HTMLInputElement>) => {
    const clickedElement:HTMLInputElement = e.target as HTMLInputElement;
    const clickedID:number = parseInt(clickedElement.id);
    clickedElement.style.background = 'white';
    setTimeout(() => {
      clickedElement.style.background = 'none';
    }, 150);

    inputList.push(clickedID);

    for (let i = 0; i < inputList.length; i++) {
      if (inputList[i]!==sequenceList[i]) {
        updateSequenceList([]);
        updateInputList([]);
        updateState('end');
        return;
      }
    }
    if (sequenceList.length!==0 && inputList.length===sequenceList.length && state === 'playing') {
      if (userScore===null) {
        updateScore(0);
      } else {
        updateScore(userScore + 1);
      }
      const tmp : number[] = sequenceList;
      tmp.push(Math.round(Math.random() * 8)+1);
      updateSequenceList(tmp);
      updateInputList([]);
      const sequenceContainer : HTMLInputElement = (document.getElementById('sequenceContainer') as HTMLInputElement);
      sequenceContainer.style.pointerEvents = 'none';
      setTimeout(() => {
        showSequence();
      }, 500);
    }
  };

  const showSequence = () => {
    let counter:number = sequenceList.length;
    sequenceList.forEach((val, i, x) => {
      setTimeout(() => {
        const highlightBox : HTMLInputElement = (document.getElementById(val.toString()) as HTMLInputElement);
        highlightBox.style.background = 'white';
        setTimeout(() => {
          highlightBox.style.background = 'none';
          counter -=1;
          if (counter===0) {
            const sequenceContainer : HTMLInputElement = (document.getElementById('sequenceContainer') as HTMLInputElement);
            sequenceContainer.style.pointerEvents = 'all';
          }
        }, 500);
      }, i * 800);
    });
  };

  const resultString = userScore === null ? '' : `${userScore} Point${userScore === 1 ? '' : 's'}`;

  return (<Test testName='Sequence Memory' chartData={chartData} userScore={chartScore} testDescription={testDescription}>

    {state === 'start' && <TestStart logoUrl={logo} updateState={updateState} shortDescription={shortTestDescription}/>}

    {state === 'end' && <TestEnd logoUrl={logo} result={resultString} updateState={updateState} />}

    {state === 'playing' &&
      <>
        <div className={style.testScore}>Score | {userScore}</div>

        <div id='sequenceContainer' className={[style.ContainerSequence].join(' ')} >
          <div id='1' style={{ background: 'none' }} className={[style.sequenceBox].join(' ')} onClick={handleClick}/>
          <div id='2' style={{ background: 'none' }} className={[style.sequenceBox].join(' ')} onClick={handleClick}/>
          <div id='3' style={{ background: 'none' }} className={[style.sequenceBox].join(' ')} onClick={handleClick}/>
          <div id='4' style={{ background: 'none' }} className={[style.sequenceBox].join(' ')} onClick={handleClick}/>
          <div id='5' style={{ background: 'none' }} className={[style.sequenceBox].join(' ')} onClick={handleClick}/>
          <div id='6' style={{ background: 'none' }} className={[style.sequenceBox].join(' ')} onClick={handleClick}/>
          <div id='7' style={{ background: 'none' }} className={[style.sequenceBox].join(' ')} onClick={handleClick}/>
          <div id='8' style={{ background: 'none' }} className={[style.sequenceBox].join(' ')} onClick={handleClick}/>
          <div id='9' style={{ background: 'none' }} className={[style.sequenceBox].join(' ')} onClick={handleClick}/>
        </div>
      </>
    }
  </Test>);
}

