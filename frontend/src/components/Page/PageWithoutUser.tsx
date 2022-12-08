import style from './Page.module.css';

import { Component, HTMLAttributes } from 'react';
import Sidebar from '@components/Sidebar/Sidebar';


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

          <div className={[style.content, this.props.background ? style.contentBackground : ''].join(' ')}>
            {this.props.children}

          </div>
        </div>
      </div>
    );
  }
}
