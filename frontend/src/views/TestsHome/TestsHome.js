import style from './TestsHome.module.css';
import { Link } from 'react-router-dom';

// import sequence from '@components/TestButtons/sequenceTest.svg';
// import visual from '@components/TestButtons/visualTest.svg';
// import verbal from '@components/TestButtons/verbalTest.svg';
// import number from '@components/TestButtons/numberTest.svg';
import TestButton from '@components/TestButtons/TestButton';
import Page from '@components/Page/Page';


function TestsHome() {
  return (
    <Page title='Welcome'>
      <div className={style.rowTests}>
        <TestButton className={style.nullButton} div/>
        <Link className={style.testButton} >
          <TestButton text='squance memory' />
        </Link>
        <Link className={style.testButton} >
          <TestButton text='visual memory' />
        </Link>
        <TestButton className={style.nullButton} div/>
      </div>
      <div className={style.rowTests}>
        <TestButton className={style.nullButton} div/>
        <Link className={style.testButton} >
          <TestButton text='verbal memory' />
        </Link>
        <Link className={style.testButton} >
          <TestButton text='number memory' />
        </Link>
        <TestButton className={style.nullButton} div/>
      </div>
    </Page>
  );
}


export default TestsHome;
