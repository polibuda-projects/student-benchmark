/* eslint-disable react-hooks/exhaustive-deps */
import style from './VerbalTest.module.css';
import Test, { TestProps, TestState } from '@components/Test/Test';
import { useEffect, useState } from 'react';
import ButtonMedium from '@components/Buttons/ButtonMedium';
import TestStart from '@components/Test/TestStart';
import logo from '@resources/img/verbalTest.svg';
import TestEnd from '@components/Test/TestEnd';
import VerbalComponent from '@components/Test/VerbalComponent/VerbalComponent';
const fetchUrlResult = `${process.env.REACT_APP_BACKEND_URL}/result/verbal`;
const fetchUrlChart = `${process.env.REACT_APP_BACKEND_URL}/tests/verbal`;
const fetchUrlWord = `${process.env.REACT_APP_BACKEND_URL}/tests/verbalWords`;


const shortTestDescription='You will be shown words, one at a time. If you\'ve seen a word during the test, click SEEN. If it\'s a new word, click NEW.';

const testDescription='This test measures how many words you can keep in short term memory at once.'+
'Go as long as you can. You have 3 strikes until game over'+
'Your score is how many turns you lasted.';


export default function VerbalTest() {
  const words = new Set<string>();
  const randomWordPickerNew = (): string => [...wordList][Math.floor(Math.random() * wordList.size)];
  const randomWordPickerSeen = (): string => [...seenWords][Math.floor(Math.random() * seenWords.size)];

  const [state, updateState] = useState<TestState>('start');
  const [userScore, updateScore] = useState<number | null>(null);
  const [userLives, updateLives] = useState<number>(3);
  const [wordList, setWordList] = useState<Set<string>>(new Set<string>([]));
  const [seenWords, updateSeenWords] = useState<Set<string>>(new Set<string>([]));
  const [activeWord, updateActiveWord] = useState<string>(randomWordPickerNew());

  const [chartData, updateChart] = useState<TestProps>({
    data: [],
    range: [0, 0],
  });

  const randomWordPicker = () => {
    if (Math.random() < 0.5) {
      return randomWordPickerNew();
    } else {
      if (seenWords.size < 2) {
        return randomWordPickerNew();
      } else {
        return randomWordPickerSeen();
      }
    }
  };

  const handleNewClick = () => {
    if (seenWords.has(activeWord)) {
      updateLives(userLives - 1);
    } else {
      updateScore((userScore ?? 0) + 1);
    }

    updateSeenWords(new Set<string>([activeWord, ...seenWords]));
  };


  const handleSeenClick = () => {
    if (seenWords.has(activeWord)) {
      updateScore((userScore ?? 0) + 1);
    } else {
      updateLives(userLives - 1);
    }

    updateSeenWords(new Set<string>([activeWord, ...seenWords]));
  };

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

  async function getWords() {
    const wordsData = await fetch(fetchUrlWord, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
      },
    });

    if (!wordsData.ok) {
      throw new Error(`Error! status: ${wordsData.status}`);
    }
    return (await wordsData.json()) as string[];
  };

  useEffect(() => {
    if (state === 'start') {
      updateScore(null);
      getChartData();
      updateLives(3);
      updateSeenWords(new Set<string>([]));
    }
  }, [state]);

  useEffect(() => {
    if (state === 'playing') {
      let word: string;

      do {
        word = randomWordPicker();
        try {
          wordList.delete(word);
        } catch (e) {}
      } while (words.has(word));

      updateActiveWord(word);
    }
  }, [seenWords, state]);


  useEffect(() => {
    if (userLives === 0 && state === 'playing') {
      updateState('end');
      sendResultRequest();
    }
  }, [userLives, state]);


  useEffect(() => {
    if (state === 'start') {
      async function getWordsAsync() {
        const result = getWords();
        setWordList(new Set<string>(await result));
      }
      getWordsAsync();
    }
  }, [state]);

  const resultString = userScore === null ? '' : `${userScore} Point${userScore === 1 ? '' : 's'}`;

  return (<Test testName='Verbal Memory' testDescription={testDescription} chartData={chartData} userScore={userScore}>

    {state === 'start' && <TestStart logoUrl={logo} updateState={updateState} shortDescription={shortTestDescription}/>}

    {state === 'end' && <TestEnd logoUrl={logo} result={resultString} updateState={updateState} updateScore={updateScore} />}

    {state === 'playing' &&
        <>
          <VerbalComponent
            textLives="Lives |"
            lives={userLives}
            textScore='Score |'
            score={userScore ?? 0}
            testWord={activeWord}/>

          <div className={style.verbalButtons}>
            <ButtonMedium className={style.seenButton} text='SEEN' onClick={handleSeenClick}/>

            <ButtonMedium className={style.newButton} text='NEW' onClick={handleNewClick} />
          </div>
        </>
    }
  </Test>);
}

