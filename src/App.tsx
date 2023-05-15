import React, {useState} from 'react';
import './App.css';
import {Token} from "./vocabulary/model/Token";
import {TokenStat} from "./vocabulary/model/TokenStat";
import TokenStatsTable from "./vocabulary/TokenStatsTable";
import VocabularyPanel from "./vocabulary/VocabularyPanel";

function App() {
  return (
    <div className="App">
      <VocabularyPanel/>
    </div>
  );
}

export default App;
