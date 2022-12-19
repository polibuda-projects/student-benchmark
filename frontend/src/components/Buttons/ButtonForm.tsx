import { Component } from 'react';

import parentStyle from './ButtonMedium.module.css';
import style from './ButtonForm.module.css';


export interface ButtonFormProps {
  text: string;
  width?: string;
  onClick?: () => Promise<void> | void;
  isActive: boolean;
  className?: string;
  id?: string;
}

export interface ButtonFormState {
  isAwaiting: boolean;
}

export default class ButtonForm extends Component<ButtonFormProps, ButtonFormState> {
  constructor(props: ButtonFormProps) {
    super(props);

    this.state = {
      isAwaiting: false,
    };
  }

  render() {
    return (
      <button
        className={this.buttonClasses}
        style={{ width: this.props.width }}
        onClick={this.clickHandler}
        type='button'
        id={this.props.id}
      >
        {this.props.text}
      </button>
    );
  }

  private get buttonClasses(): string {
    return this.joinClasses(
        parentStyle.button,
        style.formButton,
        this.props.className ?? '',
      this.props.isActive ? '' : style.disabled,
        this.state.isAwaiting ? style.awaiting : '',
    );
  }

  private clickHandler = async () => {
    if (!this.props.isActive || this.state.isAwaiting) return;
    this.setState({ isAwaiting: true });

    await this.props.onClick?.();

    this.setState({ isAwaiting: false });
  };

  private joinClasses(...classes: string[]) {
    return classes.join(' ');
  }
}

