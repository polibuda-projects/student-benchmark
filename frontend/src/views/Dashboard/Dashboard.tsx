import style from './Dashboard.module.css';
import sequence from './sequence.svg';
import visual from './visual.svg';
import verbal from './verbal.svg';
import number from './number.svg';

import Page from '@components/Page/Page';
import DashboardTable from '@components/DashboardTable/DashboardTable';
import { columnTitlesUpper, contentUpper, columnTitlesLower, contentSequenceLower, contentVisualLower } from './chwilowa-baza-danych';
import { contentVerbalLower, contentNumberLower } from './chwilowa-baza-danych';
import TestBox, { TestBoxEnum } from '@components/TestBox/TestBox';
import { useState } from 'react';

const Dashboard = () => {
  const [toggle, setToggle] = useState<TestBoxEnum>(TestBoxEnum.sequence);
  return (
    <Page titlebar={false}>
      <div className={style.dashboardContainer}>
        <div className={style.dashboardContainerLeft}>
          <DashboardTable tableContent={contentUpper} columnTitles={columnTitlesUpper} />
          <div className={style.scrollable}>
            <div id="sequency">
              <DashboardTable tableContent={contentSequenceLower} columnTitles={columnTitlesLower}/>
            </div>
            <div id="visual">
              <DashboardTable tableContent={contentVisualLower} columnTitles={columnTitlesLower}/>
            </div>
            <div id="verbal">
              <DashboardTable tableContent={contentVerbalLower} columnTitles={columnTitlesLower}/>
            </div>
            <div id="number">
              <DashboardTable tableContent={contentNumberLower} columnTitles={columnTitlesLower}/>
            </div>
          </div>
        </div>
        <div className={style.dashboardContainerRight}>
          <a href="#sequency">
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
