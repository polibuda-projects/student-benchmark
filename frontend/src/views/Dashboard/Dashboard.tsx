import style from './Dashboard.module.css';
import sequence from '@resources/img/sequenceTest.svg';
import visual from '@resources/img/visualTest.svg';
import verbal from '@resources/img/verbalTest.svg';
import number from '@resources/img/numberTest.svg';

import Page from '@components/Page/Page';
import DashboardTable from '@components/DashboardTable/DashboardTable';
import { columnTitlesUpper, contentUpper, columnTitlesLower, contentSequenceLower, contentVisualLower } from './chwilowa-baza-danych';
import { contentVerbalLower, contentNumberLower } from './chwilowa-baza-danych';
import TestBox, { TestBoxEnum } from '@components/TestBox/TestBox';
import { useEffect, useRef, useState } from 'react';
import TestChart from '@components/TestChart/TestChart';

const Dashboard = (props: any) => {
  const [toggle, setToggle] = useState<TestBoxEnum>(TestBoxEnum.sequence);
  const scroller = useRef<HTMLDivElement>(null);


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

  return (
    <Page titlebar={false}>
      <div className={style.dashboardContainer}>
        <div className={style.dashboardContainerLeft}>
          <DashboardTable tableContent={contentUpper} columnTitles={columnTitlesUpper} />
          <div ref={scroller} className={style.scrollable}>
            <div id="sequence">
              <DashboardTable tableContent={contentSequenceLower} columnTitles={columnTitlesLower}/>
              <div className={style.dashboardContainerChart}>
                <TestChart aspectRatio={0} data={Array(30).fill(0).map(() => Math.random() * 100 + 10)} range={[1, 30]} userScore={null} />
              </div>
            </div>
            <div id="visual">
              <DashboardTable tableContent={contentVisualLower} columnTitles={columnTitlesLower}/>
              <div className={style.dashboardContainerChart}>
                <TestChart aspectRatio={0} data={Array(30).fill(0).map(() => Math.random() * 100 + 10)} range={[1, 30]} userScore={null} />
              </div>
            </div>
            <div id="verbal">
              <DashboardTable tableContent={contentVerbalLower} columnTitles={columnTitlesLower}/>
              <div className={style.dashboardContainerChart}>
                <TestChart aspectRatio={0} data={Array(30).fill(0).map(() => Math.random() * 100 + 10)} range={[1, 30]} userScore={null} />
              </div>
            </div>
            <div id="number">
              <DashboardTable tableContent={contentNumberLower} columnTitles={columnTitlesLower}/>
              <div className={style.dashboardContainerChart}>
                <TestChart aspectRatio={0} data={Array(30).fill(0).map(() => Math.random() * 100 + 10)} range={[1, 30]} userScore={null} />
              </div>
            </div>
          </div>
        </div>
        <div className={style.dashboardContainerRight}>
          <a href="#sequence">
            <TestBox text={'sequence memory'} src={sequence} toggle={toggle} setToggle={setToggle} type={TestBoxEnum.sequence}/>
          </a>
          <a href="#visual">
            <TestBox text={'visual memory'} src={visual} toggle={toggle} setToggle={setToggle} type={TestBoxEnum.visual}/>
          </a>
          <a href="#verbal">
            <TestBox text={'verbal memory'} src={verbal} toggle={toggle} setToggle={setToggle} type={TestBoxEnum.verbal}/>
          </a>
          <a href="#number">
            <TestBox text={'number memory'} src={number} toggle={toggle} setToggle={setToggle} type={TestBoxEnum.number}/>
          </a>
        </div>
      </div>
    </Page>
  );
};

export default Dashboard;

