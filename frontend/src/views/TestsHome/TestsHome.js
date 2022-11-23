import style from './TestsHome.module.css';
import { Link } from 'react-router-dom';

import { SequenceButton, VisualButton, NumberButton, VerbalButton } from '@components/TestButtons/TestButton';
import Page from '@components/Page/Page';


function TestsHome() {
  return (
    <Page title='Welcome'>
      <div className={style.rowTests}>
        <div className={style.nullButton} div/>
        <Link className={style.testButton} to='/#'>
          <SequenceButton text='sequence memory' />
        </Link>
        <Link className={style.testButton} to='/#'>
          <VisualButton text='visual memory' />
        </Link>
        <div className={style.nullButton} />
      </div>
      <div className={style.rowTests}>
        <div className={style.nullButton} />
        <Link className={style.testButton} to='/#'>
          <VerbalButton text='verbal memory' />
        </Link>
        <Link className={style.testButton} to='/#'>
          <NumberButton text='number memory' />
        </Link>
        <div className={style.nullButton} div/>
      </div>
    </Page>
  );
}


export default TestsHome;
