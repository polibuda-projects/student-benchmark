import style from './Admin.module.css';

import Page from '@components/Page/Page';
import ContainerBox from '@components/ContainerBox/ContainerBox';
import { useState, useEffect } from 'react';
import sampleData from './sampleData.json';

function Admin() {
// useState do zmiany i dodanie pobierania danych z backendu
  const [data, setData] = useState(sampleData);

  useEffect(() => {
    async function fetchData() {
      const response = await fetch('<backend-url>');
      const data = await response.json();
      setData(data);
    }
    fetchData();
  }, []);

  return (
    <Page user={false} titlebar={false}>
      <ContainerBox className={ContainerBox}>
        <div className={style.messagesContainer}>
          <h1 className={style.title}>Messages</h1>
          <div className={style.messages}>
            {data ? data.messages.map((message) => (
              <div className={style.message} key={message.id}>
                <p className={style.messageID}>{message.id}</p>
                <p className={style.messageTimestamp}>{message.timestamp}</p>
                <p className={style.messageText}>{message.text}</p>
              </div>
            )) : (
              <p className={style.placeholder}>No messages to display</p>
            )}
          </div>
        </div>
        <div className={style.logsContainer}>
          <h1 className={style.title}>Logs</h1>
          <div className={style.logs}>
            {data ? data.logs.map((log) => (
              <div className={style.log} key={log.id}>
                <p className={style.logID}>{log.id}</p>
                <p className={style.logTimestamp}>{log.timestamp}</p>
                <p className={style.logText}>{log.text}</p>
              </div>
            )) : (
              <p className={style.placeholder}>No logs to display</p>
            )}
          </div>
        </div>
      </ContainerBox>

    </Page>
  );
}


export default Admin;
