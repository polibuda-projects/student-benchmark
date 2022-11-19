import style from './Page.module.css';

import { Component, HTMLAttributes } from 'react';
import Sidebar from '@components/Sidebar/Sidebar';
import UserProfile from '@components/UserProfile/UserProfile';


interface PageProps extends HTMLAttributes<HTMLDivElement> {
  title?: string;
  sidebar?: boolean;
  user?: boolean;
  background?: boolean
}

export default class Page extends Component<PageProps> {
  private static defaultProps: PageProps = {
    title: '',
    sidebar: true,
    user: true,
    background: false,
  };

  render() {
    return (
      <div className={style.app}>
        {this.props.sidebar && <Sidebar />}

        <div className={style.container}>
          <div className={style.topBar}>
            {this.props.user && <UserProfile username='UserWithLongUsername' />}
          </div>

          <div className={style.titleBar}>
            <span>{this.props.title}</span>
          </div>

          <div className={[style.content, this.props.background ? style.contentBackground : ''].join(' ')}>
            {this.props.children}

          </div>
        </div>
      </div>
    );
  }
}
