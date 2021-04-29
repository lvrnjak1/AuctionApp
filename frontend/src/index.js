import React from 'react';
import ReactDOM from 'react-dom';
import 'index.css';
import App from 'App';
import { Provider } from 'react-redux';
import store from "state/store";
import { Elements } from '@stripe/react-stripe-js';
import { loadStripe } from '@stripe/stripe-js';

const stripePromise = loadStripe(process.env.REACT_APP_STRIPE_PUBLIC_KEY);

ReactDOM.render(
  <Elements stripe={stripePromise}>
    <Provider store={store}>
      <App />
    </Provider>
  </Elements>,
  document.getElementById('root')
);
