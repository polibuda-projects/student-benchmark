import style from './Admin.module.css';

import Page from '@components/Page/Page';
import ContainerBox from '@components/ContainerBox/ContainerBox';
import { useState, useEffect } from 'react';
const fetchUrlMessages = `${process.env.REACT_APP_BACKEND_URL}/adminDashboard/messages`;
const fetchUrlLogs = `${process.env.REACT_APP_BACKEND_URL}/adminDashboard/logs`;

function Admin() {
  const [messages, setMessages] = useState(null);
  const [logs, setLogs] = useState(null);

  useEffect(() => {
    async function fetchData() {
      try {
        const responseMessages = await fetch(fetchUrlMessages);
        const messages = await responseMessages.json();
        if (Array.isArray(messages)) {
          setMessages(messages);
        }

        const responseLogs = await fetch(fetchUrlLogs);
        const logs = await responseLogs.json();
        if (Array.isArray(logs)) {
          setLogs(logs);
        }
      } catch (error) {}
    }
    fetchData();
  }, []);

  return (
    <Page user={false} titlebar={false}>
      <ContainerBox className={ContainerBox}>
        <div className={style.messagesContainer}>
          <h1 className={style.title}>Messages</h1>
          <div className={style.tableColumnNames}>
            <p className={style.messageID}>MessageID</p>
            <p className={style.messageID}>UserID</p>
            <p className={style.messageTitle}>Title</p>
            <p className={style.messageEmail}>Email</p>
            <p className={style.messageText}>Message</p>
          </div>
          <div className={style.messages}>
            {messages ? messages.map((message) => (
              <div className={style.message} key={message.id_Support}>
                <p className={style.messageID}>{message.id_Support}</p>
                <p className={style.messageID}>{message.id_user}</p>
                <p className={style.messageTitle}>{message.messageTitle}</p>
                <p className={style.messageEmail}>{message.email}</p>
                <p className={style.messageText}>{message.message}</p>
              </div>
            )) : (
              <p className={style.placeholder}>No messages to display</p>
            )}
          </div>
        </div>
        <div className={style.logsContainer}>
          <h1 className={style.title}>Logs</h1>
          <div className={style.tableColumnNames}>
            <p className={style.logID}>LogID</p>
            <p className={style.logID}>UserID</p>
            <p className={style.logUser}>User</p>
            <p className={style.logTimestamp}>Timestamp</p>
            <p className={style.logText}>Context</p>
          </div>
          <div className={style.logs}>
            {logs ? logs.map((log) => (
              <div className={style.log} key={log.idLog}>
                <p className={style.logID}>{log.idLog}</p>
                <p className={style.logID}>{log.userId}</p>
                <p className={style.logUser}>{log.user}</p>
                <p className={style.logTimestamp}>{log.date}</p>
                <p className={style.logText}>{log.context}</p>
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
