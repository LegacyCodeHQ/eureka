import { GraphData } from '../viz/model/GraphData';
import React, { useEffect, useState } from 'react';
import { graphDataJson } from './SampleData';
import { parseGraphData } from '../viz/GraphFunctions';
import { useHost } from '../HostContext';
import { WsConnectionStatus } from '../toolbar/LiveUpdatesStatus';

interface GraphDataSourceProps {
  children: (data: GraphData | null) => React.ReactElement | null;
  onConnectionStatusChange: (connectionStatus: WsConnectionStatus) => void;
}

const GraphDataSource: React.FC<GraphDataSourceProps> = (props) => {
  if (process.env.NODE_ENV === 'development') {
    return <GraphDataSourceDev {...props} />;
  }
  return <GraphDataSourceProd {...props} />;
};

const GraphDataSourceDev: React.FC<GraphDataSourceProps> = ({ children, onConnectionStatusChange }) => {
  const [graphData, setGraphData] = useState<GraphData | null>(null);

  useEffect(() => {
    setGraphData(parseGraphData(graphDataJson));
    onConnectionStatusChange(WsConnectionStatus.Connected);
  }, []);

  return children(graphData) || null;
};

const GraphDataSourceProd: React.FC<GraphDataSourceProps> = ({ children, onConnectionStatusChange }) => {
  const [graphData, setGraphData] = useState<GraphData | null>(null);
  const wsUrl = useHost().resolveWs('/structure-updates');

  useEffect(() => {
    const webSocket = new WebSocket(wsUrl);

    webSocket.onopen = () => {
      console.log('Connected to WebSocket server');
      onConnectionStatusChange(WsConnectionStatus.Connected);
    };

    webSocket.onmessage = (event) => {
      setGraphData(parseGraphData(event.data));
    };

    webSocket.onerror = (error) => {
      console.log(`WebSocket error: ${error}`);
      onConnectionStatusChange(WsConnectionStatus.Disconnected);
    };

    webSocket.onclose = () => {
      console.log('WebSocket connection closed');
      onConnectionStatusChange(WsConnectionStatus.Disconnected);
    };

    return () => {
      webSocket?.close();
    };
  }, []);

  return children(graphData) || null;
};

export default GraphDataSource;
