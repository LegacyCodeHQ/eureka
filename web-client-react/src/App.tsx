import React from 'react';
import './App.css';
import VocabularyPanel from './vocabulary/VocabularyPanel';
import EdgeBundlingGraph from './viz/EdgeBundlingGraph';
import GraphDataSource from './source/GraphDataSource';
import { GraphData } from './viz/model/GraphData';

function App() {
  return (
    <div>
      <div className="toolbar">
        <span className="product-name">TWD</span>
        <span className="class-name">StoryViewerFragment</span>
      </div>
      <GraphDataSource>
        {(data: GraphData | null) => (
          <div className="App">
            <div className="viz">{data && <EdgeBundlingGraph data={data} />}</div>
            {data && <VocabularyPanel data={data} />}
          </div>
        )}
      </GraphDataSource>
    </div>
  );
}

export default App;
