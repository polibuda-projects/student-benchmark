import style from './TestsBar.module.css';

import sequence from './sequence.svg';
import visual from './visual.svg';
import verbal from './verbal.svg';
import number from './number.svg';
import { NavLink } from 'react-router-dom';

const TestsBar = () => {
  let toggleSequenceActive;
  let toggleVisualActive;
  let toggleVerbalActive;
  let toggleNumberActive;

  if (localStorage.getItem('sequence-mode') === 'active') {
    toggleSequenceActive = style.dashboardContainerRightBoxSequenceActive;
  } else if (localStorage.getItem('sequence-mode') === 'not-active') {
    toggleSequenceActive = style.dashboardContainerRightBoxSequence;
  }

  if (localStorage.getItem('visual-mode') === 'active') {
    toggleVisualActive = style.dashboardContainerRightBoxVisualActive;
  } else if (localStorage.getItem('visual-mode') === 'not-active') {
    toggleVisualActive = style.dashboardContainerRightBoxVisual;
  }

  if (localStorage.getItem('verbal-mode') === 'active') {
    toggleVerbalActive = style.dashboardContainerRightBoxVerbalActive;
  } else if (localStorage.getItem('verbal-mode') === 'not-active') {
    toggleVerbalActive = style.dashboardContainerRightBoxVerbal;
  }

  if (localStorage.getItem('number-mode') === 'active') {
    toggleNumberActive = style.dashboardContainerRightBoxNumberActive;
  } else if (localStorage.getItem('number-mode') === 'not-active') {
    toggleNumberActive = style.dashboardContainerRightBoxNumber;
  }

  return (
    <div className={style.dashboardContainerRight}>
      <NavLink to='/dashboard-sequence' className={`${toggleSequenceActive}`}>
        <img
          className={style.dashboardContainerRightBoxImage}
          src={sequence}
          alt="sequence"
        />
        <span className={style.dashboardContainerRightBoxText}>
          sequence memory
        </span>
      </NavLink>
      <NavLink to='/dashboard-visual' className={`${toggleVisualActive}`}>
        <img
          className={style.dashboardContainerRightBoxImage}
          src={visual}
          alt="visual"
        />
        <span className={style.dashboardContainerRightBoxText}>
          visual memory
        </span>
      </NavLink>
      <NavLink to='/dashboard-verbal' className={`${toggleVerbalActive}`}>
        <img
          className={style.dashboardContainerRightBoxImage}
          src={verbal}
          alt="verbal"
        />
        <span className={style.dashboardContainerRightBoxText}>
          verbal memory
        </span>
      </NavLink>
      <NavLink to='/dashboard-number' className={`${toggleNumberActive}`}>
        <img
          className={style.dashboardContainerRightBoxImage}
          src={number}
          alt="number"
        />
        <span className={style.dashboardContainerRightBoxText}>
          number memory
        </span>
      </NavLink>
    </div>
  );
};

export default TestsBar;
