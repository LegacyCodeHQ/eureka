import React from 'react';

import {
  Header,
  HeaderContainer,
  HeaderGlobalAction,
  HeaderGlobalBar,
  HeaderMenuButton,
  HeaderMenuItem,
  HeaderName,
  HeaderNavigation,
  SkipToContent,
} from '@carbon/react';

import { Notification, Switcher } from '@carbon/react/icons';
import { Link } from 'react-router-dom';

const WorkbenchHeader = () => (
  <HeaderContainer
    render={({
      isSideNavExpanded,
      onClickSideNavExpand
    }) => (
      <Header aria-label="Tumbleweed">
        <SkipToContent/>
        <HeaderMenuButton
          aria-label="Open menu"
          onClick={onClickSideNavExpand}
          isActive={isSideNavExpanded}
        />
        <HeaderName element={Link} to="/" prefix="TWD">
          Tumbleweed
        </HeaderName>
        <HeaderNavigation>
          <HeaderMenuItem element={Link} to="/about">About</HeaderMenuItem>
        </HeaderNavigation>
        <HeaderGlobalBar>
          <HeaderGlobalAction aria-label="Notifications" tooltipAlignment="center">
            <Notification size={20}/>
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
