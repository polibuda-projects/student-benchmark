import style from './Delete.module.css';

import Page from '@components/Page/Page';
import ContainerBox from '@components/ContainerBox/ContainerBox';
import Input from '@components/Input/Input';
import ButtonMedium from '@components/Buttons/ButtonMedium';
import { useState } from 'react';

function Delete() {
  const [isShown, setIsSHown] = useState(false);

  const togglePassword = () => {
    setIsSHown((isShown) => !isShown);
  };

  return (
    <Page title='Delete'>
      <section className={style.section}>
        <ContainerBox width={'60em'}>
          <h1 className={style.title}>Enter your password</h1>
          <form method="post" action="#" className={style.form}>
            <Input type={isShown ? 'text' : 'password'} name={'passwordLogin'} placeholder={'Password'} />
            <label className={style.checkboxLabel}>
              <input type="checkbox" checked={isShown} onChange={togglePassword}/>
              <em>Show password?</em>
            </label>
            <ButtonMedium className={style.formOptions} text={'delete'} width={''}/>
          </form>
        </ContainerBox>
      </section>
    </Page>
  );
}


export default Delete;
