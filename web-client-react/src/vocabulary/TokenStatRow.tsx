import React from 'react';
import { TokenStat } from './model/TokenStat';
import './TokenStatRow.css';

interface TokenStatRowProps {
  serial: number;
  tokenStat: TokenStat;
  isSelected: boolean;
  onRowClick: (tokenStat: TokenStat) => void;
}

const TokenStatRow: React.FC<TokenStatRowProps> = ({ serial, tokenStat, isSelected, onRowClick }) => {
  return (
    <tr className={`token-stat-row ${isSelected ? 'selected' : ''}`} onClick={() => onRowClick(tokenStat)}>
      <td>
        <div className="row-content">
          <span className="column-serial">{serial}</span>
          <span className="column-name ellipsis">{tokenStat.token.name}</span>
          <span className="column-count number">{tokenStat.count}</span>
        </div>
      </td>
    </tr>
  );
};

export default TokenStatRow;
