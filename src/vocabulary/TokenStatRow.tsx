import React from "react";
import {TokenStat} from "./model/TokenStat";
import "./TokenStatRow.css"

interface TokenStatRowProps {
  serial: number;
  tokenStat: TokenStat;
  isSelected: boolean;
  onRowClick: (tokenStat: TokenStat) => void;
}

const TokenStatRow: React.FC<TokenStatRowProps> = ({serial, tokenStat, isSelected, onRowClick}) => {
  return (
    <tr className={`token-stat-row ${isSelected ? "selected" : ""}`} onClick={() => onRowClick(tokenStat)}>
      <td className="serial">{serial}</td>
      <td className="name">{tokenStat.token.name}</td>
      <td className="frequency">{tokenStat.frequency}</td>
    </tr>
  )
}

export default TokenStatRow;
