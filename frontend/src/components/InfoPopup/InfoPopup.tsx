import { Component } from 'react';
import close from '@resources/img/close.svg';

import style from './InfoPopup.module.css';


export interface InfoPopupProps {}

export interface InfoPopupState {}

export default class InfoPopup extends Component<InfoPopupProps, InfoPopupState> {
  private static messageQueue: Map<number, string> = new Map();
  private static killQueue: Set<number> = new Set();
  private static needsUpdate = false;
  private static uglyStateWatcher?: number;

  constructor(props: InfoPopupProps) {
    super(props);

    InfoPopup.killQueue.clear();
    InfoPopup.needsUpdate = true;
  }

  render() {
    return (
      <div className={style.container}>
        {Array.from(InfoPopup.messageQueue.entries()).slice(0, 5).map(([id, message], i) => {
          if (i === 0 && !InfoPopup.killQueue.has(id)) {
            InfoPopup.killQueue.add(id);

            setTimeout(() => {
              InfoPopup.messageQueue.delete(id);
              InfoPopup.killQueue.delete(id);
              this.forceUpdate();
            }, 6000);
          }

          return (
            <div className={this.joinClasses(style.message, i === 0 ? style.firstMessage : '')} key={id}>
              {message}

              <button
                className={style.closeButton}
                onClick={() => {
                  InfoPopup.messageQueue.delete(id);
                  InfoPopup.killQueue.delete(id);
                  this.forceUpdate();
                }}
              >
                <img src={close} alt='close' />
              </button>

            </div>
          );
        })}
      </div>
    );
  }

  private joinClasses(...classes: string[]) {
    return classes.join(' ');
  }

  public shouldComponentUpdate(): boolean {
    return InfoPopup.needsUpdate;
  }

  public componentDidMount(): void {
    if (InfoPopup.uglyStateWatcher === undefined) {
      InfoPopup.uglyStateWatcher = this.uglyStateWatcherLoop();
    }
  }

  public componentWillUnmount(): void {
    if (InfoPopup.uglyStateWatcher !== undefined) {
      clearInterval(InfoPopup.uglyStateWatcher);
      InfoPopup.uglyStateWatcher = undefined;
    }
  }

  private uglyStateWatcherLoop(): number {
    return setInterval(() => {
      if (InfoPopup.needsUpdate) {
        InfoPopup.needsUpdate = false;
        this.forceUpdate();
      }
    }, 100) as unknown as number;
  }

  public static addMessage(message: string) {
    let id;

    do {
      id = Math.floor(Math.random() * 100000);
    } while (InfoPopup.messageQueue.has(id));

    InfoPopup.messageQueue.set(id, message);
    InfoPopup.needsUpdate = true;
  }
}

