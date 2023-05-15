import React from "react";
import {TokenStat} from "./model/TokenStat";
import "./TokenStatRow.css"

interface TokenStatRowProps {
  serial: number;
  tokenStat: TokenStat;
}

const TokenStatRow: React.FC<TokenStatRowProps> = ({serial, tokenStat}) => {
  return (
    <tr className="token-stat-row">
      <td className="serial">{serial}</td>
      <td className="name">{tokenStat.token.name}</td>
      <td className="frequency">{tokenStat.frequency}</td>
    </tr>
  )
}

export default TokenStatRow;
