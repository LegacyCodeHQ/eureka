import React from "react";
import {TokenStat} from "./model/TokenStat";
import "./TokenStatRow.css"

interface TokenStatRowProps {
  serialNumber: number;
  tokenStat: TokenStat;
}

const TokenStatRow: React.FC<TokenStatRowProps> = ({serialNumber, tokenStat}) => {
  return (
    <tr>
      <td className="serial">{serialNumber}</td>
      <td className="name">{tokenStat.token.name}</td>
      <td className="frequency">{tokenStat.frequency}</td>
    </tr>
  )
}

export default TokenStatRow;
