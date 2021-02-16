import './App.css';
import { ThemeProvider } from '@material-ui/styles';
import theme from "./util/theme";
import Navbar from './components/navbar';

function App() {
  return (
    <ThemeProvider theme={theme}>
      <Navbar />
    </ThemeProvider>
  );
}

export default App;
