import style from './Home.module.css';
import logo from './logo.svg';

import ButtonMedium from '@components/Buttons/ButtonMedium';
import Page from '@components/Page/Page';
import { Link } from 'react-router-dom';


function Home() {
  return (
    <Page title='Welcome'>
      <img src={logo} className={style.logo} alt='Student Benchmark' />

      <span className={style.catchphrase}>Challange your memory and cognitive skills.</span>

      <Link to='/tests'>
        <ButtonMedium className={style.getStartedButton} text='Get started' />
      </Link>
    </Page>
  );
}


export default Home;
