import './App.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faGavel } from '@fortawesome/free-solid-svg-icons'
import axios from 'axios'
import { useState, useEffect } from 'react';

function App() {

  const [message, setMessage] = useState("");

  useEffect(() => {
    axios.get(`${process.env.REACT_APP_API_URL}/health`,).then((res) => {
      setMessage(res.data);
    })
  }, []);

  return (
    <div>{message}</div>
  );
}

export default App;
