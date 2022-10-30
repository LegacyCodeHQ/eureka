import React, { Component } from 'react';
import './app.scss';
import { Content, Theme } from '@carbon/react';
import WorkbenchHeader from './components/WorkbenchHeader';
import { Route, Switch } from 'react-router-dom';
import LandingPage from './content/LandingPage';

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
          </Switch>
        </Content>
      </>
    );
  }
}

export default App;
