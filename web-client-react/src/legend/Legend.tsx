import React from 'react';
import './Legend.css';
import { Count } from '../viz/NodeHoverEvent';

interface LegendProps {
  count: Count | null;
}

const Legend: React.FC<LegendProps> = ({ count }) => {
  return (
    <div className="legend">
      <table className="legend-table">
        <tbody>
          <tr>
            <td>
              <div className="circle red"></div>
            </td>
            <td>Needs {formatCount(count ? count.dependencies : null)}</td>
          </tr>
          <tr>
            <td>
              <div className="circle blue"></div>
            </td>
            <td>Used by {formatCount(count ? count.dependents : null)}</td>
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
