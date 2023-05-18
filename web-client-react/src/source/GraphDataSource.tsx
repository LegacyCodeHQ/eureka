import { GraphData } from '../viz/model/GraphData';
import React, { useEffect, useState } from 'react';
import { graphDataJson } from './SampleData';
import { parseGraphData } from '../viz/GraphFunctions';

interface GraphDataSourceProps {
  children: (data: GraphData | null) => React.ReactElement | null;
}

const GraphDataSource: React.FC<GraphDataSourceProps> = (props) => {
  if (process.env.NODE_ENV === 'development') {
    return <GraphDataSourceDev {...props} />;
  }
  return <GraphDataSourceProd {...props} />;
};

const GraphDataSourceDev: React.FC<GraphDataSourceProps> = ({ children }) => {
  const [graphData, setGraphData] = useState<GraphData | null>(null);

  useEffect(() => {
    setGraphData(parseGraphData(graphDataJson));
  }, []);

  return children(graphData) || null;
};

const GraphDataSourceProd: React.FC<GraphDataSourceProps> = ({ children }) => {
  const [graphData, setGraphData] = useState<GraphData | null>(null);
  const wsUrl = 'ws://localhost:7070/structure-updates';

  useEffect(() => {
    const webSocket = new WebSocket(wsUrl);

    webSocket.onopen = () => {
      console.log('Connected to WebSocket server');
    };

    webSocket.onmessage = (event) => {
      console.log(event.data);
      setGraphData(parseGraphData(event.data));
    };

    webSocket.onerror = (error) => {
      console.log(`WebSocket error: ${error}`);
    };

    webSocket.onclose = () => {
      console.log('WebSocket connection closed');
    };

    return () => {
      webSocket?.close();
    };
  }, []);

  return children(graphData) || null;
};

export default GraphDataSource;
