import style from './Signup.module.css';

import Page from '@components/Page/Page';
import ContainerBox from '@components/ContainerBox/ContainerBox';
import Input from '@components/Input/Input';
import ButtonMedium from '@components/Buttons/ButtonMedium';
import logo from '@resources/img/logoVertical.svg';
import { Link } from 'react-router-dom';
import { useState } from 'react';

function Signup() {
  const [isShown, setIsSHown] = useState(false);

  const togglePassword = () => {
    setIsSHown((isShown) => !isShown);
  };

  return (
    <Page titlebar={false}>
      <section className={style.section}>

        <ContainerBox width={'60em'}>
          <h1 className={style.title}>Sign up</h1>

          <form method="post" action="#" className={style.form} name={'signup'}>
            <Input type={'text'} name={'usernameLog'} placeholder={'Username'} required className={style.formElement}/>
            <Input type={'email'} name={'emailLog'} placeholder={'Address email'} required className={style.formElement}/>
            <Input type={isShown ? 'text' : 'password'} name={'passwordRegister'} placeholder={'Password'} required className={style.formElement}/>
            <Input type={isShown ? 'text' : 'password'} name={'passwordRegisterRepeat'}
              placeholder={'Repeat your password'} required className={style.formElement}/>
            <label className={style.checkboxLabel}>
              <input type="checkbox" checked={isShown} onChange={togglePassword}/>
              <em>Show password?</em>
            </label>
            <label className={style.label}>
              <input type="checkbox" name="terms" value="terms" required={true}/>
              <em>I agree to our <Link to={'/privacy'}>privacy and terms of service</Link>.</em>
            </label>

            <div className={style.formOptions}>
              <ButtonMedium text={'Sign up'} width={''}/>
              <Link to='/login'>
                Log in
              </Link>
            </div>
          </form>

        </ContainerBox>
        <img src={logo} className={style.logo} alt={'Student Benchmark'}/>

      </section>
    </Page>
  );
}


export default Signup;
