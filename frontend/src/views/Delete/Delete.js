import style from './Delete.module.css';

import Page from '@components/Page/Page';
import ContainerBox from '@components/ContainerBox/ContainerBox';
import Input from '@components/Input/Input';
import ButtonForm from '@components/Buttons/ButtonForm';
import { useState, useRef, useEffect } from 'react';
import InfoPopup from '@components/InfoPopup/InfoPopup';
import { logout } from '../../auth';
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

    const requestOptions = {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(body),
    };

    const response = await fetch(fetchUrl, requestOptions, { mode: 'cors' });
    if (response.ok) {
      InfoPopup.addMessage('Your account has been deleted. Redirecting to home page...');
      setTimeout(() => {
        logout();
      }, 5000);
    } else if (response.status === 400) {
      try {
        await response.clone().json(); // don't show verbose error message if it's a validation error
        InfoPopup.addMessage('Error: Incorrect user password');
      } catch (error) {
        if (response.headers.get('Content-Type')?.includes('text/plain')) {
          InfoPopup.addMessage(`Error: ${await response.text()}`);
        } else {
          InfoPopup.addMessage('Error: Connection error. Please try again later.');
        }
      }
    } else {
      InfoPopup.addMessage('Error: Unknown error');
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

