import style from './Signup.module.css';
import Page from '@components/Page/Page';
import ContainerBox from '@components/ContainerBox/ContainerBox';
import Input from '@components/Input/Input';
import logo from '@resources/img/logoVertical.svg';
import { Link, useNavigate } from 'react-router-dom';
import { useState, useRef, useEffect } from 'react';
import ButtonForm from '@components/Buttons/ButtonForm';
import InfoPopup from '@components/InfoPopup/InfoPopup';
const fetchUrl = `${process.env.REACT_APP_BACKEND_URL}/register`;

function Signup() {
  const [isShown, setIsSHown] = useState(false);
  const navigate = useNavigate();

  const togglePassword = () => {
    setIsSHown((isShown) => !isShown);
  };

  const nickname = useRef(null);
  const email = useRef(null);
  const password = useRef(null);
  const passwordConfirmation = useRef(null);

  const [usernameValid, setUsernameValid] = useState(false);
  const [emailValid, setEmailValid] = useState(false);
  const [passwordValid, setPasswordValid] = useState(false);
  const [passwordConfirmationValid, setPasswordConfirmationValid] = useState(false);
  const [tosChecked, setTosChecked] = useState(false);

  const [isFormValid, setIsFormValid] = useState(false);


  async function sendRegisterRequest() {
    const body = {
      nickname: nickname.current.value,
      email: email.current.value,
      password: password.current.value,
      passwordConfirmation: passwordConfirmation.current.value,
    };
    const requestOptions = {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(body),
    };

    try {
      const response = await fetch(fetchUrl, requestOptions);
      if (!response.ok) throw response;

      navigate('/login');
      InfoPopup.addMessage('Registration successful! Please log in to continue.');
    } catch (err) {
      if (err instanceof Response) {
        const message = await err.text();
        if (message === '5') InfoPopup.addMessage('Error: Unknown error');
        else if (err.headers.get('Content-Type')?.includes('text/plain')) {
          InfoPopup.addMessage(`Error: ${message}`);
        } else {
          InfoPopup.addMessage('Error: Connection error. Please try again later.');
        }
      }
    }
  };

  useEffect(() => {
    setIsFormValid(usernameValid && emailValid && passwordValid && passwordConfirmationValid && tosChecked);
  }, [
    usernameValid,
    emailValid,
    passwordValid,
    passwordConfirmationValid,
    tosChecked,
  ]);

  return (
    <Page titlebar={false}>
      <section className={style.section}>

        <ContainerBox width={'60em'}>
          <h1 className={style.title}>Sign up</h1>

          <form method="post" action="#" className={style.form} name={'signup'}>
            <Input
              useRef={nickname}
              correctValue={setUsernameValid}
              type={'text'}
              name={'usernameLog'}
              placeholder={'Username'}
              required
              className={style.formElement}
            />
            <Input
              useRef={email}
              correctValue={setEmailValid}
              type='email'
              name={'emailLog'}
              placeholder={'Address email'}
              required
              className={style.formElement}
            />
            <Input
              useRef={password}
              correctValue={setPasswordValid}
              // sibling={passwordConfirmation}
              type={isShown ? 'text' : 'password'}
              name={'passwordRegister'}
              placeholder={'Password'}
              required
              className={style.formElement}
            />
            <Input
              useRef={passwordConfirmation}
              correctValue={setPasswordConfirmationValid}
              // sibling={password}
              type={isShown ? 'text' : 'password'}
              name={'passwordRegisterRepeat'}
              placeholder={'Repeat your password'}
              required
              className={style.formElement}
            />
            <label className={style.checkboxLabel}>
              <input type="checkbox" checked={isShown} onChange={togglePassword}/>
              <em>Show password?</em>
            </label>
            <label className={style.label}>
              <input
                type="checkbox"
                name="terms"
                value="terms"
                required={true}
                onChange={({ target }) => setTosChecked(target.checked)}
              />
              <em>I agree to our <Link to={'/privacy'}>privacy and terms of service</Link>.</em>
            </label>
            <div className={style.formOptions}>
              <ButtonForm isActive={isFormValid} onClick={sendRegisterRequest} text={'Sign up'} width={''}/>
              <Link to='/login'>
                Log in
              </Link>
            </div>
          </form>

        </ContainerBox>
        <img src={logo} className={style.logo} alt={'Student Benchmark'}/>

      </section>
    </Page>
  );
}


export default Signup;
