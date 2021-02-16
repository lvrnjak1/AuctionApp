import './App.css';
import { ThemeProvider } from '@material-ui/styles';
import theme from "./util/theme";
import Layout from './components/main_layout/layout';
import NotFound from './components/static_pages/not_found';

function App() {
  return (
    <ThemeProvider theme={theme}>
      <Layout>
        <NotFound />
      </Layout>
    </ThemeProvider>
  );
}

export default App;
