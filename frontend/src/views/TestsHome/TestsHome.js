import style from './TestsHome.module.css';
import { Link } from 'react-router-dom';


import sequence from '@components/TestButtons/sequenceTest.svg';
import visual from '@components/TestButtons/visualTest.svg';
import verbal from '@components/TestButtons/verbalTest.svg';
import number from '@components/TestButtons/numberTest.svg';
import { TestButton } from '@components/TestButtons/TestButton';
import Page from '@components/Page/Page';


function TestsHome() {
  return (
    <Page title='Welcome'>
      <div className={style.blocks}>
        <div className={style.rowTests}>
          <div className={style.nullButton} div/>
          <Link className={style.testButton} to='/#'>
            <TestButton text='sequence memory' src={sequence} alt='sequence memory'/>
          </Link>
          <Link className={style.testButton} to='/#'>
            <TestButton text='visual memory' src={visual} alt='visual memory'/>
          </Link>
          <div className={style.nullButton} />
        </div>
        <div className={style.rowTests}>
          <div className={style.nullButton} />
          <Link className={style.testButton} to='/#'>
            <TestButton text='verbal memory' src={verbal} alt='verbal memory'/>
          </Link>
          <Link className={style.testButton} to='/#'>
            <TestButton text='number memory' src={number} alt='number memory'/>
          </Link>
          <div className={style.nullButton} div/>
        </div>
      </div>
    </Page>
  );
}


export default TestsHome;
