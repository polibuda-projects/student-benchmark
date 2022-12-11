import style from './Support.module.css';
import ContainerBox from '@components/ContainerBox/ContainerBox';
import Input from '@components/Input/Input';
import Textarea from '@components/Textarea/Textarea';
import Page from '@components/Page/Page';
import ButtonMedium from '@components/Buttons/ButtonMedium';
import { Link } from 'react-router-dom';

function Support() {
  return (
    <Page className={style.fullPage} titlebar={false}>
      <ContainerBox className={style.container} width={'100%'}>
        <h1 className={style.title}>Support - contact us</h1>
        <form method="post" action="#" className={style.form}>
          <Input required={true} type={'email'} name={'emailSupport'} placeholder={'Email'} className={style.formElement} />
          <Input required={true} type={'text'} name={'titleSupport'} placeholder={'Title'} className={style.formElement} />
          <Textarea required={true} name={'textSupport'} placeholder={'Your message...'} className={style.textElement} />

          <label className={style.label}>
            <input type="checkbox" name="terms" value="terms" required={true} />
            <em>I agree to our <Link to={'/privacy'}>terms of service and privacy.</Link></em>
          </label>

          <div className={style.formOptions}>
            <ButtonMedium text={'Send'} />
          </div>

        </form>
      </ContainerBox>
    </Page>
  );
}


export default Support;
