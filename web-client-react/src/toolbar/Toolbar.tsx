import React from 'react';
import SimpleJvmClassName from './SimpleJvmClassName';
import AppVersion from './AppVersion';
import './Toolbar.css';
import GitHubProjectLogo from './GitHubProjectLogo';
import AndroidLogo from './AndroidLogo';
import LiveUpdatesStatus, { WsConnectionStatus } from './LiveUpdatesStatus';
import { ClassInfo } from '../viz/model/ClassInfo';

interface ToolbarProps {
  classInfo: ClassInfo | null;
  connectionStatus: WsConnectionStatus;
}

const Toolbar: React.FC<ToolbarProps> = ({ classInfo, connectionStatus }) => {
  return (
    <div className="toolbar">
      <span className="product-name">EUREKA</span>
      <AndroidLogo />
      {classInfo && <SimpleJvmClassName classInfo={classInfo} />}
      <div className="right-content">
        <LiveUpdatesStatus connectionStatus={connectionStatus} />
        <AppVersion />
        <GitHubProjectLogo account="LegacyCodeHQ" project="eureka" />
      </div>
    </div>
  );
};

export default Toolbar;
