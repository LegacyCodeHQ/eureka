import React, {useState} from "react";
import VocabularyTable from "./VocabularyTable";
import {TokenStat} from "./model/TokenStat";
import {typeTokenStats, wordTokenStats} from "../SampleData";
import "./VocabularyPanel.css"

const VocabularyPanel = () => {
  const [activeTab, setActiveTab] = useState("types");
  const [selectedTokenStat, setSelectedTokenStat] = useState<TokenStat | null>(null);

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
          Types ({typeTokenStats.length})
        </button>
        <button className={getTabClass("words")} onClick={() => setActiveTab("words")}>
          Words ({wordTokenStats.length})
        </button>
      </div>
      <div>
        {
          activeTab === "types" &&
            <VocabularyTable kind="Type" tokenStats={typeTokenStats} onStatRowClick={handleStatRowClick}/>
        }
        {
          activeTab === "words" &&
            <VocabularyTable kind="Word" tokenStats={wordTokenStats} onStatRowClick={handleStatRowClick}/>
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
