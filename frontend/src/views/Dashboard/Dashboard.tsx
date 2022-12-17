import style from './Dashboard.module.css';
import sequence from '@resources/img/sequenceTest.svg';
import visual from '@resources/img/visualTest.svg';
import verbal from '@resources/img/verbalTest.svg';
import number from '@resources/img/numberTest.svg';

import Page from '@components/Page/Page';
import DashboardTable from '@components/DashboardTable/DashboardTable';
import TestBox, { TestBoxEnum } from '@components/TestBox/TestBox';
import { useEffect, useRef, useState } from 'react';
import TestChart, { TestChartProps } from '@components/TestChart/TestChart';
import RestrictedRoute from '@components/RestrictedRoute/RestrictedRoute';
const fetchSequenceChartUrl = `${process.env.REACT_APP_BACKEND_URL}/tests/sequence`;
const fetchVisualChartUrl = `${process.env.REACT_APP_BACKEND_URL}/tests/visual`;
const fetchVerbalChartUrl = `${process.env.REACT_APP_BACKEND_URL}/tests/verbal`;
const fetchNumberChartUrl = `${process.env.REACT_APP_BACKEND_URL}/tests/number`;

const columnTitles = ['Lp', 'User ID', 'Date', 'Score'];
const columnTitlesPersonal = ['Scenario', 'Personal Best', 'Avg. Score', 'Percentile'];


interface PublicData {
  idTest: number;
  idUser: number;
  score: number;
  dateOfSubmission: string;
}

interface PersonalData {
  testName: string;
  personalBest: number;
  averageScore: number | string;
  percentile: string | string;
}


