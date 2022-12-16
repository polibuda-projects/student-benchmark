const loginUrl = `${process.env.REACT_APP_BACKEND_URL}/login`;
const logoutUrl = `${process.env.REACT_APP_BACKEND_URL}/logout`;


interface UserState {
  username: string;
  sessionStart: Date;
}

export class InvalidSessionError extends Error {
  constructor() {
    super('Inalid session');
  }
}

export const removeSesionData = () => {
  sessionStorage.removeItem('USERSTATE');
};

export const logout = async () => {
  removeSesionData();

  try {
    const resp = await fetch(logoutUrl, { method: 'POST' });
    if (resp.status !== 200) throw new Error('Logout failed');
  } catch (err) {}

  document.location.href = '/';
};

export const validateUserState = (userState: UserState): userState is UserState => {
  if (typeof userState !== 'object') return false;
  if (typeof userState.username !== 'string') return false;
  if (typeof userState.sessionStart !== 'string') return false;

  const date = new Date(userState.sessionStart);
  if (isNaN(date.getTime())) return false;

  userState.sessionStart = date;
  return true;
};

export const getUserstate = (): UserState | null => {
  const rawSession = sessionStorage.getItem('USERSTATE');
  if (rawSession === null) return null;

  try {
    const userState = JSON.parse(rawSession);
    if (!validateUserState(userState)) throw new InvalidSessionError();

    return userState;
  } catch (err) {
    removeSesionData();
    return null;
  }
};

export const setUserState = (username: string): void => {
  const userState: UserState = {
    username: username,
    sessionStart: new Date(),
  };

  sessionStorage.setItem('USERSTATE', JSON.stringify(userState));
};

export const isLoggedIn = (): boolean => {
  const userState = getUserstate();
  if (userState === null) return false;

  const now = new Date();
  const diff = now.getTime() - userState.sessionStart.getTime();

  if (diff > 1000 * 60 * 120) {
    removeSesionData();
    return false;
  }

  return true;
};

export async function login(username: string, password: string): Promise<number | string> {
  const body = new FormData();
  body.append('username', username);
  body.append('password', password);

  const requestOptions = {
    method: 'POST',
    body: body,
  };

  try {
    const resp = await fetch(loginUrl, requestOptions);
    if (resp.status !== 200) return await resp.text();
    setUserState(username);

    document.location.href = '/';
    return 0;
  } catch (err) { }

  return 'Login failed';
};

