import style from './Sidebar.module.css';
import logo from './logo.svg';

import homeIcon from './homeIcon.svg';
import dashboardIcon from './dashboardIcon.svg';
import supportIcon from './supportIcon.svg';
import donateBoxIcon from './donateBoxIcon.svg';

import MediumButton from '@components/Buttons/ButtonMedium';
import { Link, NavigateFunction, NavLink, useNavigate } from 'react-router-dom';
import { Component, HTMLAttributes } from 'react';


export interface SidebarProps {
  navigate: NavigateFunction;
}

export class SidebarComponent extends Component<SidebarProps> {
  render() {
    return (
      <div className={style.sidebar}>
        <Link className={style.logo} to='/'>
          <img src={logo} alt='Student Benchmark' />
        </Link>

        <div className={style.menu}>
          <NavLink className={style.menuButton} to='/'>
            <div style={SidebarComponent.menuButtonStyleGenerator(homeIcon)} />
            <span>Home</span>
          </NavLink>

          <NavLink className={style.menuButton} to='/dashboard'>
            <div style={SidebarComponent.menuButtonStyleGenerator(dashboardIcon)} />
            <span>Dashboard</span>
          </NavLink>

          <NavLink className={style.menuButton} to='/support'>
            <div style={SidebarComponent.menuButtonStyleGenerator(supportIcon)} />
            <span>Support</span>
          </NavLink>

          <NavLink className={style.menuButton} to='/donate'>
            <div style={SidebarComponent.menuButtonStyleGenerator(donateBoxIcon)} />
            <span>Donate</span>
          </NavLink>

        </div>

        <div className={style.auth}>
          <MediumButton text='Login' width='100%' onClick={() => this.props.navigate('/login')} />
          <MediumButton text='Sign up' width='100%' onClick={() => this.props.navigate('/signup')} />
        </div>

        <div className={style.links}>
          <Link className={style.link} to='/privacy'>Privacy</Link>
          <div className={style.linkDivider}></div>
          <Link className={style.link} to='/tos'>Terms of service</Link>
          <div className={style.linkDivider}></div>
          <Link className={style.link} to='/github'>Github</Link>
        </div>
      </div>
    );
  }

  private static menuButtonStyleGenerator = (icon: string): HTMLAttributes<HTMLDivElement>['style'] => {
    return {
      maskImage: `url(${icon})`,
      WebkitMaskImage: `url(${icon})`,
    };
  };
}

export default function Sidebar() {
  const navigate = useNavigate();

  return (
    <SidebarComponent navigate={navigate}/>
  );
}