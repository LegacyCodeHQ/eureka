import {GraphData} from "../viz/model/GraphData";
import React, {ReactNode} from "react";
import {graphDataJson} from "../SampleData";
import {parseGraphData} from "../viz/GraphFunctions";

interface GraphDataSourceProps {
  children: (data: GraphData) => ReactNode;
}

class GraphDataSource extends React.Component<GraphDataSourceProps> {
  render() {
    let graphData = parseGraphData(graphDataJson);
    return this.props.children(graphData);
  }
}

export default GraphDataSource;
