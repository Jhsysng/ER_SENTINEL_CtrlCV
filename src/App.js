import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Login from './screen/Login';
import Regist from './screen/Regist'; 
import Main from './screen/Main';
import CongestionLevelOrder from './screen/CongestionLevelOrder';
import DistanceOrder from './screen/DistanceOrder'; 
import MapOrder from './screen/MapOrder'; 
import HInfo from './screen/HInfo';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/Regist" element={<Regist />} />
        <Route path="/Main" element={<Main />} />
        <Route path="/CongestionLevelOrder" element={<CongestionLevelOrder />} />
        <Route path="/DistanceOrder" element={<DistanceOrder />} />
        <Route path="/MapOrder" element={<MapOrder />} />
        <Route path="/HInfo/:dutyID" element={<HInfo />} />
      </Routes>
    </Router>
  );
}

export default App;
