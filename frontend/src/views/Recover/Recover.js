import style from './Recover.module.css';

import Page from '@components/Page/Page';
import ContainerBox from '@components/ContainerBox/ContainerBox';
import Input from '@components/Input/Input';
import logo from '@resources/img/logoVertical.svg';
import ButtonForm from '@components/Buttons/ButtonForm';
import { useRef, useState, useEffect } from 'react';

const fetchUrl = `${process.env.REACT_APP_BACKEND_URL}/passwordRecovery`;

function Recover() {
  const emailToWhichSendReset = useRef(null);

  const [validEmail, setValidEmail] = useState(false);
  const [resetMessage, setResetMessage] = useState('');

  const [isFormValid, setIsFormValid] = useState(false);

  async function sendResetRequest() {
    if (!validEmail) {
      return;
    }

    const body = {
      email: emailToWhichSendReset.current.value,
    };

    const requestOptions = {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(body),
    };

    try {
      const response = await fetch(fetchUrl, requestOptions);
      // Information about a non-existent account is not provided - for security reasons.
      setResetMessage(`If a matching account was found an email was sent to ${body['email']} 
        to allow you to reset your password. 
        If you still don't see the email after a few minutes, please check your Spam folder.`);

      // if (response.status === /*40number - documentation please*/) {
      //   // The reset link message was not sent due to, for example, a server network connection issue.
      //   setResetMessage('Something went wrong - try again later');
      // }
    } catch (err) {
      // Request cannot be made or a response cannot be retrieved - probably network connection issue.
      setResetMessage('Something went wrong - try again');
    }
  }

  useEffect(() => {
    setIsFormValid(validEmail);
  }, [
    validEmail,
  ]);

  return (
    <Page title='Recover'>
      <section className={style.section}>
        <ContainerBox width={'60em'}>
          <h1 className={style.title}>Enter your email</h1>
          <form method="post" action="#" className={style.form}>
            <Input
              useRef={emailToWhichSendReset}
              correctValue={setValidEmail}
              className={style.formElement}
              type={'email'} name={'emailLog'}
              placeholder={'Address email'}
            />
            <ButtonForm isActive={isFormValid} onClick={sendResetRequest} className={style.formOptions} text={'SEND'} width={''}/>
            <div className={style.resetMessage}>{resetMessage}</div>
          </form>
        </ContainerBox>
        <img src={logo} className={style.logo} alt={'Student Benchmark'}/>
      </section>
    </Page>
  );
}


export default Recover;

