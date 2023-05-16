import {TokenStat} from "./model/TokenStat";
import TokenStatRow from "./TokenStatRow";
import React, {useState} from "react";
import "./VocabularyTable.css"
import {Token} from "./model/Token";

interface TokenStatsTableProps {
  kind: string;
  tokenCountMap: any;
  onStatRowClick: (tokenStat: TokenStat | null) => void;
}

const VocabularyTable: React.FC<TokenStatsTableProps> = ({kind, tokenCountMap, onStatRowClick}) => {
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
    <div className="table-container">
      <table>
        <thead>
        <tr>
          <th className="column-serial">#</th>
          <th className="column-name">{kind}</th>
          <th className="column-count">Count</th>
        </tr>
        </thead>
      </table>
      <div className="table-body-container">
        <table>
          <tbody>
          {Object.keys(tokenCountMap).map((token, index) =>
            <TokenStatRow
              key={token}
              serial={index + 1}
              tokenStat={new TokenStat(new Token(token), tokenCountMap[token])}
              isSelected={selectedTokenStat?.token.name === token}
              onRowClick={handleStatRowClick}/>
          )}
          </tbody>
        </table>
      </div>
    </div>
  )
}

export default VocabularyTable;
