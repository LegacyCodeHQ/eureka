import { GraphData } from '../viz/model/GraphData';
import React from 'react';
import SimpleJvmClassName from './SimpleJvmClassName';
import AppVersion from './AppVersion';
import './Toolbar.css';
import GitHubProjectLogo from './GitHubProjectLogo';
import AndroidLogo from './AndroidLogo';

interface ToolbarProps {
  data: GraphData | null;
}

const Toolbar: React.FC<ToolbarProps> = ({ data }) => {
  return (
    <div className="toolbar">
      <span className="product-name">TWD</span>
      <AndroidLogo />
      {data && <SimpleJvmClassName classInfo={data.meta.classInfo} />}
      <AppVersion />
      <GitHubProjectLogo account="LegacyCodeHQ" project="tumbleweed" />
    </div>
  );
};

export default Toolbar;
