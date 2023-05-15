import {TokenStat} from "./model/TokenStat";
import TokenStatRow from "./TokenStatRow";
import React, {useState} from "react";
import "./TokenStatsTable.css"

interface TokenStatsTableProps {
  tokenStats: TokenStat[];
  onStatRowClick: (tokenStat: TokenStat | null) => void;
}

const TokenStatsTable: React.FC<TokenStatsTableProps> = ({tokenStats, onStatRowClick}) => {
  const [selectedTokenStat, setSelectedTokenStat] = useState<TokenStat | null>(null);

  const handleStatRowClick = (tokenStat: TokenStat) => {
    if (selectedTokenStat === tokenStat) {
      setSelectedTokenStat(null);
      onStatRowClick(null);
    } else {
      setSelectedTokenStat(tokenStat);
      onStatRowClick(tokenStat);
    }
  };

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
        <TokenStatRow
          key={tokenStat.token.name}
          serial={index + 1}
          tokenStat={tokenStat}
          isSelected={selectedTokenStat === tokenStat}
          onRowClick={handleStatRowClick}/>
      )}
      </tbody>
    </table>
  )
}

export default TokenStatsTable;
