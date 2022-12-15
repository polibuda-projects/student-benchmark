import style from './Recover.module.css';

import Page from '@components/Page/Page';
import ContainerBox from '@components/ContainerBox/ContainerBox';
import Input from '@components/Input/Input';
import logo from '@resources/img/logoVertical.svg';
import ButtonMedium from '@components/Buttons/ButtonMedium';

import { useRef, useState } from 'react';
const fetchUrl = `${process.env.REACT_APP_BACKEND_URL}/passwordRecovery`;

function Recover() {
  const [resetMessage, setResetMessage] = useState('');
  const emailToWhichSendReset = useRef(null);

  async function sendResetRequest() {
    const body = {
      email: emailToWhichSendReset.current.value,
    };
    console.log(body);

    const requestOptions = {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(body),
    };

    try {
      const response = await fetch(fetchUrl, requestOptions);
      try {
        console.log(await response.clone().json());
      } catch (error) {
        setResetMessage(await response.clone().text());
      }
    } catch (err) {
      setResetMessage('Something went wrong - try again');
      console.log('Reset password error - ' + err.message);
    }
  }

  return (
    <Page title='Recover'>
      <section className={style.section}>
        <ContainerBox width={'60em'}>
          <h1 className={style.title}>Enter your email</h1>
          <form method="post" action="#" className={style.form}>
            <Input useRef={emailToWhichSendReset} className={style.formElement} type={'email'} name={'emailLog'} placeholder={'Address email'}/>
            <ButtonMedium onClick={sendResetRequest} className={style.formOptions} text={'SEND'} width={''}/>
          </form>
          <div className={style.resetMessage}>{resetMessage}</div>
        </ContainerBox>
        <img src={logo} className={style.logo} alt={'Student Benchmark'}/>
      </section>
    </Page>
  );
}


export default Recover;

