import style from './Login.module.css';

import Page from '@components/Page/Page';
import ContainerBox from '@components/ContainerBox/ContainerBox';
import Input from '@components/Input/Input';
import logo from '@resources/img/logoVertical.svg';
import ButtonMedium from '@components/Buttons/ButtonMedium';
import { Link } from 'react-router-dom';
import { useState, useRef } from 'react';
const fetchUrl = `${process.env.REACT_APP_BACKEND_URL}/login`;

function Login() {
  const [isShown, setIsSHown] = useState(false);

  const togglePassword = () => {
    setIsSHown((isShown) => !isShown);
  };

  const nickname = useRef(null);
  const password = useRef(null);

  async function sendRegisterRequest() {
    const body = new FormData();
    body.append('username', nickname.current.value);
    body.append('password', password.current.value);

    const requestOptions = {
      method: 'POST',
      body: body,
    };


    try {
      await fetch(fetchUrl, requestOptions, { mode: 'cors' });
      document.location.replace('/');
    } catch (error) {
      console.log('Login error');
    }
  };

  return (
    <Page titlebar={false}>
      <section className={style.section}>

        <ContainerBox width={'60em'}>
          <h1 className={style.title}>Log in</h1>
          <form method="post" action="#" className={style.form}>
            <Input useRef={nickname} type={'text'} name={'usernameLog'} placeholder={'Username'} required className={style.formElement}/>
            <Input useRef={password} type={isShown ? 'text' : 'password'} name={'passwordLogin'} placeholder={'Password'} required className={style.formElement}/>
            <label className={style.checkboxLabel}>
              <input type="checkbox" checked={isShown} onChange={togglePassword}/>
              <em>Show password?</em>
            </label>

            <div className={style.formOptions}>
              <ButtonMedium onClick={sendRegisterRequest} text={'Login'} width={''}/>
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

