import './index.css';

import React from 'react';
import ReactDOM from 'react-dom/client';
import { createBrowserRouter, RouterProvider } from 'react-router-dom';

import Home from '@views/Home/Home';
import Login from '@views/Login/Login';
import Policy from '@views/Policy/Policy';
import Donate from '@views/Donate/Donate';
import Recover from '@views/Recover/Recover';
import Delete from '@views/Delete/Delete';
import Signup from '@views/Signup/Signup';
import Support from '@views/Support/Support';
import User from '@views/User/User';
import TestsHome from '@views/Tests/Home/Home';
import Password from '@views/Password/Password';
import Dashboard from '@views/Dashboard/Dashboard';
import VisualTest from '@views/Tests/VisualTest/VisualTest';
import VerbalTest from '@views/Tests/VerbalTest/VerbalTest';
import SequenceTest from '@views/Tests/SequenceTest/SequenceMemory';
import NumberTest from '@views/Tests/NumberTest/NumberTest';
import Admin from '@views/Admin/Admin';
import RestrictedRoute from '@components/RestrictedRoute/RestrictedRoute';
import { isLoggedIn } from './auth';


const router = createBrowserRouter([
  {
    path: '/',
    element: <Home />,
    errorElement: <div>404</div>,
  },
  {
    path: '/tests',
    element: <TestsHome />,
  },
  {
    path: '/tests/number',
    element: <NumberTest />,
  },
  {
    path: '/tests/sequence',
    element: <SequenceTest />,
  },
  {
    path: '/tests/verbal',
    element: <VerbalTest />,
  },
  {
    path: '/tests/visual',
    element: <VisualTest />,
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
    path: '/delete',
    element: <RestrictedRoute
      condition={isLoggedIn}
      component={<Delete />}
      invalidComponent={<Login />}
    />,
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
    element: <RestrictedRoute
      condition={isLoggedIn}
      component={<User />}
      invalidComponent={<Login />}
    />,
  },
  {
    path: '/password',
    element: <RestrictedRoute
      condition={isLoggedIn}
      component={<Password />}
      invalidComponent={<Login />}
    />,
  },
  {
    path: '/dashboard',
    element: <Dashboard />,
  },
  {
    path: '/donate',
    element: <Donate />,
  },
  {
    path: '/admin',
    element: <RestrictedRoute
      condition={isLoggedIn}
      component={<Admin />}
      invalidComponent={<Login />}
    />,
  },
]);

ReactDOM.createRoot(document.getElementById('root')).render(
    <React.StrictMode>
      <RouterProvider router={router} />
    </React.StrictMode>
);

