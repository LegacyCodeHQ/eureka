import React from "react";
import "./EdgeBundlingGraph.css";

interface EdgeBundlingGraphProps {
  classJson: any | null
}

const EdgeBundlingGraph: React.FC<EdgeBundlingGraphProps> = ({classJson}) => {
  return (
    <div className="json">
      <code>{classJson}</code>
    </div>
  );
}

export default EdgeBundlingGraph;
