import style from './Delete.module.css';

import Page from '@components/Page/Page';
import ContainerBox from '@components/ContainerBox/ContainerBox';
import Input from '@components/Input/Input';
import ButtonForm from '@components/Buttons/ButtonForm';
import { useState, useRef, useEffect } from 'react';
const fetchUrl = `${process.env.REACT_APP_BACKEND_URL}/deleteAccount`;

function Delete() {
  const [isShown, setIsSHown] = useState(false);

  const togglePassword = () => {
    setIsSHown((isShown) => !isShown);
  };

  const password = useRef(null);

  const [passwordValid, setPasswordValid] = useState(false);
  const [isFormValid, setIsFormValid] = useState(false);

  async function sendDeleteAccountRequest() {
    const body = {
      password: password.current.value,
    };

    console.log(body);
    const requestOptions = {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(body),
    };

    const response = await fetch(fetchUrl, requestOptions, { mode: 'cors' } );
    try {
      if (response.ok) {
        alert('Your account has been deleted :(');
        document.location.replace('/');
      } else {
        alert('You entered wrong password!');
      }
    } catch (error) {
      console.log(await response.clone().text());
    }
  }

  useEffect(() => {
    setIsFormValid(passwordValid);
  }, [
    passwordValid,
  ]);

  return (
    <Page title='Delete' contentClassName={style.contentOverride}>
      <section className={style.section}>
        <ContainerBox width={'60em'} className={style.containerBox}>
          <h1 className={style.title}>Enter your password</h1>
          <form method="post" action="#" className={style.form}>
            <Input
              useRef={password}
              correctValue={setPasswordValid}
              type={isShown ? 'text' : 'password'}
              name={'passwordLogin'}
              placeholder={'Password'}
            />
            <label className={style.checkboxLabel}>
              <input className={style.formElement} type="checkbox" checked={isShown} onChange={togglePassword}/>
              <em>Show password?</em>
            </label>
            <ButtonForm isActive={isFormValid} onClick={sendDeleteAccountRequest} className={style.formOptions} text={'delete'} width={''}/>
          </form>
        </ContainerBox>
      </section>
    </Page>
  );
}


export default Delete;

