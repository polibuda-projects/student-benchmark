import Test, { TestProps, TestState } from '@components/Test/Test';
import { useEffect, useState } from 'react';
import style from './SequenceMemory.module.css';
import ButtonMedium from '@components/Buttons/ButtonMedium';
import TestStart from '@components/Test/TestStart';
import logo from '@resources/img/sequenceTest.svg';
import TestEnd from '@components/Test/TestEnd';
import ContainerBox from '@components/ContainerBox/ContainerBox';
import { isDisabled } from '@testing-library/user-event/dist/utils';

const testDescription = 'Memorize the sequence of buttons that light up, then press them in order. '+
'Every time you finish the pattern, it gets longer. '+
'Make a mistake, and the test is over.';

const shortTestDescription = 'Memorize the pattern.';

export default function SequenceTest() {
  const [state, updateState] = useState<TestState>('start');
  const [sequenceList, updateSequenceList] = useState<number[]>([]);
  const [inputList, updateInputList] = useState<number[]>([]);
  const [userScore, updateScore] = useState<null | number>(0);
  const [chartData, updateChart] = useState<TestProps>({
    data: Array(30).fill(0).map(() => Math.random() * 100 + 10),
    range: [10, 30],
  });

  useEffect(() => {
    if (state === 'playing') {
      updateScore(0);
      const tmp : number[] = sequenceList;
      tmp.push(Math.round(Math.random() * 8)+1);
      updateSequenceList(tmp);
      updateInputList([]);
      showSequence();
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

  return (<Test testName='Sequence Memory' chartData={chartData} userScore={userScore} testDescription={testDescription}>

    {state === 'start' && <TestStart logoUrl={logo} updateState={updateState} shortDescription={shortTestDescription}/>}

    {state === 'end' && <TestEnd logoUrl={logo} result={resultString} updateState={updateState} updateScore={updateScore} />}

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
