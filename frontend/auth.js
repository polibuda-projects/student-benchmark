import Cookies from 'js-cookie';

export const signOut = () => {
  Cookies.remove('sessionId');
};

export const isSignedIn = () => {
  return !!Cookies.get('sessionId');
};

export const authenticate = async (username, password) => {
  try {
    const response = await fetch(`${process.env.REACT_APP_BACKEND_URL}/login`, {
      method: 'POST',
      body: JSON.stringify({ username, password }),
    });

    if (response.ok) {
      const data = await response.json();
      const sessionId = data.sessionId;
      Cookies.set('sessionId', sessionId);
      return sessionId;
    }
  } catch (error) {
    console.error(error);
    return null;
  }
};

export const reauthenticate = async (sessionId) => {
  try {
    const response = await fetch(`${process.env.REACT_APP_BACKEND_URL}/login`, {
      method: 'POST',
      headers: {
        'X-Session-Id': sessionId,
      },
    });

    if (response.ok) {
      return true;
    } else {
      return false;
    }
  } catch (error) {
    console.error(error);
    return false;
  }
};
