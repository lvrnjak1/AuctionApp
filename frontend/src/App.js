import './App.css';
import { ThemeProvider } from '@material-ui/styles';
import theme from "./util/theme";
import Layout from './components/main_layout/layout';

function App() {
  return (
    <ThemeProvider theme={theme}>
      <Layout>

      </Layout>
    </ThemeProvider>
  );
}

export default App;