const Dashboard = (props: any) => {
  const [toggle, setToggle] = useState<TestBoxEnum>(TestBoxEnum.sequence);
  const [sequenceData, setSequenceData] = useState<PublicData[]>([]);
  const [visualData, setVisualData] = useState<PublicData[]>([]);
  const [verbalData, setVerbalData] = useState<PublicData[]>([]);
  const [numberData, setNumberData] = useState<PublicData[]>([]);
  const [personalData, setPersonalData] = useState([]);

  const [sequenceChartData, updateSequenceChartData] = useState<TestChartProps>({
    data: [],
    range: [0, 0],
  });
  const [visualChartData, updateVisualChartData] = useState<TestChartProps>({
    data: [],
    range: [0, 0],
  });
  const [verbalChartData, updateVerbalChartData] = useState<TestChartProps>({
    data: [],
    range: [0, 0],
  });
  const [numberChartData, updateNumberChartData] = useState<TestChartProps>({
    data: [],
    range: [0, 0],
  });

  const scroller = useRef<HTMLDivElement>(null);

  const sortFunction = (a: any, b: any) => {
    if (a.idTest < b.idTest) return 1;
    if (a.idTest > b.idTest) return -1;
    return 0;
  };

  useEffect(() => {
    fetch(`${process.env.REACT_APP_BACKEND_URL}/DashboardPublic`)
        .then((response) => response.json())
        .then((data) => {
          setNumberData(data[0]);
          setSequenceData(data[1]);
          setVerbalData(data[2]);
          setVisualData(data[3]);
        })
        .catch((error) => {
          console.error(error);
        });

    fetch(`${process.env.REACT_APP_BACKEND_URL}/DashboardPersonal`)
        .then((response) => response.json())
        .then((data) => {
          setPersonalData(data);
          console.log(data);
        })
        .catch((error) => {
          console.error(error);
        });

    fetch(fetchSequenceChartUrl, { method: 'GET', headers: { 'Content-Type': 'application/json' } })
        .then((response) => {
          if (response.ok) return response.json(); throw response;
        })
        .then((data: number[]) => {
          updateSequenceChartData({
            data,
            range: [0, data.length - 1],
          });
        })
        .catch((error) => {
          console.error(error);
        });

    fetch(fetchVisualChartUrl, { method: 'GET', headers: { 'Content-Type': 'application/json' } })
        .then((response) => {
          if (response.ok) return response.json(); throw response;
        })
        .then((data: number[]) => {
          updateVisualChartData({
            data,
            range: [0, data.length - 1],
          });
        })
        .catch((error) => {
          console.error(error);
        });

    fetch(fetchVerbalChartUrl, { method: 'GET', headers: { 'Content-Type': 'application/json' } })
        .then((response) => {
          if (response.ok) return response.json(); throw response;
        })
        .then((data: number[]) => {
          updateVerbalChartData({
            data,
            range: [0, data.length - 1],
          });
        })
        .catch((error) => {
          console.error(error);
        });

    fetch(fetchNumberChartUrl, { method: 'GET', headers: { 'Content-Type': 'application/json' } })
        .then((response) => {
          if (response.ok) return response.json(); throw response;
        })
        .then((data: number[]) => {
          updateNumberChartData({
            data,
            range: [0, data.length - 1],
          });
        })
        .catch((error) => {
          console.error(error);
        });
  }, []);

  const scrollHandler = () => {
    let target = '';
    const children = Array.from(scroller.current?.children ?? []);

    for (const child of children) {
      if (child instanceof HTMLElement) {
        if ((child.offsetTop - (scroller.current?.offsetTop ?? 0)) + child.scrollHeight > (scroller.current?.scrollTop ?? 0) + 100) {
          target = child.id;
          break;
        }
      }
    }

    if (document.location.hash !== `#${target}`) {
      window.history.replaceState({}, '', `#${target}`);
      setToggle(TestBoxEnum[target as keyof typeof TestBoxEnum]);
    }
  };

  useEffect(() => {
    const scrollerCopy = scroller.current;

    scrollerCopy?.addEventListener('scroll', scrollHandler);
    setToggle(TestBoxEnum[document.location.hash.slice(1) as keyof typeof TestBoxEnum]);
    scrollHandler();

    return () => {
      scrollerCopy?.removeEventListener('scroll', scrollHandler);
    };
  }, []);


  const toColumnData = (data: PublicData[]): PublicData[] => data.sort(sortFunction).slice(0, 6).map(({ dateOfSubmission, score, idUser }, i) => ({
    idTest: i + 1,
    idUser,
    dateOfSubmission,
    score,
  }));

  const toColumnDataPersonal = (data: PersonalData[]): PersonalData[] => data.map(({ testName, personalBest, averageScore, percentile }) => ({
    testName,
    personalBest,
    averageScore: (averageScore as number).toFixed(1),
    percentile: `${(percentile as unknown as number).toFixed(1)}%`,
  }));

  return (
    <Page titlebar={false}>
      <div className={style.dashboardContainer}>
        <div className={style.dashboardContainerLeft}>
          <DashboardTable tableContent={toColumnDataPersonal(personalData)} columnTitles={columnTitlesPersonal} />
          <div ref={scroller} className={style.scrollable}>
            <div id="sequence">
              <DashboardTable tableContent={toColumnData(sequenceData)} columnTitles={columnTitles}/>
              <div className={style.dashboardContainerChart}>
                <TestChart aspectRatio={0} {...sequenceChartData} userScore={null} />
              </div>
            </div>
            <div id="visual">
              <DashboardTable tableContent={toColumnData(visualData)} columnTitles={columnTitles} />
              <div className={style.dashboardContainerChart}>
                <TestChart aspectRatio={0} {...visualChartData} userScore={null} />
              </div>
            </div>
            <div id="verbal">
              <DashboardTable tableContent={toColumnData(verbalData)} columnTitles={columnTitles} />
              <div className={style.dashboardContainerChart}>
                <TestChart aspectRatio={0} {...verbalChartData} userScore={null} />
              </div>
            </div>
            <div id="number">
              <DashboardTable tableContent={toColumnData(numberData)} columnTitles={columnTitles} />
              <div className={style.dashboardContainerChart}>
                <TestChart aspectRatio={0} {...numberChartData} userScore={null} />
              </div>
            </div>
          </div>
        </div>
        <div className={style.dashboardContainerRight}>
          <a href="#sequence">
            <TestBox text={'sequence memory'} src={sequence} toggle={toggle} setToggle={setToggle} type={TestBoxEnum.sequence} />
          </a>
          <a href="#visual">
            <TestBox text={'visual memory'} src={visual} toggle={toggle} setToggle={setToggle} type={TestBoxEnum.visual} />
          </a>
          <a href="#verbal">
            <TestBox text={'verbal memory'} src={verbal} toggle={toggle} setToggle={setToggle} type={TestBoxEnum.verbal} />
          </a>
          <a href="#number">
            <TestBox text={'number memory'} src={number} toggle={toggle} setToggle={setToggle} type={TestBoxEnum.number} />
          </a>
        </div>
      </div>
    </Page>
  );
};

export default Dashboard;
