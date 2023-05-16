import React from 'react';
import './App.css';
import VocabularyPanel from "./vocabulary/VocabularyPanel";
import EdgeBundlingGraph from "./viz/EdgeBundlingGraph";

function App() {
  return (
    <div className="App">
      <EdgeBundlingGraph data={[10, 5, 15, 20]}/>
      <VocabularyPanel/>
    </div>
  );
}

export default App;
