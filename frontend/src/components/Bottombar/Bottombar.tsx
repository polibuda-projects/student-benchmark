import style from './Bottombar.module.css';

import homeIcon from '@resources/img/homeIcon.svg';
import dashboardIcon from '@resources/img/dashboardIcon.svg';
import supportIcon from '@resources/img/supportIcon.svg';
import donateBoxIcon from '@resources/img/donateBoxIcon.svg';
import burgerIcon from '@resources/img/burgerIcon.svg';

import MediumButton from '@components/Buttons/ButtonMedium';
import { Link, NavLink } from 'react-router-dom';
import { Component, HTMLAttributes } from 'react';


export interface BottombarProps {
  className?: string;
}

export interface BottombarState {
  active: boolean;
}

export default class BottombarComponent extends Component<BottombarProps, BottombarState> {
  constructor(props: BottombarProps) {
    super(props);

    this.state = {
      active: false,
    };
  }

  render() {
    return (
      <div className={this.dummyPositioningContainerClasses}>
        <div className={style.container}>
          <NavLink className={this.menuNavlinkStyleGenrator} to='/support'>
            <div style={BottombarComponent.menuButtonStyleGenerator(supportIcon)} />
            <span>Support</span>
          </NavLink>

          <NavLink className={this.menuNavlinkStyleGenrator} to='/donate'>
            <div style={BottombarComponent.menuButtonStyleGenerator(donateBoxIcon)} />
            <span>Donate</span>
          </NavLink>

          <NavLink className={this.menuNavlinkStyleGenrator} to='/'>
            <div style={BottombarComponent.menuButtonStyleGenerator(homeIcon)} />
            <span>Home</span>
          </NavLink>

          <NavLink className={this.menuNavlinkStyleGenrator} to='/dashboard'>
            <div style={BottombarComponent.menuButtonStyleGenerator(dashboardIcon)} />
            <span>Dashboard</span>
          </NavLink>

          <div className={this.burgetStyleGenerator} onClick={this.toggleDropdown}>
            <div style={BottombarComponent.menuButtonStyleGenerator(burgerIcon)} />
            <span>More</span>
          </div>

          <div className={this.dropdownStyleGenerator}>
            <div className={style.auth}>
              <Link to='/login'><MediumButton text='Login' width='100%'/></Link>
              <Link to='/signup'><MediumButton text='Sign up' width='100%'/></Link>
            </div>

            <div className={style.links}>
              <Link className={style.link} to='/privacy'>Privacy & Terms of service</Link>
              <div className={style.linkDivider}></div>
              <Link className={style.link} to='/github'>Github</Link>
            </div>
          </div>
        </div>
      </div>
    );
  }

  private toggleDropdown = () => {
    this.setState({ active: !this.state.active });
  };

  private menuNavlinkStyleGenrator =(props: { isActive: boolean, isPending: boolean}): string => {
    return this.joinClasses(style.menuButton, props.isActive ? style.active : '');
  };

  private get burgetStyleGenerator(): string {
    return this.joinClasses(style.menuButton, style.burger, this.state.active ? style.active : '');
  }

  private get dropdownStyleGenerator(): string {
    return this.joinClasses(style.dropdown, this.state.active ? style.dropdownActive : '');
  }

  private static menuButtonStyleGenerator = (icon: string): HTMLAttributes<HTMLDivElement>['style'] => {
    return {
      maskImage: `url(${icon})`,
      WebkitMaskImage: `url(${icon})`,
    };
  };

  private joinClasses(...classes: string[]) {
    return classes.join(' ');
  }

  private get dummyPositioningContainerClasses() {
    return this.joinClasses(
        style.dummyPositioningContainer,
        this.props.className ?? '',
    );
  }
}
