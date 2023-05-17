import { TokenStat } from './model/TokenStat';
import TokenStatRow from './TokenStatRow';
import React, { useState } from 'react';
import './VocabularyTable.css';

interface TokenStatsTableProps {
  kind: string;
  tokenStats: TokenStat[];
  onStatRowClick: (tokenStat: TokenStat | null) => void;
}

const VocabularyTable: React.FC<TokenStatsTableProps> = ({ kind, tokenStats, onStatRowClick }) => {
  const [selectedTokenStat, setSelectedTokenStat] = useState<TokenStat | null>(null);

  const handleStatRowClick = (tokenStat: TokenStat) => {
    const deselect = tokenStat.isEqual(selectedTokenStat);

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
            {tokenStats.map((tokenStat, index) => (
              <TokenStatRow
                key={tokenStat.token.name}
                serial={index + 1}
                tokenStat={tokenStat}
                isSelected={tokenStat.isEqual(selectedTokenStat)}
                onRowClick={handleStatRowClick}
              />
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default VocabularyTable;
