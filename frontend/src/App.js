import { ThemeProvider } from '@material-ui/styles';
import theme from "util/style/theme";
import {
  BrowserRouter as Router,
  Switch,
  Route
} from "react-router-dom";
import Layout from 'components/layout/layout';
import Terms from 'static_pages/terms/terms';
import Privacy from 'static_pages/privacy/privacy';
import NotFound from 'static_pages/not_found/notFound';
import About from 'static_pages/about/about';
import Register from 'components/forms/register/register';
import Login from 'components/forms/login/login';
import Home from 'components/home/home';


function App() {
  return (
    <Router>
      <ThemeProvider theme={theme}>
        <Switch>
          <Route exact path="/about">
            <Layout breadcrumbs={{ current: "about us", breadcrumbs: ["shop/", "about"] }}>
              <About />
            </Layout>
          </Route>
          <Route exact path="/terms">
            <Layout breadcrumbs={{ current: "terms and conditions", breadcrumbs: ["shop/", "terms and conditions"] }}>
              <Terms />
            </Layout>
          </Route>
          <Route exact path="/privacy">
            <Layout breadcrumbs={{ current: "privacy and policy", breadcrumbs: ["shop/", "privacy and policy"] }}>
              <Privacy />
            </Layout>
          </Route>
          <Route exact path="/register">
            <Layout breadcrumbs={{ current: "register" }}>
              <Register />
            </Layout>
          </Route>
          <Route exact path="/login">
            <Layout breadcrumbs={{ current: "login" }}>
              <Login />
            </Layout>
          </Route>
          <Route exact path={["/home", "/"]}>
            <Layout>
              <Home />
            </Layout>
          </Route>
          <Route path="/**">
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
