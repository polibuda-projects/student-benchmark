import style from './Page.module.css';

import { Component, HTMLAttributes } from 'react';
import Sidebar from '@components/Sidebar/Sidebar';
import UserProfile from '@components/UserProfile/UserProfile';


interface PageProps {
  title?: string;
  sidebar?: boolean;
  titlebar?: boolean;
  user?: boolean;
  content?: boolean;
  background?: boolean;
  children?: HTMLAttributes<HTMLDivElement>['children'];
}

export default class Page extends Component<PageProps> {
  private static defaultProps: PageProps = {
    title: '',
    sidebar: true,
    titlebar: true,
    user: true,
    content: true,
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

          {this.props.titlebar &&
            <div className={style.titleBar}>
              <span>{this.props.title}</span>
            </div>
          }

          {this.props.content ?
            <div className={[style.content, this.props.background ? style.contentBackground : ''].join(' ')}> {this.props.children}</div> :
            this.props.children
          }
        </div>
      </div>
    );
  }
}
