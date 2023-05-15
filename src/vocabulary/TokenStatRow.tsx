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
      <td>{serialNumber}</td>
      <td>{tokenStat.token.name}</td>
      <td>{tokenStat.frequency}</td>
    </tr>
  )
}

export default TokenStatRow
