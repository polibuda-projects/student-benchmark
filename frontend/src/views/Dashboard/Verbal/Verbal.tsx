import style from './Verbal.module.css';

import Page from '@components/Page/Page';
import TestsBar from '@components/TestsBar/TestsBar';
import DashboardTable from '@components/DashboardTable/DashboardTable';
import { columnTitlesUpper, contentUpper, columnTitlesLower, contentVerbalLower } from '../chwilowa-baza-danych';

function Verbal() {
  localStorage.setItem('sequence-mode', 'not-active');
  localStorage.setItem('visual-mode', 'not-active');
  localStorage.setItem('verbal-mode', 'active');
  localStorage.setItem('number-mode', 'not-active');
  return (
    <Page>
      <div className={style.dashboardContainer}>
        <div className={style.dashboardContainerLeft}>
          <DashboardTable tableContent={contentUpper} columnTitles={columnTitlesUpper} />
          <DashboardTable tableContent={contentVerbalLower} columnTitles={columnTitlesLower}/>
        </div>
        <TestsBar />
      </div>
    </Page>
  );
}

export default Verbal;
