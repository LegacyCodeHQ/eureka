/* eslint-disable @typescript-eslint/no-explicit-any */
import React, { useEffect, useRef } from 'react';
import * as d3 from 'd3';
import { createRoot, line } from './GraphFunctions';
import { GraphData } from './model/GraphData';
import { NodeHoverEvent } from './NodeHoverEvent';

interface EdgeBundlingGraphProps {
  data: GraphData;
  onNodeHover: (event: NodeHoverEvent | null) => void;
}

interface GroupAngles {
  [group: string]: {
    minAngle: number;
    maxAngle: number;
  };
}

const EdgeBundlingGraph: React.FC<EdgeBundlingGraphProps> = ({ data, onNodeHover }) => {
  const svgRef = useRef<SVGSVGElement | null>(null);
  const width = 800;
  const radius = width / 2;
  const colorSelected = '#000';
  const colorDeselected = '#999';
  const colorIn = '#00f';
  const colorOut = '#f00';
  const colorNone = '#ddd';

  const groupAngles = {} as GroupAngles;
  const groupColors: Record<string, string> = {
    '1': '#0088FF', // Field
    '2': '#0088FF', // Method
    '3': '#32DC80', // Android field
    '4': '#32DC80', // Android method
  };

  function countableTextDependencies(count: number): string {
    if (count === 1) {
      return '1 dependency';
    } else {
      return count + ' dependencies';
    }
  }

  function countableTextDependents(count: number): string {
    if (count === 1) {
      return '1 dependent';
    } else {
      return count + ' dependents';
    }
  }

  function effort(dependencies: number, dependents: number): number {
    return dependencies + dependents / 2;
  }

  function instability(dependencies: number, dependents: number): number {
    const i = dependencies / (dependencies + dependents);
    return Math.round((i + Number.EPSILON) * 100) / 100;
  }

  useEffect(() => {
    if (svgRef.current) {
      const svg = d3.select(svgRef.current);
      svg.selectAll('*').remove();

      const root = createRoot(data, radius);

      svg.attr('viewBox', [-width / 2, -width / 2, width, width]);

      const g = svg.append('g');

      const link = g
        .append('g')
        .attr('stroke', colorNone)
        .attr('fill', 'none')
        .selectAll('path')
        .data(root.leaves().flatMap((leaf: any) => leaf.dependencies))
        .join('path')
        .style('mix-blend-mode', 'multiply')
        .attr('d', ([i, o]) => line(i.path(o)))
        .each(function (d) {
          d.path = this;
        });

      function getNodeIds(nodes: any, index: number): string[] {
        return nodes.map((d: any) => d[index].data.id);
      }

      function countNodes(nodes: any, index: number): number {
        return [...new Set(getNodeIds(nodes, index))].length;
      }

      function countDependencies(d: any): number {
        return countNodes(d.dependencies, 1);
      }

      function countDependents(d: any): number {
        return countNodes(d.dependents, 0);
      }

      /* eslint-disable @typescript-eslint/ban-ts-comment */
      function overed(event: any, d: any) {
        link.style('mix-blend-mode', null);
        d3.select(event.currentTarget).attr('fill', colorSelected);
        d3.selectAll(d.dependents.map((d: any) => d.path))
          .attr('stroke', colorIn)
          .raise();
        // @ts-ignore
        d3.selectAll(d.dependents.map(([d]) => d.text)).attr('fill', colorIn);
        d3.selectAll(d.dependencies.map((d: any) => d.path))
          .attr('stroke', colorOut)
          .raise();
        // @ts-ignore
        d3.selectAll(d.dependencies.map(([, d]) => d.text)).attr('fill', colorOut);

        const hoverEvent: NodeHoverEvent = {
          name: d.data.id,
          count: {
            dependencies: countDependencies(d),
            dependents: countDependents(d),
          },
        };

        onNodeHover(hoverEvent);
      }

      function outed(event: any, d: any) {
        link.style('mix-blend-mode', 'multiply');
        d3.select(event.currentTarget).attr('fill', colorDeselected);
        d3.selectAll(d.dependents.map((d: any) => d.path)).attr('stroke', null);
        // @ts-ignore
        d3.selectAll(d.dependents.map(([d]) => d.text))
          .attr('fill', colorDeselected)
          .attr('font-weight', null);
        d3.selectAll(d.dependencies.map((d: any) => d.path)).attr('stroke', null);
        // @ts-ignore
        d3.selectAll(d.dependencies.map(([, d]) => d.text))
          .attr('fill', colorDeselected)
          .attr('font-weight', null);

        onNodeHover(null);
      }
      /* eslint-enable @typescript-eslint/ban-ts-comment */

      g.append('g')
        .attr('font-family', 'sans-serif')
        .attr('font-size', 10)
        .selectAll('g')
        .data(root.leaves())
        .join('g')
        .attr('transform', (d) => `rotate(${(d.x * 180) / Math.PI - 90}) translate(${d.y},0)`)
        .append('text')
        .attr('fill', colorDeselected)
        .attr('dy', '0.31em')
        .attr('x', (d) => (d.x < Math.PI ? 6 : -6))
        .attr('text-anchor', (d) => (d.x < Math.PI ? 'start' : 'end'))
        .attr('transform', (d) => (d.x >= Math.PI ? 'rotate(180)' : null))
        .text((d) => d.data.id)
        .attr('dy', '0.31em')
        .attr('x', (d) => (d.x < Math.PI ? 6 : -6))
        .attr('text-anchor', (d) => (d.x < Math.PI ? 'start' : 'end'))
        .attr('transform', (d) => (d.x >= Math.PI ? 'rotate(180)' : null))
        .text((d) => d.data.id)
        .each(function (d: any) {
          d.text = this;

          const angle = (d.x * 180) / Math.PI;
          const group = d.data.group;

          if (!groupAngles[group]) {
            groupAngles[group] = { minAngle: angle, maxAngle: angle };
          }
          groupAngles[group].minAngle = Math.min(groupAngles[group].minAngle, angle);
          groupAngles[group].maxAngle = Math.max(groupAngles[group].maxAngle, angle);
        })
        .on('mouseover', overed)
        .on('mouseout', outed)
        .call((text) =>
          text.append('title').text(
            (d: any) => `${d.data.id}
${countableTextDependencies(countDependencies(d))}
${countableTextDependents(countDependents(d))}
Effort* = ${effort(d.dependencies.length, d.dependents.length)}, I = ${
              isNaN(instability(d.dependencies.length, d.dependents.length))
                ? 'N/A'
                : instability(d.dependencies.length, d.dependents.length)
            }`,
          ),
        );

      const zoom = d3
        .zoom()
        .scaleExtent([0.9, 1.2])
        .on('zoom', (event) => {
          g.attr('transform', event.transform);
        });
      svg.call(zoom as any);

      const outerRadius = width / 2.69;
      const thickness = 12;
      const innerRadius = outerRadius - thickness;

      Object.entries(groupAngles).forEach(([group, { minAngle, maxAngle }]) => {
        // Convert the angles from degrees to radians
        const startAngle = minAngle * (Math.PI / 180);
        const endAngle = maxAngle * (Math.PI / 180);

        // Define the arc generator
        const arcGenerator = d3.arc().innerRadius(innerRadius).outerRadius(outerRadius);

        // Append the arc path to the SVG
        g.append('g')
          .append('path')
          .attr(
            'd',
            arcGenerator({
              startAngle: startAngle,
              endAngle: endAngle,
              innerRadius: innerRadius,
              outerRadius: outerRadius,
            }),
          )
          .attr('fill', `${groupColors[group]}C8`)
          .attr('stroke', `${groupColors[group]}FF`)
          .call((path) => {
            function getHint(group: string): string {
              switch (group) {
                case '1':
                  return 'Fields';
                case '2':
                  return 'Methods';
                case '3':
                  return 'Android fields';
                case '4':
                  return 'Android methods';
              }
              throw new Error('Unknown group: ' + group);
            }

            path.append('title').text(getHint(group));
          });
      });
    }
  }, [data]);

  return <svg ref={svgRef} width="100%" height="100vh" />;
};

export default EdgeBundlingGraph;
