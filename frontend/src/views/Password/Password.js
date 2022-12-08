import style from './Password.module.css';

import Page from '@components/Page/Page';
import ContainerBox from '@components/ContainerBox/ContainerBox';

function Password() {
  return (
    <Page title="">
      <ContainerBox width={'100%'}>
        <form className={style.changePasswordForm}>
          <h1 className={style.title}>Change Password</h1>
          <input
            className={style.passwordInput}
            type="password"
            placeholder="Current Password"
          />
          <input
            className={style.passwordInput}
            type="password"
            placeholder="New Password"
          />
          <input
            className={style.passwordInput}
            type="password"
            placeholder="Repeat New Password"
          />
          <button className={style.changePasswordSubmit}>UPDATE</button>
        </form>
      </ContainerBox>
    </Page>
  );
}

export default Password;
