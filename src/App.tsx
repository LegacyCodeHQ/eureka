import React from 'react';
import './App.css';
import VocabularyPanel from "./vocabulary/VocabularyPanel";
import EdgeBundlingGraph from "./graph/EdgeBundlingGraph";
import {classJson} from "./SampleData";

function App() {
  return (
    <div className="App">
      <EdgeBundlingGraph classJson={classJson}/>
      <VocabularyPanel/>
    </div>
  );
}

export default App;
