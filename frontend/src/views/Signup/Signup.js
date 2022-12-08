import style from './Signup.module.css';
import * as React from 'react';
import Page from '@components/Page/Page';
import ContainerBox from '@components/ContainerBox/ContainerBox';
import Input from '@components/Input/Input';
import ButtonMedium from '@components/Buttons/ButtonMedium';
import logo from '@views/Home/logo.svg';
import { Link } from 'react-router-dom';
const fetchUrl = 'http://localhost:8080/register';

function Signup() {
  const nickname = React.useRef(null);
  const email = React.useRef(null);
  const password = React.useRef(null);
  const passwordConfirmation = React.useRef(null);
  function sendAjax() {
    console.log('leci post');
    const body = {
      nickname: nickname.current.value,
      email: email.current.value,
      password: password.current.value,
      passwordConfirmation: passwordConfirmation.current.value,
    };
    console.log(body);
    const requestOptions = {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(body),
    };
    fetch(fetchUrl, requestOptions)
        .then((response) => console.log(response))
        .then((data) => console.log(data));
  };
  return (
    <Page titlebar={false}>
      <section className={style.section}>

        <ContainerBox width={'60em'}>
          <h1 className={style.title}>Sign up</h1>

          <form className={style.form} name={'signup'}>
            <Input type={'text'} useRef={nickname} name={'nickname'} placeholder={'Username'} required className={style.formElement}/>
            <Input type={'email'} useRef={email} name={'email'} placeholder={'Address email'} required className={style.formElement}/>
            <Input type={'password'} useRef={password} name={'password'} placeholder={'Password'} required className={style.formElement}/>
            <Input type={'password'} useRef={passwordConfirmation}
              name={'passwordConfirmation'} placeholder={'Repeat your password'} required className={style.formElement}/>
            <label className={style.label}>
              <input type="checkbox" name="terms"
                value="terms" required={true}/><em>I agree to our <Link to={'/privacy'}>privacy and terms of service</Link>.</em>
            </label>
            <div className={style.formOptions}>
              <ButtonMedium onClick={sendAjax} text={'Sign up'} width={''}/>
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
