import { TokenStat } from './model/TokenStat';
import TokenStatRow from './TokenStatRow';
import React, { useEffect, useRef, useState } from 'react';

interface VocabularyTableBodyProps {
  tokenStats: TokenStat[];
  selectedTokenStat: TokenStat | null;
  onStatRowClick: (tokenStat: TokenStat) => void;
}

const VocabularyTableBody: React.FC<VocabularyTableBodyProps> = ({ tokenStats, selectedTokenStat, onStatRowClick }) => {
  const containerRef = useRef<HTMLDivElement>(null);
  const [containerHeight, setContainerHeight] = useState(0);

  useEffect(() => {
    function updateHeight() {
      if (containerRef.current) {
        const rect = containerRef.current.getBoundingClientRect();
        setContainerHeight(window.innerHeight - rect.top);
      }
    }

    updateHeight();
    window.addEventListener('resize', updateHeight);

    return () => {
      window.removeEventListener('resize', updateHeight);
    };
  }, []);

  return (
    <div ref={containerRef} style={{ height: containerHeight, overflowY: 'auto' }}>
      <table className="scrollable-table">
        <tbody>
          {tokenStats.map((tokenStat, index) => (
            <TokenStatRow
              key={tokenStat.token.name}
              serial={index + 1}
              tokenStat={tokenStat}
              isSelected={tokenStat.isEqual(selectedTokenStat)}
              onRowClick={onStatRowClick}
            />
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default VocabularyTableBody;
