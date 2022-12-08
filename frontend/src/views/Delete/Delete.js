import style from './Delete.module.css';

import Page from '@components/Page/Page';
import ContainerBox from '@components/ContainerBox/ContainerBox';
import Input from '@components/Input/Input';
import ButtonMedium from '@components/Buttons/ButtonMedium';


function Delete() {
  return (
    <Page title='Delete'>
      <section className={style.section}>
        <ContainerBox width={'60em'}>
          <h1 className={style.title}>Enter your password</h1>
          <form method="post" action="#" className={style.form}>
            <Input className={style.formElement} type={'password'} placeholder={'Password'} />
            <ButtonMedium className={style.formOptions} text={'delete'} width={''}/>
          </form>
        </ContainerBox>
      </section>
    </Page>
  );
}


export default Delete;
