import React, { useContext, useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { Link } from "react-router-dom";
import AuthContext from "./AuthContext";
import "../css/Main.css";
import CongestionLevelOrderIcon from "../components/CongestionLevelOrder.png";
import Hospmanager from "../components/Hospmanager.png";
import Usermanager from "../components/Usermanager.png";

// Todo: main css 좀 더 만지기

const AppManager = () => {
  const Auth = useContext(AuthContext);
  const [isManager, setisManager] = useState(false);
  const Navigate = useNavigate();

  useEffect(() => {
    if (Auth.getUser().data.role !== "ADMIN") {
        alert("Only the manager has access!!!!");
        Navigate('/Main');
    };
  }, []);

  return (
    <div className="main-container">
      <div className="main-container">
        <Link to="/AppHManager">
          <img className="main-button" src={Hospmanager} alt="병원 매니저" />
        </Link>
        <Link to="/AppUserManager">
          <img className="main-button" src={Usermanager} alt="유저 매니저" />
        </Link>
      </div>
    </div>
  );
};

export default AppManager;
