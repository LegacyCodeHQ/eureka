import React from 'react';

import {
  Header,
  HeaderContainer,
  HeaderGlobalAction,
  HeaderGlobalBar,
  HeaderMenuButton,
  HeaderMenuItem,
  HeaderName,
  HeaderSideNavItems,
  SideNav,
  SideNavItems,
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
        <SideNav
          aria-label="Side navigation"
          expanded={isSideNavExpanded}
          isPersistent={false}
        >
          <SideNavItems>
            <HeaderSideNavItems>
              <HeaderMenuItem href="/repos">Repositories</HeaderMenuItem>
            </HeaderSideNavItems>
          </SideNavItems>
        </SideNav>
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
