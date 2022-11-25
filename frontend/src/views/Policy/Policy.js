/* eslint-disable max-len */
import style from './Policy.module.css';
import ContainerBox from '@components/ContainerBox/ContainerBox';

import Page from '@components/Page/Page';


function Policy() {
  return (
    <Page>
      <ContainerBox className={style.container}>
        <h1 className={style.title}>Privacy Policy</h1>
        <p className={style.text}>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris libero nisi, finibus in ex nec, sollicitudin tincidunt lacus. Curabitur scelerisque consequat euismod. Nunc aliquam ultrices porttitor. Fusce condimentum rhoncus magna. Phasellus lacinia eros ut augue luctus, id egestas velit viverra. Ut nunc felis, sodales quis placerat ut, aliquet et tellus. Nam ultrices mi vitae fermentum ultrices. Ut efficitur, sem at eleifend sagittis, odio urna ullamcorper dui, in vehicula eros nisl eu tortor. Nullam tincidunt metus nec mauris sodales, eu porta est bibendum. Fusce condimentum vel leo ac interdum. Nunc varius enim risus, mollis tincidunt augue pulvinar vel. Sed tristique sodales erat. </p>
        <p className={style.text}>Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Suspendisse sapien sapien, venenatis at hendrerit ac, sollicitudin vel nisl. Nulla ut nibh sit amet mi efficitur aliquet iaculis non mi. Sed dapibus commodo eleifend. Donec et ipsum tempus, vestibulum massa sit amet, vestibulum est. Nam ullamcorper ante sit amet laoreet tempor. Sed tincidunt ipsum massa, eu malesuada sapien pulvinar in. Donec ullamcorper augue sit amet dapibus commodo. Cras tincidunt vehicula magna, id fermentum ligula ultricies vel. Nulla ullamcorper mi placerat dapibus eleifend. Nullam sit amet sodales libero. Sed posuere tincidunt ullamcorper. Nulla varius neque nec augue ullamcorper efficitur. Quisque ut dui vitae lectus rutrum pellentesque ac vitae nunc. Donec sit amet quam ut erat porta cursus quis sit amet nunc. </p>
        <h2 className={style.subtitle}>Information Collection and Use</h2>
        <p className={style.text}>Suspendisse vel rutrum velit. Proin id sagittis sapien. Sed elementum lorem quis venenatis aliquam. Donec ullamcorper euismod nulla in tincidunt. Nam malesuada luctus tellus, ut ultricies nisl hendrerit ac. Quisque at sem pretium, semper metus non, varius turpis. Sed eget augue suscipit, pretium tellus id, lacinia enim.</p>
        <h2 className={style.subtitle}>Cookies</h2>
        <p className={style.text}>Praesent ornare lacus vel sem aliquet pharetra. Quisque rutrum dui arcu, at faucibus nisi tristique a. Fusce ut lacinia risus, elementum aliquam urna. Quisque ut nulla volutpat, porta urna sed, consectetur diam. Integer massa arcu, congue ut urna vel, pellentesque tempus lorem. Pellentesque ac eleifend ante. Nunc eros arcu, congue eget ante a, mollis tempor est. Nam tincidunt vel nisl vel vehicula.</p>
      </ContainerBox>
    </Page>
  );
}


export default Policy;
