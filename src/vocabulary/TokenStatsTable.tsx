import {TokenStat} from "./model/TokenStat";
import TokenStatRow from "./TokenStatRow";
import React from "react";

interface TokenStatsTableProps {
  tokenStats: TokenStat[];
}

const TokenStatsTable: React.FC<TokenStatsTableProps> = ({tokenStats}) => {
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
        <TokenStatRow serial={index + 1} tokenStat={tokenStat}/>
      )}
      </tbody>
    </table>
  )
}

export default TokenStatsTable;
