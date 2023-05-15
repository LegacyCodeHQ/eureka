import React, {useEffect, useRef} from "react";
import * as d3 from "d3";
import "./EdgeBundlingGraph.css";

interface EdgeBundlingGraphProps {
  data: number[];
}

const EdgeBundlingGraph: React.FC<EdgeBundlingGraphProps> = ({data}) => {
  const svgRef = useRef<SVGSVGElement | null>(null);
  const dimension = 200;

  useEffect(() => {
    if (svgRef.current) {
      const svg = d3.select(svgRef.current);

      svg.selectAll("*").remove();

      svg.selectAll("rect")
        .data(data)
        .enter()
        .append("rect")
        .attr("x", (d, i) => i * 45)
        .attr("y", d => dimension - 10 * d)
        .attr("width", 40)
        .attr("height", d => d * 10)
        .attr("fill", "lightblue");
    }
  }, [data]);

  return <svg ref={svgRef} width={dimension} height={dimension}/>;
}

export default EdgeBundlingGraph;
