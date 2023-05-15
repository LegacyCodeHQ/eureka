import React from 'react';
import './App.css';
import VocabularyPanel from "./vocabulary/VocabularyPanel";
import EdgeBundlingGraph from "./graph/EdgeBundlingGraph";

function App() {
  return (
    <div className="App">
      <EdgeBundlingGraph classJson='{ "name": "Tumbleweed" }'/>
      <VocabularyPanel/>
    </div>
  );
}

export default App;
