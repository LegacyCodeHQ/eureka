import React from 'react';
import './LiveUpdatesStatus.css';

interface LiveUpdatesStatusProps {
  connectionStatus: WsConnectionStatus;
}

export enum WsConnectionStatus {
  Connected,
  Disconnected,
}

const statusToClassname = {
  [WsConnectionStatus.Connected]: 'status-led connected',
  [WsConnectionStatus.Disconnected]: 'status-led disconnected',
};

const LiveUpdatesStatus: React.FC<LiveUpdatesStatusProps> = ({ connectionStatus }) => {
  const className = statusToClassname[connectionStatus];
  return <div className={className} title={WsConnectionStatus[connectionStatus]}></div>;
};

export default LiveUpdatesStatus;
