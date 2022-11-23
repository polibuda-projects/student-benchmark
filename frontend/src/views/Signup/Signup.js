import style from './Signup.module.css';

import Page from '@components/Page/Page';
import ContainerBox from '@components/ContainerBox/ContainerBox';
import Input from '@components/Input/Input';
import ButtonMedium from '@components/Buttons/ButtonMedium';
import logo from '@views/Home/logo.svg';


function Signup() {
  return (
    <Page title=''>
      <section className={style.section}>

        <ContainerBox width={'60em'}>
          <h1 className={style.title}>Sign up</h1>

          <form method="post" action="#" className={style.form}>
            <Input type={'text'} name={'usernameLog'} placeholder={'Username'} className={style.formElement}/>
            <Input type={'email'} name={'emailLog'} placeholder={'Address email'} className={style.formElement}/>
            <Input type={'password'} name={'passwordLog'} placeholder={'Password'} className={style.formElement}/>
            <Input type={'password'} name={'passwordRepeatLog'} placeholder={'Repeat your password'} className={style.formElement}/>
            <label className={style.label}>
              <input type="checkbox" name="terms" value="terms" required={true}/><em>I agree to our terms and privacy of sevice.</em>
            </label>

            <div className={style.formOptions}>
              <ButtonMedium text={'Sign up'} width={''}/>
              <a href={'/login'}>Log in</a>
            </div>
          </form>

        </ContainerBox>
        <img src={logo} className={style.logo} alt={'Student Benchmark'}/>

      </section>
    </Page>
  );
}


export default Signup;
