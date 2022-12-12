import style from './Password.module.css';

import Input from '@components/Input/Input';
import ButtonMedium from '@components/Buttons/ButtonMedium';
import Page from '@components/Page/Page';
import ContainerBox from '@components/ContainerBox/ContainerBox';
import { useState } from 'react';

function Password() {
  const [isShown, setIsSHown] = useState(false);

  const togglePassword = () => {
    setIsSHown((isShown) => !isShown);
  };
  return (
    <Page title="">
      <ContainerBox width={'60em'} className={style.passwordContainer}>
        <form className={style.changePasswordForm}>
          <h1 className={style.title}>Change Password</h1>
          <Input
            type={isShown ? 'text' : 'password'}
            name={'passwordLogin'}
            placeholder={'Current Password'}
            required
            className={style.passwordInput}
          />
          <Input
            type={isShown ? 'text' : 'password'}
            name={'passwordRegister'}
            placeholder={'New Password'}
            required
            className={style.passwordInput}
          />
          <Input
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
          <ButtonMedium
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

