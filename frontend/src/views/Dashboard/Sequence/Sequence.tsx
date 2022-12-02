import style from './Sequence.module.css';

import Page from '@components/Page/Page';
import TestsBar from '@components/TestsBar/TestsBar';
import DashboardTable from '@components/DashboardTable/DashboardTable';
import { columnTitlesUpper, contentUpper, columnTitlesLower, contentSequenceLower } from '../chwilowa-baza-danych';

function Sequence() {
  localStorage.setItem('sequence-mode', 'active');
  localStorage.setItem('visual-mode', 'not-active');
  localStorage.setItem('verbal-mode', 'not-active');
  localStorage.setItem('number-mode', 'not-active');
  return (
    <Page>
      <div className={style.dashboardContainer}>
        <div className={style.dashboardContainerLeft}>
          <DashboardTable tableContent={contentUpper} columnTitles={columnTitlesUpper} />
          <DashboardTable tableContent={contentSequenceLower} columnTitles={columnTitlesLower}/>
        </div>
        <TestsBar />
      </div>
    </Page>
  );
}

export default Sequence;
