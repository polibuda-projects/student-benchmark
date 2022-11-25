import style from './Login.module.css';

import Page from '@components/Page/Page';
import ContainerBox from '@components/ContainerBox/ContainerBox';
import Input from '@components/Input/Input';
import logo from '@views/Home/logo.svg';
import ButtonMedium from '@components/Buttons/ButtonMedium';
import { Link } from 'react-router-dom';


function Login() {
  return (
    <Page title=''>
      <section className={style.section}>

        <ContainerBox width={'60em'}>
          <h1 className={style.title}>Log in</h1>
          <form method="post" action="#" className={style.form}>
            <Input type={'email'} name={'emailLog'} placeholder={'Address email'} required className={style.formElement}/>
            <Input type={'password'} name={'passwordLog'} placeholder={'Password'} required className={style.formElement}/>

            <div className={style.formOptions}>
              <ButtonMedium text={'Login'} width={''}/>
              <div className={style.formOptionsLink}>
                <Link to='/signup'>Sign up</Link>
                <Link to='/recover'>Reset my password</Link>
              </div>
            </div>
          </form>
        </ContainerBox>
        <img src={logo} className={style.logo} alt={'Student Benchmark'}/>

      </section>
    </Page>
  );
}


export default Login;
