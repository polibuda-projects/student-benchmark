import { ChangeEventHandler, Component } from 'react';

import style from './Input.module.css';

const validEmailRegex = RegExp(/^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/);
const passwordLengthRegex = RegExp(/^(?=.{8,64})/);
const passwordCapitalLetterRegex = RegExp(/^(?=.*?[A-Z])/);
const passwordLowercaseLetterRegex = RegExp(/^(?=.*?[a-z])/);
const passwordNumberRegex = RegExp(/^(?=.*?[0-9])/);
const passwordSpecialCharacterRegex = RegExp(/^(?=.*?[#?!@$%^&*-])/);

export interface InputProps {
  type?: string,
  name?: string,
  placeholder?: string,
  shadows?: boolean,
  className?: string,
  focus?: boolean,
  id?: string,
  autoComplete?: string;
  required?: boolean;
  useRef?: React.RefObject<HTMLInputElement>;
  correctValue?: (isCorrect: boolean) => void,
}

export interface InputState {
  message: string;
}

export default class Inputs extends Component<InputProps, InputState> {
  private static defaultProps: InputProps = {
    shadows: true,
    focus: false,
  };

  constructor(props: InputProps) {
    super(props);
    this.props.correctValue?.(false);

    this.state = {
      message: '',
    };
  }

  private validateInput: ChangeEventHandler = (event) => {
    event.preventDefault();
    const input = event.target as HTMLInputElement;
    const value = input.value;

    if (input.name.includes('email')) {
      if (!validEmailRegex.test(value)) {
        this.setState({ message: 'Email is not valid' });
        this.props.correctValue?.(false);
      } else if (value.length > 64) {
        this.setState({ message: 'Email is too long' });
        this.props.correctValue?.(false);
      } else {
        this.setState({ message: '' });
        this.props.correctValue?.(true);
      }
    }

    if (input.name.includes('passwordLogin')) {
      if (!passwordLengthRegex.test(value)) {
        this.setState({ message: 'Password must be between 8 and 64 characters' });
        this.props.correctValue?.(false);
      } else {
        this.setState({ message: '' });
        this.props.correctValue?.(true);
      }
    }

    if (input.name.includes('passwordRegister')) {
      if (!passwordCapitalLetterRegex.test(value)) {
        this.setState({ message: 'Password must contain at least one capital letter' });
        this.props.correctValue?.(false);
      } else if (!passwordLowercaseLetterRegex.test(value)) {
        this.setState({ message: 'Password must contain at least one lowercase letter' });
        this.props.correctValue?.(false);
      } else if (!passwordNumberRegex.test(value)) {
        this.setState({ message: 'Password must contain at least one number' });
        this.props.correctValue?.(false);
      } else if (!passwordSpecialCharacterRegex.test(value)) {
        this.setState({ message: 'Password must contain at least one special character' });
        this.props.correctValue?.(false);
      } else if (!passwordLengthRegex.test(value)) {
        this.setState({ message: 'Password must be between 8 and 64 characters' });
        this.props.correctValue?.(false);
      } else {
        this.setState({ message: '' });
        this.props.correctValue?.(true);
      }
    }

    if (input.name.includes('username')) {
      if (value.length < 3) {
        this.setState({ message: 'Username is too short' });
        this.props.correctValue?.(false);
      } else if (value.length > 64) {
        this.setState({ message: 'Username is too long' });
        this.props.correctValue?.(false);
      } else {
        this.setState({ message: '' });
        this.props.correctValue?.(true);
      }
    }

    if (input.name.includes('title')) {
      if (value.length > 64) {
        this.setState({ message: 'Title is too long' });
        this.props.correctValue?.(false);
      } else if (value.length < 5) {
        this.setState({ message: 'Title is too short' });
        this.props.correctValue?.(false);
      } else {
        this.setState({ message: '' });
        this.props.correctValue?.(true);
      }
    }
  };

  render() {
    return (
      <div className={style.container}>
        <input ref={this.props.useRef} className={[style.input, this.props.className, this.props.shadows ? style.shadow : ''].join(' ')}
          type={this.props.type} name={this.props.name} placeholder={this.props.placeholder} autoFocus ={this.props.focus} id={this.props.id}
          autoComplete={this.props.autoComplete} required={this.props.required} onChange={this.validateInput} />
        <span className={style.message}>{this.state.message}</span>
      </div>
    );
  }
}

