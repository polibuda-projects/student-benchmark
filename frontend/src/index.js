import './index.css';

import React from 'react';
import ReactDOM from 'react-dom/client';
import {
  createBrowserRouter,
  RouterProvider,
} from 'react-router-dom';

import Home from '@views/Home/Home';
import Login from '@views/Login/Login';
import Policy from '@views/Policy/Policy';
import Recover from '@views/Recover/Recover';
import Signup from '@views/Signup/Signup';
import Support from '@views/Support/Support';
import User from '@views/User/User';

const router = createBrowserRouter([
  {
    path: '/',
    element: <Home />,
    errorElement: <div>404</div>,
  },
  {
    path: '/login',
    element: <Login />,
  },
  {
    path: '/privacy',
    element: <Policy />,
  },
  {
    path: '/recover',
    element: <Recover />,
  },
  {
    path: '/signup',
    element: <Signup />,
  },
  {
    path: '/support',
    element: <Support />,
  },
  {
    path: '/settings',
    element: <User />,
  },
]);

ReactDOM.createRoot(document.getElementById('root')).render(
    <React.StrictMode>
      <RouterProvider router={router} />
    </React.StrictMode>
);
