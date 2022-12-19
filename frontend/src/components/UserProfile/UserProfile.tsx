import { Component, useRef } from 'react';

import style from './UserProfile.module.css';
import defaultAvatar from '@resources/img/defaultAvatar.svg';
import downArrow from '@resources/img/downArrow.svg';
import { Link } from 'react-router-dom';
import { getUserstate, logout } from '../../auth';


export interface UserProfileProps {
  nodeRef: React.RefObject<HTMLDivElement>;
}

export interface UserProfileState {
  active: boolean;
}


export class UserProfileComponent extends Component<UserProfileProps, UserProfileState> {
  constructor(params: UserProfileProps) {
    super(params);

    this.state = {
      active: false,
    };
  }

  public render() {
    const userState = getUserstate();

    if (userState === null) return <div style={{ height: '5rem' }}></div>;

    return (
      <div className={style.container}>
        <div className={style.profile} onClick={this.toggleDropdown}>
          <img src={defaultAvatar} className={style.avatar} alt='?'/>
          <span className={style.username}>{userState?.username ?? 'Guest'}</span>
          <img src={downArrow} className={this.burgerClasses} alt='more'/>
        </div>

        <div className={this.dropdownClasses}>
          <Link className={style.dropdownButton} to='/dashboard'>Dashboard</Link>
          <Link className={style.dropdownButton} to='/settings'>Settings</Link>
          <div style={{ cursor: 'pointer' }} className={style.dropdownButton} onClick={logout}>Logout</div>
        </div>
      </div>
    );
  }

  private toggleDropdown = () => {
    this.setState({ active: !this.state.active });
  };

  private joinClasses(...classes: string[]) {
    return classes.join(' ');
  }

  private get dropdownClasses() {
    return this.joinClasses(style.dropdown, this.state.active ? style.dropdownActive:'');
  }

  private get burgerClasses() {
    return this.joinClasses(style.burger, this.state.active ? style.burgerActive:'');
  }
}

export default function UserProfile(props: Omit<UserProfileProps, 'nodeRef'>) {
  const nodeRef = useRef<HTMLDivElement>(null);

  return (
    <UserProfileComponent nodeRef={nodeRef} {...props} />
  );
};

