import React from 'react';
import './Legend.css';

interface LegendProps {
  dependencies: number | null;
  dependents: number | null;
}

const Legend: React.FC<LegendProps> = ({ dependencies, dependents }) => {
  return (
    <div className="legend">
      <table className="legend-table">
        <tbody>
          <tr>
            <td>
              <div className="circle red"></div>
            </td>
            <td>Dependencies</td>
          </tr>
          <tr>
            <td>
              <div className="circle blue"></div>
            </td>
            <td>Dependents</td>
          </tr>
        </tbody>
      </table>
    </div>
  );
};

export default Legend;
