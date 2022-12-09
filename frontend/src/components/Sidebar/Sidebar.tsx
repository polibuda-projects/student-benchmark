import style from './Sidebar.module.css';
import logo from '@resources/img/logo.svg';
import icon from '@resources/img/icon.svg';
import leftDoubleArrow from '@resources/img/leftDoubleArrow.svg';

import homeIcon from '@resources/img/homeIcon.svg';
import dashboardIcon from '@resources/img/dashboardIcon.svg';
import supportIcon from '@resources/img/supportIcon.svg';
import donateBoxIcon from '@resources/img/donateBoxIcon.svg';

import MediumButton from '@components/Buttons/ButtonMedium';
import { Link, NavLink } from 'react-router-dom';
import { Component, HTMLAttributes } from 'react';


export interface SidebarProps {}

export interface SidebarState {
  active: boolean;
  floating: boolean;
}

export default class SidebarComponent extends Component<SidebarProps, SidebarState> {
  private static staticActive = true;

  constructor(props: SidebarProps) {
    super(props);

    this.state = {
      active: SidebarComponent.staticActive,
      floating: false,
    };
  }

  render() {
    return (
      <div className={this.dummyContainerClasses}>
        <div className={style.container}>
          <div className={style.sidebar}>
            <Link className={style.branding} to='/#'>
              <img src={logo} className={style.logo} alt='Student Benchmark' />
              <img src={icon} className={style.icon} alt=''/>
            </Link>

            <div className={style.menu}>
              <NavLink className={this.menuNavlinkStyleGenrator} to='/'>
                <div style={SidebarComponent.menuButtonStyleGenerator(homeIcon)} />
                <span>Home</span>
              </NavLink>

              <NavLink className={this.menuNavlinkStyleGenrator} to='/dashboard'>
                <div style={SidebarComponent.menuButtonStyleGenerator(dashboardIcon)} />
                <span>Dashboard</span>
              </NavLink>

              <NavLink className={this.menuNavlinkStyleGenrator} to='/support'>
                <div style={SidebarComponent.menuButtonStyleGenerator(supportIcon)} />
                <span>Support</span>
              </NavLink>

              <NavLink className={this.menuNavlinkStyleGenrator} to='/donate'>
                <div style={SidebarComponent.menuButtonStyleGenerator(donateBoxIcon)} />
                <span>Donate</span>
              </NavLink>

            </div>

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

          <img className={style.burger} src={leftDoubleArrow} onClick={this.toggleSidebar} alt='?'/>
          <div className={style.shadowBox}></div>
        </div>
      </div>
    );
  }

  private menuNavlinkStyleGenrator =(props: { isActive: boolean, isPending: boolean}): string => {
    return this.joinClasses(style.menuButton, props.isActive ? style.active : '');
  };

  private static menuButtonStyleGenerator = (icon: string): HTMLAttributes<HTMLDivElement>['style'] => {
    return {
      maskImage: `url(${icon})`,
      WebkitMaskImage: `url(${icon})`,
    };
  };

  private joinClasses(...classes: string[]) {
    return classes.join(' ');
  }

  private toggleSidebar = () => {
    SidebarComponent.staticActive = !this.state.active;
    this.setState({ active: SidebarComponent.staticActive });
  };

  private get dummyContainerClasses() {
    return this.joinClasses(style.dummyContainer, this.state.active ? '':style.sidebarThin, this.state.floating ? style.sidebarFloating : '');
  }

  public componentDidMount() {
    this.registerEventListeners();
    this.resizeViewer();
  }

  public componentDidUpdate() {
    this.unregisterEventListeners();
    this.registerEventListeners();
  }

  public componentWillUnmount() {
    this.unregisterEventListeners();
  }

  private registerEventListeners() {
    window.addEventListener('resize', this.resizeViewer);
  }

  public unregisterEventListeners(): void {
    window.removeEventListener('resize', this.resizeViewer);
  }

  private resizeViewer = () => {
    if (window.innerWidth < 800) this.setState({ floating: true });
    else this.setState({ floating: false });

    if (window.innerWidth < 1024) {
      // wrapping in timeout makes it animate on route change
      setTimeout(() => {
        this.setState({ active: false });
        SidebarComponent.staticActive = false;
      }, 1);
    }
  };
}
