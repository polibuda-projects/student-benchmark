import style from './Login.module.css';

import Page from '@components/Page/Page';
import ContainerBox from '@components/ContainerBox/ContainerBox';
import Input from '@components/Input/Input';
import logo from '@resources/img/logoVertical.svg';
import ButtonMedium from '@components/Buttons/ButtonMedium';
import { Link, useNavigate } from 'react-router-dom';
import { useState, useRef } from 'react';
import { login as apiLogin } from '../../auth';

function Login() {
  const [isShown, setIsSHown] = useState(false);
  const [loginMessage, setLoginMessage] = useState('');

  const togglePassword = () => {
    setIsSHown((isShown) => !isShown);
  };

  const nickname = useRef(null);
  const password = useRef(null);
  const navigate = useNavigate();

  async function sendRegisterRequest() {
    const resp = await apiLogin(nickname.current.value, password.current.value, navigate);
    setLoginMessage(resp);
  };

  return (
    <Page titlebar={false}>
      <section className={style.section}>

        <ContainerBox width={'60em'}>
          <h1 className={style.title}>Log in</h1>
          <form method="post" action="#" className={style.form}>
            <Input useRef={nickname} type={'text'} name={'usernameLog'} placeholder={'Username or Email'} required className={style.formElement}/>
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
          <p>{loginMessage}</p>
        </ContainerBox>
        <img src={logo} className={style.logo} alt={'Student Benchmark'}/>

      </section>
    </Page>
  );
}


export default Login;

