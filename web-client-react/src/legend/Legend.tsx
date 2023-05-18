import React from 'react';
import './Legend.css';

interface LegendProps {
  dependencyCount: number | null;
  dependentCount: number | null;
}

const Legend: React.FC<LegendProps> = ({ dependencyCount, dependentCount }) => {
  return (
    <div className="legend">
      <table className="legend-table">
        <tbody>
          <tr>
            <td>
              <div className="circle red"></div>
            </td>
            <td>Dependencies {formatCount(dependencyCount)}</td>
          </tr>
          <tr>
            <td>
              <div className="circle blue"></div>
            </td>
            <td>Dependents {formatCount(dependentCount)}</td>
          </tr>
        </tbody>
      </table>
    </div>
  );
};

function formatCount(count: number | null): string {
  if (count === 0) {
    return ' (None)';
  } else if (count) {
    return ` (${count})`;
  } else {
    return '';
  }
}

export default Legend;
