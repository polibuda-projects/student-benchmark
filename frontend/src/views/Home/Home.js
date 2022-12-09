import style from './Home.module.css';
import logo from '@resources/img/logoVertical.svg';

import ButtonMedium from '@components/Buttons/ButtonMedium';
import Page from '@components/Page/Page';
import { Link } from 'react-router-dom';
import TestsHome from '@views/Tests/Home/Home';

let visited = false;

function Home() {
  if (visited) {
    return <TestsHome />;
  }

  return (
    <Page title='Welcome'>
      <img src={logo} className={style.logo} alt='Student Benchmark' />

      <span className={style.catchphrase}>Challange your memory and cognitive skills.</span>

      <Link to='/tests'>
        <ButtonMedium className={style.getStartedButton} text='Get started' onClick={() => visited = true} />
      </Link>
    </Page>
  );
}


export default Home;
