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
    let deselect = selectedTokenStat === tokenStat;
    if (deselect) {
      setSelectedTokenStat(null);
      onStatRowClick(null);
    } else {
      setSelectedTokenStat(tokenStat);
      onStatRowClick(tokenStat);
    }
  };

  return (
    <div className="stats-container">
      <table>
        <thead>
        <tr>
          <th className="column-serial">#</th>
          <th className="column-name">Type</th>
          <th className="column-frequency">Freq.</th>
        </tr>
        </thead>
      </table>
      <div className="table-container">
        <table>
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
      </div>
    </div>
  )
}

export default TokenStatsTable;
