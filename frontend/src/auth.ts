const loginUrl = `${process.env.REACT_APP_BACKEND_URL}/login`;
const logoutUrl = `${process.env.REACT_APP_BACKEND_URL}/logout`;
const fetchUserUrl = `${process.env.REACT_APP_BACKEND_URL}/user`;


interface UserState {
  username: string;
  sessionStart: Date;
  role: Role;
}

enum Role {
  ROLE_USER,
  ROLE_ADMIN,
}

interface User {
  username: string;
  role: Role;
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
  if (typeof userState.role !== 'number') return false;

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
    if (!validateUserState(userState)) return null;

    return userState;
  } catch (err) {
    removeSesionData();
    return null;
  }
};

export const setUserState = (username: string, role: Role): void => {
  const userState: UserState = {
    username: username,
    sessionStart: new Date(),
    role,
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

export const isAdmin = (): boolean => {
  if (!isLoggedIn()) return false;

  const userState = getUserstate();
  if (userState === null) return false;

  return userState.role === Role.ROLE_ADMIN;
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
    const user = await fetchUser();

    if (user === null) return 'Login failed';
    setUserState(user.username, user.role);

    document.location.href = '/';
    return 0;
  } catch (err) { }

  return 'Login failed';
};

export const validateUser = (user: User): user is User => {
  if (typeof user !== 'object') return false;
  if (typeof user.username !== 'string') return false;
  if (typeof user.role !== 'string') return false;

  switch (user.role) {
    case Role[Role['ROLE_USER']] as unknown as Role: user.role = Role.ROLE_USER; break;
    case Role[Role['ROLE_ADMIN']] as unknown as Role: user.role = Role.ROLE_ADMIN; break;
    default: return false;
  }


  return true;
};

export const fetchUser = async (): Promise<User | null> => {
  const requestOptions = {
    method: 'GET',
  };

  try {
    const resp = await fetch(fetchUserUrl, requestOptions);
    console.log(resp);
    if (resp.status !== 200) return null;

    const user = await resp.json();
    if (!validateUser(user)) return null;

    return user;
  } catch (err) { }

  return null;
};
