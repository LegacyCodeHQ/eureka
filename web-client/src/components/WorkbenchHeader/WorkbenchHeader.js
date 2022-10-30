import React from 'react';

import {
  Header,
  HeaderContainer,
  HeaderGlobalAction,
  HeaderGlobalBar,
  HeaderMenuButton,
  HeaderName,
  SkipToContent,
} from '@carbon/react';

import { Notification, Switcher } from '@carbon/react/icons';

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
        <HeaderName prefix="TWD">
          Tumbleweed
        </HeaderName>
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
