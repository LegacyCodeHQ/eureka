import React, {useState} from "react";
import VocabularyTable from "./VocabularyTable";
import {TokenStat} from "./model/TokenStat";
import "./VocabularyPanel.css"
import {GraphData} from "../viz/Model";
import {vocabulary, vocabularyStats} from "./model/Vocabulary";

interface VocabularyPanelProps {
  data: GraphData;
}

const VocabularyPanel: React.FC<VocabularyPanelProps> = ({data}) => {
  const [activeTab, setActiveTab] = useState("types");
  const [selectedTokenStat, setSelectedTokenStat] = useState<TokenStat | null>(null);
  let {types, words} = vocabularyStats(vocabulary(data));

  const handleStatRowClick = (tokenStat: TokenStat | null) => {
    setSelectedTokenStat(tokenStat);
  };

  function isTabActive(tab: string): boolean {
    return tab === activeTab
  }

  function getTabClass(tab: string): string {
    return `tab ${isTabActive(tab) ? "active" : ""}`
  }

  return (
    <div className="vocabulary-panel">
      <div className="tab-bar">
        <button className={getTabClass("types")} onClick={() => setActiveTab("types")}>
          Types ({Object.keys(types).length})
        </button>
        <button className={getTabClass("words")} onClick={() => setActiveTab("words")}>
          Words ({Object.keys(words).length})
        </button>
      </div>
      <div>
        {
          activeTab === "types" &&
            <VocabularyTable kind="Type" tokenCountMap={types} onStatRowClick={handleStatRowClick}/>
        }
        {
          activeTab === "words" &&
            <VocabularyTable kind="Word" tokenCountMap={words} onStatRowClick={handleStatRowClick}/>
        }
      </div>
      {selectedTokenStat &&
          <p className="debug-element">
              <span><b>Selected</b><br/>{selectedTokenStat.token.name}: {selectedTokenStat.count}</span>
          </p>
      }
    </div>
  );
}

export default VocabularyPanel;
