import React from "react";

interface EdgeBundlingGraphProps {
  classJson: any | null
}

const EdgeBundlingGraph : React.FC<EdgeBundlingGraphProps> = ({classJson}) => {
  return (
    <code>{classJson}</code>
  );
}

export default EdgeBundlingGraph;
