import './App.css';
import { ThemeProvider } from '@material-ui/styles';
import theme from "./util/theme";
import Layout from './components/layout';
import Terms from './components/static_pages/terms';
import Privacy from './components/static_pages/privacy';
import NotFound from './components/static_pages/not_found';
import About from './components/static_pages/about';

function App() {
  return (
    <ThemeProvider theme={theme}>
      <Layout>
        <Privacy />
      </Layout>
    </ThemeProvider>
  );
}

export default App;
