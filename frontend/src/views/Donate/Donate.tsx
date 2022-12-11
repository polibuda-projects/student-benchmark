import style from './Donate.module.css';
import { useEffect } from 'react';


const Donate = () => {
  useEffect(() => {
    window.location.replace('https://www.buymeacoffee.com/polibudaproject');
  }, []);

  return <div className={style.Redirecting}>
    <h2>Redirecting...</h2>
  </div>;
};

export default Donate;
