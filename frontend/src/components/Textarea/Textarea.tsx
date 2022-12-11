import { ChangeEventHandler, Component } from 'react';

import style from './Textarea.module.css';

const textLengthRegex = /^.{5,256}$/;

export interface TextareaProps {
  type?: string,
  name?: string,
  placeholder?: string,
  shadows?: boolean,
  className?: string
}

export interface TextareaState {
  message: string;
}

export default class Textarea extends Component<TextareaProps, TextareaState> {
  private static defaultProps: TextareaProps = {
    shadows: true,
  };

  constructor(props: TextareaProps) {
    super(props);

    this.state = {
      message: '',
    };
  }

  private validateInput: ChangeEventHandler = (event) => {
    event.preventDefault();
    const input = event.target as HTMLInputElement;
    const value = input.value;

    if (input.name.includes('text')) {
      if (!textLengthRegex.test(value)) {
        this.setState({ message: 'Message must be beetween 5 and 256 characters' });
      } else {
        this.setState({ message: '' });
      }
    }
  };

  render() {
    return (
      <div className={style.textareaContainer}>
        <textarea className={[style.textarea, this.props.className, this.props.shadows ? style.shadow : ''].join(' ')}
          name={this.props.name} placeholder={this.props.placeholder} onChange={this.validateInput} />
        <span className={style.message}>{this.state.message}</span>
      </div>
    );
  }
}
