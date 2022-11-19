import style from './Home.module.css';
import logo from './logo.svg';

import ButtonMedium from '@components/Buttons/ButtonMedium';
import Page from '@components/Page/Page';


function Home() {
  return (
    <Page title='Welcome'>
      <img src={logo} className={style.logo} alt='Student Benchmark' />

      <span className={style.catchphrase}>Lorem ipsum, one liners are hard</span>

      <ButtonMedium className={style.getStartedButton} text='Get started' />
    </Page>
  );
}


export default Home;
