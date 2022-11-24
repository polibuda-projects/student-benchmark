import style from './Login.module.css';

import Page from '@components/Page/Page';
import ContainerBox from '@components/ContainerBox/ContainerBox';
import Input from '@components/Input/Input';
import logo from '@views/Home/logo.svg';
import ButtonMedium from '@components/Buttons/ButtonMedium';


function Login() {
  return (
    <Page title=''>
      <section className={style.section}>

        <ContainerBox width={'60em'}>
          <h1 className={style.title}>Log in</h1>
          <form method="post" action="#" className={style.form}>
            <Input type={'email'} name={'emailLog'} placeholder={'Address email'} className={style.formElement}/>
            <Input type={'password'} name={'passwordLog'} placeholder={'Password'} className={style.formElement}/>

            <div className={style.formOptions}>
              <ButtonMedium text={'Login'} width={''}/>
              <div className={style.formOptionsLink}>
                <a href={'/signup'}>Sign up</a>
                <a href={'/password'}>Reset my password</a>
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
