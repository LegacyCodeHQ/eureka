import { GraphData } from '../viz/model/GraphData';
import React, { useEffect, useState } from 'react';
import { graphDataJson } from '../SampleData';
import { parseGraphData } from '../viz/GraphFunctions';


interface GraphDataSourceProps {
  children: (data: GraphData | null) => React.ReactElement | null;
}

const GraphDataSource: React.FC<GraphDataSourceProps> = ({children}) => {
  const [graphData, setGraphData] = useState<GraphData | null>(null);

  useEffect(() => {
    if (process.env.NODE_ENV === 'development') {
      setGraphData(parseGraphData(graphDataJson));
    }
  }, []);

  return children(graphData) || null;
};

export default GraphDataSource;
