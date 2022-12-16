import style from './Page.module.css';

import { Component, HTMLAttributes } from 'react';
import Sidebar from '@components/Sidebar/Sidebar';
import UserProfile from '@components/UserProfile/UserProfile';

import icon from '@resources/img/icon.svg';
import { Link } from 'react-router-dom';
import Bottombar from '@components/Bottombar/Bottombar';


interface PageProps {
  title?: string;
  sidebar?: boolean;
  topbar?: boolean;
  titlebar?: boolean;
  user?: boolean;
  content?: boolean;
  background?: boolean;
  contentClassName?: string;
  children?: HTMLAttributes<HTMLDivElement>['children'];
}

export default class Page extends Component<PageProps> {
  private static defaultProps: PageProps = {
    title: '',
    sidebar: true,
    topbar: true,
    titlebar: true,
    user: true,
    content: true,
    background: false,
    contentClassName: '',
  };

  render() {
    return (
      <div className={style.app}>
        {this.props.sidebar && <Sidebar className={style.sidebar} />}

        <div className={this.containerClasses}>
          {this.props.topbar &&
            <div className={style.topBar}>
              <Link className={style.branding} to='/#'>
                <img src={icon} className={style.icon} alt='' />
              </Link>
              {this.props.user && <UserProfile />}
            </div>
          }

          {this.props.titlebar &&
            <div className={style.titleBar}>
              <span>{this.props.title}</span>
            </div>
          }

          {this.props.content ?
            <div className={[
              style.content,
              this.props.background ? style.contentBackground : '',
              this.props.contentClassName,
            ].join(' ')}> {this.props.children}</div> :
            this.props.children
          }
        </div>

        {this.props.sidebar && <Bottombar className={style.bottombar} />}
      </div>
    );
  }

  private get containerClasses(): string {
    if (this.props.sidebar) return [style.container, style.containerPadded].join(' ');
    return style.container;
  }
}

