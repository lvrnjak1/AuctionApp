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
        <Switch>
          <Route exact path="/shop/about">
            <Layout breadcrumbs={{ current: "about us", breadcrumbs: ["shop/", "about"] }}>
              <About />
            </Layout>
          </Route>
          <Route exact path="/shop/terms">
            <Layout breadcrumbs={{ current: "terms and conditions", breadcrumbs: ["shop/", "terms and conditions"] }}>
              <Terms />
            </Layout>
          </Route>
          <Route exact path="/shop/privacy">
            <Layout breadcrumbs={{ current: "privacy and policy", breadcrumbs: ["shop/", "privacy and policy"] }}>
              <Privacy />
            </Layout>
          </Route>
          <Route path="/">
            <Layout removeHeader>
              <NotFound />
            </Layout>
          </Route>
        </Switch>
      </ThemeProvider>
    </Router >
  );
}

export default App;
