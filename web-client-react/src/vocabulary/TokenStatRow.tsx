import React from 'react';
import { TokenStat } from './model/TokenStat';
import './TokenStatRow.css';

interface TokenStatRowProps {
  serial: number;
  tokenStat: TokenStat;
  isSelected: boolean;
  onRowClick: (tokenStat: TokenStat) => void;
}

const TokenStatRow: React.FC<TokenStatRowProps> = ({serial, tokenStat, isSelected, onRowClick}) => {
  return (
    <tr className={`token-stat-row ${isSelected ? "selected" : ""}`} onClick={() => onRowClick(tokenStat)}>
      <td className="column-serial">{serial}</td>
      <td className="column-name ellipsis">{tokenStat.token.name}</td>
      <td className="column-count number">{tokenStat.count}</td>
    </tr>
  )
}

export default TokenStatRow;
