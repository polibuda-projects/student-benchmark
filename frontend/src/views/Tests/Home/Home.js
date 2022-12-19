import style from './Home.module.css';
import { Link } from 'react-router-dom';


import sequence from '@resources/img/sequenceTest.svg';
import visual from '@resources/img/visualTest.svg';
import verbal from '@resources/img/verbalTest.svg';
import number from '@resources/img/numberTest.svg';
import { TestButton } from '@components/TestButtons/TestButton';
import Page from '@components/Page/Page';


function TestsHome() {
  return (
    <Page title='Get started'>
      <div className={style.blocks}>
        <div className={style.rowTests}>
          <div className={style.nullButton} />
          <Link className={style.testButton} to='/tests/sequence'>
            <TestButton text='sequence memory' src={sequence} alt='sequence memory'/>
          </Link>
          <Link className={style.testButton} to='/tests/visual'>
            <TestButton text='visual memory' src={visual} alt='visual memory'/>
          </Link>
          <div className={style.nullButton} />
        </div>
        <div className={style.rowTests}>
          <div className={style.nullButton} />
          <Link className={style.testButton} to='/tests/verbal'>
            <TestButton text='verbal memory' src={verbal} alt='verbal memory'/>
          </Link>
          <Link className={style.testButton} to='/tests/number'>
            <TestButton text='number memory' src={number} alt='number memory'/>
          </Link>
          <div className={style.nullButton} />
        </div>
        <div className={style.rowTests}>
          <div className={style.nullButtonMobile} />
          <div className={style.nullButtonMobile} />
        </div>
        <div className={style.rowTests}>
          <div className={style.nullButtonMobile} />
          <div className={style.nullButtonMobile} />
        </div>
      </div>
    </Page>
  );
}


export default TestsHome;

