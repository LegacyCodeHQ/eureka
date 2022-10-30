import React, { Component } from 'react';
import './app.scss';
import { Content, Theme } from '@carbon/react';
import WorkbenchHeader from './components/WorkbenchHeader';
import { Route, Switch } from 'react-router-dom';
import LandingPage from './content/LandingPage';
import AboutPage from './content/AboutPage';

class App extends Component {
  render() {
    return (
      <>
        <Theme theme="g100">
          <WorkbenchHeader/>
        </Theme>
        <Content>
          <Switch>
            <Route exact path="/" component={LandingPage}/>
            <Route path="/about" component={AboutPage}/>
          </Switch>
        </Content>
      </>
    );
  }
}

export default App;
