import { GraphData } from '../viz/model/GraphData';
import React, { useEffect, useState } from 'react';
import { graphDataJson } from '../SampleData';
import { parseGraphData } from '../viz/GraphFunctions';

interface GraphDataSourceProps {
  children: (data: GraphData | null) => React.ReactElement | null;
}

const GraphDataSource: React.FC<GraphDataSourceProps> = ({ children }) => {
  const [graphData, setGraphData] = useState<GraphData | null>(null);
  const wsUrl = 'ws://localhost:7070/structure-updates';
  let webSocket: WebSocket | null = null;

  useEffect(() => {
    if (process.env.NODE_ENV === 'development') {
      setGraphData(parseGraphData(graphDataJson));
    } else {
      const ws = new WebSocket(wsUrl);
      webSocket = ws;

      ws.onopen = () => {
        console.log('Connected to WebSocket server');
      };

      ws.onmessage = (event) => {
        console.log(event.data);
        setGraphData(parseGraphData(event.data));
      };

      ws.onerror = (error) => {
        console.log(`WebSocket error: ${error}`);
      };

      ws.onclose = () => {
        console.log('WebSocket connection closed');
      };
    }

    return () => {
      webSocket?.close();
    };
  }, []);

  return children(graphData) || null;
};

export default GraphDataSource;
