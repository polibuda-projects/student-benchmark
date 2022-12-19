import style from './Login.module.css';

import Page from '@components/Page/Page';
import ContainerBox from '@components/ContainerBox/ContainerBox';
import Input from '@components/Input/Input';
import logo from '@resources/img/logoVertical.svg';
import ButtonForm from '@components/Buttons/ButtonForm';
import { Link, useNavigate } from 'react-router-dom';
import { useState, useRef, useEffect } from 'react';
import { login as apiLogin } from '../../auth';
import InfoPopup from '@components/InfoPopup/InfoPopup';

function Login() {
  const [isShown, setIsSHown] = useState(false);

  const togglePassword = () => {
    setIsSHown((isShown) => !isShown);
  };

  const nickname = useRef(null);
  const password = useRef(null);
  const navigate = useNavigate();

  const [usernameValid, setUsernameValid] = useState(false);
  const [passwordValid, setPasswordValid] = useState(false);

  const [isFormValid, setIsFormValid] = useState(false);

  async function sendRegisterRequest() {
    const resp = await apiLogin(nickname.current.value, password.current.value, navigate);
    if (resp !== undefined) InfoPopup.addMessage(`Error: ${resp}`);
  };

  useEffect(() => {
    setIsFormValid(usernameValid && passwordValid);
  }, [
    usernameValid,
    passwordValid,
  ]);

  return (
    <Page titlebar={false}>
      <section className={style.section}>

        <ContainerBox width={'60em'}>
          <h1 className={style.title}>Log in</h1>
          <form method="post" action="#" className={style.form}>
            <Input
              useRef={nickname}
              correctValue={setUsernameValid}
              type={'text'}
              name={'usernameLog'}
              placeholder={'Username or Email'}
              required
              className={style.formElement}
            />
            <Input
              useRef={password}
              correctValue={setPasswordValid}
              type={isShown ? 'text' : 'password'}
              name={'passwordLogin'} placeholder={'Password'}
              required
              className={style.formElement}
            />
            <label className={style.checkboxLabel}>
              <input type="checkbox" checked={isShown} onChange={togglePassword}/>
              <em>Show password?</em>
            </label>

            <div className={style.formOptions}>
              <ButtonForm isActive={isFormValid} onClick={sendRegisterRequest} text={'Login'} width={''}/>
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

