import style from './TestBox.module.css';

export type TestBoxProps = {
  text: string;
  src: string;
  setToggle: (state: TestBoxEnum) => void;
  toggle: TestBoxEnum;
  type: TestBoxEnum;
}

export enum TestBoxEnum {
  sequence,
  visual,
  verbal,
  number,
};

const TestBox = (props: TestBoxProps) => {
  const toggleHandler = () => {
    props.setToggle(props.type);
  };

  return (
    <div>
      <div className={props.toggle === props.type ? `${style.active}` : `${style.inactive}`} onClick={toggleHandler}>
        <img src={props.src} alt="src" className={style.image} />
        <span>{props.text}</span>
      </div>
    </div>
  );
};

export default TestBox;
