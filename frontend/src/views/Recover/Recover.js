import style from './Recover.module.css';

import Page from '@components/Page/Page';
import ContainerBox from '@components/ContainerBox/ContainerBox';
import Input from '@components/Input/Input';
import logo from '@resources/img/logoVertical.svg';
import ButtonMedium from '@components/Buttons/ButtonMedium';


function Recover() {
  return (
    <Page title='Recover'>
      <section className={style.section}>
        <ContainerBox width={'60em'}>
          <h1 className={style.title}>Enter your email</h1>
          <form method="post" action="#" className={style.form}>
            <Input className={style.formElement} type={'email'} name={'emailLog'} placeholder={'Address email'} />
            <ButtonMedium className={style.formOptions} text={'SEND'} width={''}/>
          </form>
        </ContainerBox>
        <img src={logo} className={style.logo} alt={'Student Benchmark'}/>
      </section>
    </Page>
  );
}


export default Recover;

