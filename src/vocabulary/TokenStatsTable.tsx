import {TokenStat} from "./model/TokenStat";
import TokenStatRow from "./TokenStatRow";
import React from "react";
import "./TokenStatsTable.css"

interface TokenStatsTableProps {
  tokenStats: TokenStat[];
  onStatRowClick: (tokenStat: TokenStat) => void;
}

const TokenStatsTable: React.FC<TokenStatsTableProps> = ({tokenStats, onStatRowClick}) => {
  return (
    <table>
      <thead>
      <tr>
        <th>#</th>
        <th>Type</th>
        <th>Freq.</th>
      </tr>
      </thead>
      <tbody>
      {tokenStats.map((tokenStat, index) =>
        <TokenStatRow key={tokenStat.token.name} serial={index + 1} tokenStat={tokenStat} onRowClick={onStatRowClick}/>
      )}
      </tbody>
    </table>
  )
}

export default TokenStatsTable;
