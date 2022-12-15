import style from './Support.module.css';
import ContainerBox from '@components/ContainerBox/ContainerBox';
import Input from '@components/Input/Input';
import Textarea from '@components/Textarea/Textarea';
import Page from '@components/Page/Page';
import ButtonMedium from '@components/Buttons/ButtonMedium';
import { Link } from 'react-router-dom';
import * as React from 'react';
const validEmailRegex = RegExp(/^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/);

const fetchUrl = process.env.REACT_APP_BACKEND_URL+'/support';

function Support() {
  const email = React.useRef(null);
  const title = React.useRef(null);
  const message = React.useRef(null);
  const responseInfo = React.useRef(null);
  async function sendSupportRequest() {
    if (!validEmailRegex.test(email.current.value)||email.current.value.length > 64||title.current.value.length<3) {
      responseInfo.current.innerText = 'Invalid request';
      return;
    } else if (email.current.value.length > 64) {
      responseInfo.current.innerText = 'Invalid request';
      return;
    }
    const body = {
      email: email.current.value,
      messageTitle: title.current.value,
      message: message.current.children[0].value,
    };
    const requestOptions = {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(body),
    };

    const response = await fetch(fetchUrl, requestOptions);
    try {
      if (response.ok) {
        console.log(response);
        email.current.value = '';
        title.current.value = '';
        message.current.children[0].value = '';
        responseInfo.current.innerText = 'Message has been sent to support';
      } else {
        responseInfo.current.innerText = 'Invalid request';
      }
    } catch (error) {
      console.log(await response.clone().text());
    }
  };

  return (
    <Page className={style.fullPage} titlebar={false}>
      <ContainerBox className={style.container} width={'100%'}>
        <h1 className={style.title}>Support - contact us</h1>
        <form method="post" action="#" className={style.form}>
          <Input useRef={email} required={true} type={'email'} name={'email'} placeholder={'Email'} className={style.formElement} />
          <Input useRef={title} required={true} type={'text'} name={'title'} placeholder={'Title'} className={style.formElement} />
          <Textarea useRef={message} required={true} name={'textSupport'} placeholder={'Your message...'} className={style.textElement} />

          <label className={style.label}>
            <input type="checkbox" name="terms" value="terms" required={true} />
            <em>I agree to our <Link to={'/privacy'}>terms of service and privacy.</Link></em>
          </label>

          <div className={style.formOptions}>
            <ButtonMedium onClick={sendSupportRequest} text={'Send'} />
            <div className={style.responseInfo} ref={responseInfo}></div>
          </div>

        </form>
      </ContainerBox>
    </Page>
  );
}


export default Support;

