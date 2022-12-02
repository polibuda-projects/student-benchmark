import style from './Number.module.css';

import Page from '@components/Page/Page';
import TestsBar from '@components/TestsBar/TestsBar';
import DashboardTable from '@components/DashboardTable/DashboardTable';
import { columnTitlesUpper, contentUpper, columnTitlesLower, contentNumberLower } from '../chwilowa-baza-danych';

function Number() {
  localStorage.setItem('sequence-mode', 'not-active');
  localStorage.setItem('visual-mode', 'not-active');
  localStorage.setItem('verbal-mode', 'not-active');
  localStorage.setItem('number-mode', 'active');
  return (
    <Page>
      <div className={style.dashboardContainer}>
        <div className={style.dashboardContainerLeft}>
          <DashboardTable tableContent={contentUpper} columnTitles={columnTitlesUpper} />
          <DashboardTable tableContent={contentNumberLower} columnTitles={columnTitlesLower}/>
        </div>
        <TestsBar />
      </div>
    </Page>
  );
}

export default Number;
