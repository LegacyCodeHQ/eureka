import React, { Component } from 'react';
import './app.scss';
import { Button, Content, Theme } from '@carbon/react';
import WorkbenchHeader from './components/WorkbenchHeader';

class App extends Component {
  render() {
    return (
      <>
        <Theme theme="g100">
          <WorkbenchHeader/>
        </Theme>
        <Content>
          <Button>Button</Button>
        </Content>
      </>
    );
  }
}

export default App;
