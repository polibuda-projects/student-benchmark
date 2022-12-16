import style from './User.module.css';
import Page from '@components/Page/Page';
import ButtonMedium from '@components/Buttons/ButtonMedium';
import ButtonGrey from '@components/Buttons/ButtonGrey';
import { Link } from 'react-router-dom';
import userAvatar from '@resources/img/defaultAvatar.svg';
import { getUserstate, logout } from '../../auth';


function User() {
  const userState = getUserstate();

  return (
    <Page background={true} titlebar={false} user={false}>
      <img src={userAvatar} className={style.userAvatar} alt={'Default Avatar'} />
      <div className={style.userBar}>
        <span className={style.username}>{userState?.username}</span>
      </div>
      <div className={style.buttons}>
        <Link to='/dashboard'>
          <ButtonGrey className={style.buttonGrey} text={'Dashboard'}/>
        </Link>
        <Link to='/password'>
          <ButtonGrey className={style.buttonGrey} text={'Change Password'}/>
        </Link>
        <Link to='/delete'>
          <ButtonGrey className={style.buttonGrey} text={'Delete Account'}/>
        </Link>
      </div>
      <ButtonMedium className={style.buttonMedium} onClick={logout} text={'Log Out'}/>
    </Page>
  );
}


export default User;

