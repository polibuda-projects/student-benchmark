import './index.css';

import React from 'react';
import ReactDOM from 'react-dom/client';
import { createBrowserRouter, RouterProvider } from 'react-router-dom';

import Home from '@views/Home/Home';
import Login from '@views/Login/Login';
import Policy from '@views/Policy/Policy';
import Donate from '@views/Donate/Donate';
import Github from '@views/Donate/Github';
import Recover from '@views/Recover/Recover';
import Delete from '@views/Delete/Delete';
import Signup from '@views/Signup/Signup';
import Support from '@views/Support/Support';
import User from '@views/User/User';
import TestsHome from '@views/Tests/Home/Home';
import Password from '@views/Password/Password';
import ResetPassword from '@views/Password/ResetPassword';
import Dashboard from '@views/Dashboard/Dashboard';
import VisualTest from '@views/Tests/VisualTest/VisualTest';
import VerbalTest from '@views/Tests/VerbalTest/VerbalTest';
import SequenceTest from '@views/Tests/SequenceTest/SequenceMemory';
import NumberTest from '@views/Tests/NumberTest/NumberTest';
import Admin from '@views/Admin/Admin';
import RestrictedRoute from '@components/RestrictedRoute/RestrictedRoute';
import { isAdmin, isLoggedIn, updateUserState } from './auth';


const router = createBrowserRouter([
  {
    path: '/',
    element: <Home />,
    errorElement: <div>404</div>,
  },
  {
    path: '/tests',
    element: <TestsHome />,
    errorElement: <Home />,
  },
  {
    path: '/tests/number',
    element: <NumberTest />,
    errorElement: <Home />,
  },
  {
    path: '/tests/sequence',
    element: <SequenceTest />,
    errorElement: <Home />,
  },
  {
    path: '/tests/verbal',
    element: <VerbalTest />,
    errorElement: <Home />,
  },
  {
    path: '/tests/visual',
    element: <VisualTest />,
    errorElement: <Home />,
  },
  {
    path: '/login',
    element: <Login />,
    errorElement: <Home />,
  },
  {
    path: '/privacy',
    element: <Policy />,
    errorElement: <Home />,
  },
  {
    path: '/recover',
    element: <Recover />,
    errorElement: <Home />,
  },
  {
    path: '/resetPassword',
    element: <ResetPassword />,
    errorElement: <Home />,
  },
  {
    path: '/delete',
    element: <RestrictedRoute
      condition={isLoggedIn}
      component={<Delete />}
      invalidComponent={<Login />}
    />,
    errorElement: <Home />,
  },
  {
    path: '/signup',
    element: <Signup />,
    errorElement: <Home />,
  },
  {
    path: '/support',
    element: <RestrictedRoute
      condition={isLoggedIn}
      component={<Support />}
      invalidComponent={<Login />}
    />,
    errorElement: <Home />,
  },
  {
    path: '/settings',
    element: <RestrictedRoute
      condition={isLoggedIn}
      component={<User />}
      invalidComponent={<Login />}
    />,
    errorElement: <Home />,
  },
  {
    path: '/password',
    element: <RestrictedRoute
      condition={isLoggedIn}
      component={<Password />}
      invalidComponent={<Login />}
    />,
    errorElement: <Home />,
  },
  {
    path: '/dashboard',
    element: <RestrictedRoute
      condition={isLoggedIn}
      component={<Dashboard />}
      invalidComponent={<Login />}
    />,
    errorElement: <Home />,
  },
  {
    path: '/donate',
    element: <Donate />,
    errorElement: <Home />,
  },
  {
    path: '/github',
    element: <Github />,
    errorElement: <Home />,
  },
  {
    path: '/admin',
    element: <RestrictedRoute
      condition={isAdmin}
      component={<Admin />}
      invalidComponent={<Home />}
    />,
    errorElement: <Home />,
  },
]);

const reactRoot = () => {
  ReactDOM.createRoot(document.getElementById('root')).render(
      <React.StrictMode>
        <RouterProvider router={router} />
      </React.StrictMode>
  );
};

// redundancy bc no top level await
updateUserState()
    .then(() => reactRoot())
    .catch(() => reactRoot());

