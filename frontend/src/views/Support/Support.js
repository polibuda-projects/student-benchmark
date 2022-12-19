import style from './Support.module.css';
import ContainerBox from '@components/ContainerBox/ContainerBox';
import Input from '@components/Input/Input';
import Textarea from '@components/Textarea/Textarea';
import Page from '@components/Page/Page';
import ButtonForm from '@components/Buttons/ButtonForm';
import { Link } from 'react-router-dom';
import { useState, useRef, useEffect } from 'react';
const fetchUrl = `${process.env.REACT_APP_BACKEND_URL}/support`;

function Support() {
  const title = useRef(null);
  const message = useRef(null);
  const responseInfo = useRef(null);

  const [titleValid, setTitleValid] = useState(false);
  const [messageValid, setMessageValid] = useState(false);
  const [tosChecked, setTosChecked] = useState(false);

  const [isFormValid, setIsFormValid] = useState(false);

  async function sendSupportRequest() {
    const body = {
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

  useEffect(() => {
    setIsFormValid(titleValid && messageValid && tosChecked);
  }, [
    titleValid,
    messageValid,
    tosChecked,
  ]);

  return (
    <Page className={style.fullPage} titlebar={false}>
      <ContainerBox className={style.container} width={'100%'}>
        <h1 className={style.title}>Support - contact us</h1>
        <form method="post" action="#" className={style.form}>
          <Input
            useRef={title}
            correctValue={setTitleValid}
            type={'text'}
            name={'title'}
            placeholder={'Title'}
            required
            className={style.formElement}
          />
          <Textarea
            useRef={message}
            correctValue={setMessageValid}
            name={'textSupport'}
            placeholder={'Your message...'}
            required
            className={style.textElement}
          />
          <label className={style.label}>
            <input
              type="checkbox"
              name="terms"
              value="terms"
              required={true}
              onChange={({ target }) => setTosChecked(target.checked)}
            />
            <em>I agree to our <Link to={'/privacy'}>privacy and terms of service</Link></em>
          </label>
          <div className={style.formOptions}>
            <ButtonForm isActive={isFormValid} onClick={sendSupportRequest} text={'Send'} width={''}/>
            <div className={style.responseInfo} ref={responseInfo}></div>
          </div>
        </form>
      </ContainerBox>
    </Page>
  );
}


export default Support;

