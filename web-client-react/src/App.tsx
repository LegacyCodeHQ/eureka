import React from 'react';
import './App.css';
import VocabularyPanel from './vocabulary/VocabularyPanel';
import EdgeBundlingGraph from './viz/EdgeBundlingGraph';
import GraphDataSource from './source/GraphDataSource';
import { GraphData } from './viz/model/GraphData';
import SimpleJvmClassName from './SimpleJvmClassName';

function App() {
  return (
    <GraphDataSource>
      {(data: GraphData | null) => (
        <div>
          <div className="toolbar">
            <span className="product-name">TWD</span>
            {data && <SimpleJvmClassName classInfo={data.meta.classInfo} />}
          </div>
          <div className="App">
            <div className="viz">{data && <EdgeBundlingGraph data={data} />}</div>
            {data && <VocabularyPanel data={data} />}
          </div>
        </div>
      )}
    </GraphDataSource>
  );
}

export default App;
