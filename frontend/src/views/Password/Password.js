import style from './Password.module.css';

import Input from '@components/Input/Input';
import ButtonForm from '@components/Buttons/ButtonForm';
import Page from '@components/Page/Page';
import ContainerBox from '@components/ContainerBox/ContainerBox';
import { useState, useRef, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import InfoPopup from '@components/InfoPopup/InfoPopup';
const fetchUrl = `${process.env.REACT_APP_BACKEND_URL}/changeUserPassword`;

function Password() {
  const [isShown, setIsSHown] = useState(false);

  const navigate = useNavigate();

  const togglePassword = () => {
    setIsSHown((isShown) => !isShown);
  };

  const oldPassword = useRef(null);
  const newPassword = useRef(null);
  const newPasswordRepeated = useRef(null);

  const [currentPasswordValid, setCurrentPasswordValid] = useState(false);
  const [passwordValid, setPasswordValid] = useState(false);
  const [passwordConfirmationValid, setPasswordConfirmationValid] = useState(false);

  const [isFormValid, setIsFormValid] = useState(false);

  async function sendChangePasswordRequest() {
    const body = {
      oldPassword: oldPassword.current.value,
      newPassword: newPassword.current.value,
      newPasswordRepeated: newPasswordRepeated.current.value,
    };

    const requestOptions = {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(body),
    };

    const response = await fetch(fetchUrl, requestOptions, { mode: 'cors' });
    try {
      if (response.ok) {
        InfoPopup.addMessage('Password changed successfully!');
        navigate('/settings');
      } else {
        if (response.headers.get('Content-Type')?.includes('text/plain')) {
          InfoPopup.addMessage(`Error: ${await response.text()}`);
        } else {
          InfoPopup.addMessage('Error: Connection error. Please try again later.');
        }
      }
    } catch (error) {}
  }

  useEffect(() => {
    setIsFormValid(currentPasswordValid&& passwordValid && passwordConfirmationValid);
  }, [
    currentPasswordValid,
    passwordValid,
    passwordConfirmationValid,
  ]);

  return (
    <Page title="">
      <ContainerBox width={'60em'} className={style.passwordContainer}>
        <form className={style.changePasswordForm}>
          <h1 className={style.title}>Change Password</h1>
          <Input
            useRef={oldPassword}
            correctValue={setCurrentPasswordValid}
            type={isShown ? 'text' : 'password'}
            name={'passwordLogin'}
            placeholder={'Current Password'}
            required
            className={style.passwordInput}
          />
          <Input
            useRef={newPassword}
            // sibling={newPasswordRepeated}
            correctValue={setPasswordValid}
            type={isShown ? 'text' : 'password'}
            name={'passwordRegister'}
            placeholder={'New Password'}
            required
            className={style.passwordInput}
          />
          <Input
            useRef={newPasswordRepeated}
            // sibling={newPassword}
            correctValue={setPasswordConfirmationValid}
            type={isShown ? 'text' : 'password'}
            name={'passwordRegisterRepeat'}
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

export default Password;

