import React from 'react';
import SimpleJvmClassName from './SimpleJvmClassName';
import AppVersion from './AppVersion';
import './Toolbar.css';
import GitHubProjectLogo from './GitHubProjectLogo';
import AndroidLogo from './AndroidLogo';
import LiveUpdatesStatus, { WsConnectionStatus } from './LiveUpdatesStatus';
import { ClassInfo } from '../viz/model/ClassInfo';
import { ClassStats } from '../viz/model/GraphData';

interface ToolbarProps {
  classInfo: ClassInfo | null;
  classStats: ClassStats;
  connectionStatus: WsConnectionStatus;
}

const Toolbar: React.FC<ToolbarProps> = ({ classInfo, classStats, connectionStatus }) => {
  function summary(classStats: ClassStats): string {
    return (
      '\u00A0• ' +
      classStats.fieldCount +
      ' fields, ' +
      classStats.methodCount +
      ' methods, and ' +
      classStats.relationshipsCount +
      ' relationships'
    );
  }

  return (
    <div className="toolbar">
      <span className="product-name">EUREKA</span>
      <AndroidLogo />
      {classInfo && <SimpleJvmClassName classInfo={classInfo} />}
      <div className="class-stats">{summary(classStats)}</div>
      <div className="right-content">
        <LiveUpdatesStatus connectionStatus={connectionStatus} />
        <AppVersion />
        <GitHubProjectLogo account="LegacyCodeHQ" project="eureka" />
      </div>
    </div>
  );
};

export default Toolbar;
