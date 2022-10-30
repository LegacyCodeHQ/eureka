import React, { Component } from 'react';
import './app.scss';
import { Button, Content } from '@carbon/react';
import WorkbenchHeader from './components/WorkbenchHeader';

class App extends Component {
  render() {
    return (
      <>
        <WorkbenchHeader />
        <Content>
          <Button>Button</Button>
        </Content>
      </>
    );
  }
}

export default App;
