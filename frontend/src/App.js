import './App.css';
import { ThemeProvider } from '@material-ui/styles';
import theme from "./util/theme";
import Layout from './components/main_layout';
// import NotFound from './components/static_pages/not_found';
import About from './components/static_pages/about';

function App() {
  return (
    <ThemeProvider theme={theme}>
      <Layout>
        {/* <NotFound/> */}
        <About />
      </Layout>
    </ThemeProvider>
  );
}

export default App;
