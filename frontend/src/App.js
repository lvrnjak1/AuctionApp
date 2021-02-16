import './App.css';
import { ThemeProvider } from '@material-ui/styles';
import theme from "./util/theme";
import Layout from './components/layout';
import Terms from './static_pages/terms';
import Privacy from './static_pages/privacy';
import NotFound from './static_pages/not_found';
import About from './static_pages/about';
import {
  BrowserRouter as Router,
  Switch,
  Route
} from "react-router-dom";


function App() {
  return (
    <Router>
      <ThemeProvider theme={theme}>
        <Layout>
          <Switch>
            <Route exact path="/about">
              <About />
            </Route>
            <Route exact path="/terms">
              <Terms />
            </Route>
            <Route exact path="/privacy">
              <Privacy />
            </Route>
            <Route path="/">
              <NotFound />
            </Route>
          </Switch>
        </Layout>
      </ThemeProvider>
    </Router>
  );
}

export default App;
