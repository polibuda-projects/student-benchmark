import style from './Visual.module.css';

import Page from '@components/Page/Page';
import TestsBar from '@components/TestsBar/TestsBar';
import DashboardTable from '@components/DashboardTable/DashboardTable';
import { columnTitlesUpper, contentUpper, columnTitlesLower, contentVisualLower } from '../chwilowa-baza-danych';

function Visual() {
  localStorage.setItem('sequence-mode', 'not-active');
  localStorage.setItem('visual-mode', 'active');
  localStorage.setItem('verbal-mode', 'not-active');
  localStorage.setItem('number-mode', 'not-active');
  return (
    <Page>
      <div className={style.dashboardContainer}>
        <div className={style.dashboardContainerLeft}>
          <DashboardTable tableContent={contentUpper} columnTitles={columnTitlesUpper} />
          <DashboardTable tableContent={contentVisualLower} columnTitles={columnTitlesLower}/>
        </div>
        <TestsBar />
      </div>
    </Page>
  );
}

export default Visual;
