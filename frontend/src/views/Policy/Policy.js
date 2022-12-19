/* eslint-disable max-len */
import style from './Policy.module.css';
import ContainerBox from '@components/ContainerBox/ContainerBox';
import { Link } from 'react-router-dom';

import Page from '@components/Page/Page';


function Policy() {
  return (
    <Page title='Privacy Policy'>
      <ContainerBox className={style.container}>
        <p className={style.text}>Student Benchmark operates the <Link to={'/'}>https://studentbenchmark.pl</Link> website, which provides the SERVICE.</p>
        <p className={style.text}>This page is used to inform website visitors regarding our policies with the collection, use, and disclosure of Personal Information if anyone decided to use our Service, the Student Benchmark website. </p>
        <p className={style.text}>If you choose to use our Service, then you agree to the collection and use of information in relation with this policy. The Personal Information that we collect are used for providing and improving the Service. We will not use or share your information with anyone.</p>
        <p className={style.text}>We do not pass any data on to third parties. The service functions only for educational purposes.</p>
        <h2 className={style.subtitle}>Log Data</h2>
        <p className={style.text}>We want to inform you that whenever you visit our Service, we collect information that your browser sends to us that is called Log Data. This Log Data may include information such as your computer’s Internet Protocol ("IP") address, browser version, pages of our Service that you visit, the time and date of your visit, the time spent on those pages, and other statistics.</p>
        <h2 className={style.subtitle}>Cookies</h2>
        <p className={style.text}>Cookies are files with small amount of data that is commonly used an anonymous unique identifier. These are sent to your browser from the website that you visit and are stored on your computer’s hard drive.</p>
        <p className={style.text}>Our website uses these "cookies" to collection information and to improve our Service. You have the option to either accept or refuse these cookies, and know when a cookie is being sent to your computer. If you choose to refuse our cookies, you may not be able to use some portions of our Service.</p>
        <h2 className={style.subtitle}>Security</h2>
        <p className={style.text}>We value your trust in providing us your Personal Information, thus we are striving to use commercially acceptable means of protecting it. But remember that no method of transmission over the internet, or method of electronic storage is 100% secure and reliable, and we cannot guarantee its absolute security.</p>
        <h2 className={style.subtitle}>Children's Privacy</h2>
        <p className={style.text}>Our Services do not address anyone under the age of 13. We do not knowingly collect personal identifiable information from children under 13. In the case we discover that a child under 13 has provided us with personal information, we immediately delete this from our servers. If you are a parent or guardian and you are aware that your child has provided us with personal information, please contact us so that we will be able to do necessary actions.</p>
        <h2 className={style.subtitle}>Changes to This Privacy Policy</h2>
        <p className={style.text}>We may update our Privacy Policy from time to time. Thus, we advise you to review this page periodically for any changes. We will notify you of any changes by posting the new Privacy Policy on this page. These changes are effective immediately, after they are posted on this page.</p>
        <h2 className={style.subtitle}>Contact Us</h2>
        <p className={style.text}>If you have any questions or suggestions about our Privacy Policy, do not hesitate to <Link to={'/support'}>contact us.</Link></p>
      </ContainerBox>
    </Page>
  );
}


export default Policy;

