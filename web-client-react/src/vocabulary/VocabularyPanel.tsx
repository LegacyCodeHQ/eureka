import React, { useState } from 'react';
import VocabularyTable from './VocabularyTable';
import { TokenStat } from './model/TokenStat';
import './VocabularyPanel.css';
import { vocabulary, vocabularyStats } from './model/Vocabulary';
import { Token } from './model/Token';
import { GraphData } from '../viz/model/GraphData';

interface VocabularyPanelProps {
  data: GraphData;
}

const VocabularyPanel: React.FC<VocabularyPanelProps> = ({ data }) => {
  const [activeTab, setActiveTab] = useState('types');
  const [selectedTokenStat, setSelectedTokenStat] = useState<TokenStat | null>(null);
  const { types, words } = vocabularyStats(vocabulary(data));
  const typeTokenStats = Object.keys(types).map((name: string) => new TokenStat(new Token(name), types[name]));
  const wordTokenStats = Object.keys(words).map((name: string) => new TokenStat(new Token(name), words[name]));

  const handleStatRowClick = (tokenStat: TokenStat | null) => {
    setSelectedTokenStat(tokenStat);
  };

  function isTabActive(tab: string): boolean {
    return tab === activeTab;
  }

  function getTabClass(tab: string): string {
    return `tab ${isTabActive(tab) ? 'active' : ''}`;
  }

  return (
    <div className="vocabulary-panel">
      <div className="tab-bar">
        <button className={getTabClass('types')} onClick={() => setActiveTab('types')}>
          Types ({typeTokenStats.length})
        </button>
        <button className={getTabClass('words')} onClick={() => setActiveTab('words')}>
          Words ({wordTokenStats.length})
        </button>
      </div>
      {activeTab === 'types' && (
        <VocabularyTable kind="Type" tokenStats={typeTokenStats} onStatRowClick={handleStatRowClick} />
      )}
      {activeTab === 'words' && (
        <VocabularyTable kind="Word" tokenStats={wordTokenStats} onStatRowClick={handleStatRowClick} />
      )}
    </div>
  );
};

export default VocabularyPanel;
