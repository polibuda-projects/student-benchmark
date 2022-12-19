import style from './Password.module.css';

import Input from '@components/Input/Input';
import ButtonForm from '@components/Buttons/ButtonForm';
import Page from '@components/Page/Page';
import ContainerBox from '@components/ContainerBox/ContainerBox';
import { useState, useRef, useEffect } from 'react';
const fetchUrl = `${process.env.REACT_APP_BACKEND_URL}/resetPassword?token=${window.location.search.slice(7)}`;

function ResetPassword() {
  const [isShown, setIsSHown] = useState(false);

  const togglePassword = () => {
    setIsSHown((isShown) => !isShown);
  };

  const newPassword = useRef(null);
  const newPasswordRepeated = useRef(null);

  const [passwordValid, setPasswordValid] = useState(false);
  const [passwordConfirmationValid, setPasswordConfirmationValid] = useState(false);


  const [isFormValid, setIsFormValid] = useState(false);

  async function sendChangePasswordRequest() {
    const body = {
      newPassword: newPassword.current.value,
      newPasswordRepeated: newPasswordRepeated.current.value,
    };

    console.log(body);
    const requestOptions = {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(body),
    };

    const response = await fetch(fetchUrl, requestOptions, { mode: 'cors' });
    try {
      if (response.ok) {
        document.location.replace('/dashboard');
        alert('Password changed successfully!');
      } else {
        alert('Invalid request!');
      }
    } catch (error) {
      console.log(await response.clone().text());
    }
  }

  useEffect(() => {
    setIsFormValid(passwordValid && passwordConfirmationValid);
  }, [
    passwordValid,
    passwordConfirmationValid,
  ]);

  return (
    <Page title="">
      <ContainerBox width={'60em'} className={style.passwordContainer}>
        <form className={style.changePasswordForm}>
          <h1 className={style.title}>Set new Password</h1>
          <Input
            useRef={newPassword}
            correctValue={setPasswordValid}
            type={isShown ? 'text' : 'password'}
            name={'newPassword'}
            placeholder={'New Password'}
            required
            className={style.passwordInput}
          />
          <Input
            useRef={newPasswordRepeated}
            correctValue={setPasswordConfirmationValid}
            type={isShown ? 'text' : 'password'}
            name={'newPasswordRepeated'}
            placeholder={'Repeat New Password'}
            required
            className={style.passwordInput}
          />
          <label className={style.checkboxLabel}>
            <input
              type="checkbox"
              checked={isShown}
              onChange={togglePassword}
            />
            <em>Show password?</em>
          </label>
          <ButtonForm
            isActive={isFormValid}
            onClick={sendChangePasswordRequest}
            className={style.changePasswordSubmit}
            text={'UPDATE'}
            width={''}
          />
        </form>
      </ContainerBox>
    </Page>
  );
}

export default ResetPassword;

