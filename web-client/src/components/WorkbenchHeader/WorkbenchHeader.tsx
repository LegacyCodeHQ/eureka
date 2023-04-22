import React from 'react';

import {
  Header,
  HeaderContainer,
  HeaderGlobalAction,
  HeaderGlobalBar,
  HeaderMenuItem,
  HeaderName,
  HeaderNavigation,
  SkipToContent,
} from '@carbon/react';

import {LogoGithub, Switcher} from '@carbon/react/icons';
import {Link} from 'react-router-dom';

const WorkbenchHeader = () => (
  <HeaderContainer
    render={() => (
      <Header aria-label="Tumbleweed">
        <SkipToContent/>
        <HeaderName element={Link} to="/" prefix="TWD">
          Tumbleweed
        </HeaderName>
        <HeaderNavigation>
          <HeaderMenuItem element={Link} to="/about">About</HeaderMenuItem>
        </HeaderNavigation>
        <HeaderGlobalBar>
          <HeaderGlobalAction aria-label="GitHub" tooltipAlignment="center">
            <LogoGithub size={20} onClick={() => {
              window.open('https://github.com/redgreenio/tumbleweed', '_blank');
            }}/>
          </HeaderGlobalAction>
          <HeaderGlobalAction aria-label="App Switcher" tooltipAlignment="end">
            <Switcher size={20}/>
          </HeaderGlobalAction>
        </HeaderGlobalBar>
      </Header>
    )}
  />
);

export default WorkbenchHeader;
