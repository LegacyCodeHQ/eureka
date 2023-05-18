import { GraphData } from '../viz/model/GraphData';
import React from 'react';
import SimpleJvmClassName from './SimpleJvmClassName';
import AppVersion from './AppVersion';
import './Toolbar.css';

interface ToolbarProps {
  data: GraphData | null;
}

const Toolbar: React.FC<ToolbarProps> = ({ data }) => {
  return (
    <div className="toolbar">
      <span className="product-name">TWD</span>
      {data && <SimpleJvmClassName classInfo={data.meta.classInfo} />}
      <AppVersion />
    </div>
  );
};

export default Toolbar;
