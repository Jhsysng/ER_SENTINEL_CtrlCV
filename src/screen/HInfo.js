import React, {useContext, useEffect, useState} from "react";
import {useParams} from "react-router-dom";
import {useLocation} from 'react-router-dom';
import {Cookies} from "react-cookie";
import {parseJwt} from "../utils/JwtUtils";
import {config} from "../utils/Constants";
import axios from "axios";
import "../css/HInfo.css";
import "../utils/ApiUtils.js";
import hospitalbed from "../components/hospitalbed.png";
import reset from "../components/reset.png";
import choose from "../components/choose.png";
import Location from "../components/location.png";
import call from "../components/call.png";
import greenHInfo from "../components/greenHInfo.png";
import yellowHInfo from "../components/yellowHInfo.png";
import orangeHInfo from "../components/orangeHInfo.png";
import redHInfo from "../components/redHInfo.png";
import star from "../components/star.png";
import AuthContext from "./AuthContext";
import X from "../components/X.png";

const getCongestionImage = (congestionValue) => {
    const v = parseInt(congestionValue)
    if(v < 0) return X;
    else if (v < 50) {
        return greenHInfo;
    } else if (v < 100) {
        return yellowHInfo;
    } else if (v < 150) {
        return orangeHInfo;
    } else {
        return redHInfo;
    }
};

const HInfo = () => {
    // AuthContext : 전역적인 변수.
    // Auth.getUser().accessToken : 토큰을 가져오는 것
    // header에 Authorization: Bearer {accessToken} 이거 넣어주기.

    const Auth = useContext(AuthContext);
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const location = useLocation();
    const dutyId = location.state.dutyId;

    // Todo : 나중에 백 연결하고 이거 주석 풀기
    const [Ahospital, setAHospital] = useState(null); // 혼잡도
    const [Bhospital, setBHospital] = useState(null); //
    const [Chospital, setCHospital] = useState(null);
    const [Dhospital, setDHospital] = useState(null);
    const [Ehospital, setEHospital] = useState(null);
    const [Star, setStar] = useState(null);
    const [ID, setID] = useState(null);
    let count = 0;

    useEffect(() => {
        const fetchHospitalData = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/hospitaldetail/congestioninfo`, {
                    params: {
                        dutyId: dutyId,
                    }
                });
                console.log("setAHospital");
                setAHospital(response.data.data[0]);
            } catch (error) {
                console.error("Failed to fetch hospital data", error);
            }
        };
        const timeoutId = setTimeout(fetchHospitalData, 2000);
        return () => clearTimeout(timeoutId);
    }, [dutyId]);

    useEffect(() => {
        const fetchHospitalData = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/hospitaldetail/hospitalequipment`, {
                    params: {
                        dutyId: dutyId,
                    }
                });
                console.log("setBHospital");
                setBHospital(response.data.data[0]);
            } catch (error) {
                console.error("Failed to fetch hospital data", error);
            }
        };
        const timeoutId = setTimeout(fetchHospitalData, 3000);
        return () => clearTimeout(timeoutId);
    }, [dutyId]);

    useEffect(() => {
        const fetchHospitalData = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/hospitaldetail/emergencyroomseverecapacityinfo`, {
                    params: {
                        dutyId: dutyId,
                    }
                });
                console.log("setCHospital");
                setCHospital(response.data.data[0]);
            } catch (error) {
                console.error("Failed to fetch hospital data", error);
            }
        };
        const timeoutId = setTimeout(fetchHospitalData, 4000);
        return () => clearTimeout(timeoutId);
    }, [dutyId]);

    useEffect(() => {
        const fetchHospitalData = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/hospitaldetail/roadmap`, {
                    params: {
                        dutyId: dutyId,
                    }
                });
                setDHospital(response.data.data[0]);
                console.log("setDHospital");
            } catch (error) {
                console.error("Failed to fetch hospital data", error);
            }
        };
        const timeoutId = setTimeout(fetchHospitalData, 5000);
        return () => clearTimeout(timeoutId);
    }, [dutyId]);

    // useEffect(() => {
    //     const fetchHospitalData = async () => {
    //         try {
    //             const response = await axios.get(`http://localhost:8080/hospitaldetail/emergencymessage`, {
    //                 params: {
    //                     dutyId: dutyId,
    //                 }
    //             });
    //             setEHospital(response.data.data);
    //         } catch (error) {
    //             console.error("Failed to fetch hospital data", error);
    //         }
    //     };
    //
    //     const timeoutId = setTimeout(fetchHospitalData, 3000);
    //     return () => clearTimeout(timeoutId);
    //     Eflag = true;
    // }, [dutyId]);

    // useEffect(() => {
    //   const fetchHospitalData = async () => {
    //     try {
    //       const response = await axios.get(`http://localhost:8080/hospitaldetail/emergencymessage`, {
    //         params: {
    //           dutyId: dutyId,
    //         }
    //       });
    //       if(!response.data.error) {setEHospital(response.data.data);
    //           console.log("setEHospital");}
    //       else {setEHospital({"data" : {
    //         "error": true,
    //         "data": [
    //           {
    //             "emgMessage": "메세지가 없습니다!",
    //             "emgMsgType": "메세지가 없습니다!",
    //             "lasttime": "메세지가 없습니다!"
    //           },
    //         ]
    //       }});}
    //     } catch (error) {
    //       console.error("Failed to fetch hospital data", error);
    //     }
    //   };
    //     const timeoutId = setTimeout(fetchHospitalData, 40000);
    //     return () => clearTimeout(timeoutId);
    // }, []);

    // survey 띄우기
    useEffect(() => {
    setIsLoggedIn(Auth.userIsAuthenticated());
      const fetchHospitalData = async () => {
        try {
            const response = await axios.get(`http://localhost:8080/survey`, {
                params: {
                    dutyId: dutyId,
                }

            });
          setEHospital(response.data.data);
          console.log("setEHospital");
          console.log(response);
          // else {setEHospital({"data" : {}});} // error 처리
        } catch (error) {
          console.error("Failed to fetch hospital data", error);
        }
      };
      const timeoutId = setTimeout(fetchHospitalData, 5000);
      return () => clearTimeout(timeoutId);
    }, []);

    // survey 만들기
    const handlesurvey = async (index) => {
        if(star == null) {
            alert("별점을 적어주세요!");
            return;
        }
        console.log(Auth.getUser().accessToken);
        console.log(dutyId);
        try {
            const response = await axios.post(`http://localhost:8080/survey`, {

                    dutyId: dutyId,
                    star: Star,
                    shortMessage: "test",

            }, {
                headers: {
                    "Authorization" : `Bearer ${Auth.getUser().accessToken}`,
                }
            });
            setID(response.data.data[0].id);
        } catch (error) {
            console.error("error make survey", error);
        }
    };

    const deletesurvey = async () => {
        if(star == null) {
            alert("별점을 적어주세요!");
            return;
        }
        console.log(ID);
        try {
            const response = await axios.post(`http://localhost:8080/survey/d`, {
                dutyId: dutyId,
                star: Star,
                shortMessage: "test",
            }, {
                headers: {
                    "Authorization" : `Bearer ${Auth.getUser().accessToken}`,
                }
            });
        } catch (error) {
            console.error("error make survey", error);
        }
    };

    if (!Ahospital || !Bhospital || !Chospital || !Dhospital || !Ehospital) {
        return <div>병원 정보를 갱신 중입니다!</div>;
    }

    return (
        <div className="HInfo-hospital-details-container">
            {Ahospital && Bhospital && Chospital && Dhospital && Ehospital ? (
                <>
                    <h1 className="HInfo-hospital-name">{Dhospital.name}</h1>
                <div className="HInfo-hospital-info-section HInfo-emergency-section">
                    <div className="HInfo-emergency-header">
                        <div className="HInfo-title-container">
                            <h4 className="HInfo-emergency-title">
                                현재 응급실 혼잡도(성인 / 소아)
                            </h4>
                            <span className="HInfo-subtitle">
              현재 환자 수 대비 남은 병실 수 기준입니다.
            </span>
                        </div>
                        <div className="HInfo-header-right">
                            <img
                                src={hospitalbed}
                                alt="Bed"
                                className="HInfo-hospitalbed-icon"
                            />
                        </div>
                    </div>
                    <div className="HInfo-congestion-container">
                        <div className="HInfo-emergency-content">
                            <span className="HInfo-emergency-label">성인 진료</span>
                            <img
                                src={getCongestionImage(Ahospital.adultpercent)}
                                alt="Adult Congestion"
                                className="HInfo-emergency-icon"
                            />
                            <span className="HInfo-congestion-text">
              성인: {JSON.stringify(Ahospital.adultpercent)}
            </span>
                        </div>
                        <div className="HInfo-emergency-content">
                            <span className="HInfo-emergency-label">소아 진료</span>
                            <img
                                src={getCongestionImage(Ahospital.pediatricpercent)}
                                alt="Pediatric Congestion"
                                className="HInfo-emergency-icon"
                            />
                            <span className="HInfo-congestion-text">
              소아: {JSON.stringify(Ahospital.pediatricpercent)}
            </span>
                        </div>
                    </div>
                </div>

                <div className="HInfo-hospital-info-section HInfo-procedure-section">
                <div className="HInfo-procedure-header">
                <div className="HInfo-title-container">
                <h4 className="HInfo-procedure-title">실시간 가용장비 정보</h4>
                <span className="HInfo-subtitle">
                해당 정보는 10분마다 갱신됩니다.
                </span>
                <span className="HInfo-update-time">
                갱신시각: {JSON.stringify(Bhospital.updateTime)}
                </span>
                </div>
                <div className="HInfo-header-right">
                <img src={reset} alt="Update" className="HInfo-reset-icon" />
                </div>
                </div>
                <div className="HInfo-congestion-container">
                <div className="HInfo-procedure-content">
                <div className="HInfo-procedure-list-container">
                <ul className="HInfo-procedure-list-left">
            {Bhospital.eqlist.map((data, index) => (
                <li key={index} className="HInfo-procedure-item">
            {data}
                </li>
                ))}
                </ul>
                </div>
                </div>
                </div>
                </div>

                <div className="HInfo-hospital-info-section HInfo-procedure-section">
                <div className="HInfo-procedure-header">
                <div className="HInfo-title-container">
                <h4 className="HInfo-procedure-title">
                중증 질환별 수용 가능 여부
                </h4>
                <span className="HInfo-subtitle">
                해당 정보는 20분마다 갱신됩니다.
                </span>
                <span className="HInfo-update-time">
                갱신시각: {JSON.stringify(Chospital.updateTime)}
                </span>
                </div>
                <div className="HInfo-header-right">
                <img src={reset} alt="Update" className="HInfo-reset-icon" />
                </div>
                </div>
                <div className="HInfo-congestion-container">
                <div className="HInfo-procedure-content">
                <div className="HInfo-procedure-list-container">
                <ul className="HInfo-procedure-list-left">
            {Chospital.ersclist.map((data, index) => (
                <li key={index} className="HInfo-procedure-item">
            {data}
                </li>
                ))}
                </ul>
                </div>
                </div>
                </div>
                </div>

                <div className="HInfo-hospital-info-section HInfo-procedure-section">
                <div className="HInfo-procedure-header">
                <div className="HInfo-title-container">
                <h4 className="HInfo-procedure-title">응급실 길찾기 / 전화하기</h4>
                <span className="HInfo-subtitle">
                아이콘을 누르면 곧바로 연결됩니다.
                </span>
                </div>
                <div className="HInfo-header-right">
                <img src={choose} alt="Update" className="HInfo-reset-icon" />
                </div>
                </div>
                <div className="HInfo-congestion-container">
                <div className="HInfo-procedure-content">
                <div className="HInfo-procedure-list-container">
                <ul className="HInfo-procedure-list-left">
                <li className="HInfo-procedure-item">
                주소: {JSON.stringify(Dhospital.address)}
                <a
                href={`https://www.google.com/maps/dir/?api=1&destination=${JSON.stringify(Dhospital.address)}`}
                target="_blank"
                rel="noopener noreferrer"
                >
                <img
                src={Location}
                alt="Location Icon"
                className="HInfo-info-icon"
                />
                </a>
                </li>
                <li className="HInfo-procedure-item">
                전화번호:{" "}
                <a href={`tel:${JSON.stringify(Dhospital.phoneNumber)}`}>
            {JSON.stringify(Dhospital.phoneNumber)}
                </a>
                <a href={`tel:${JSON.stringify(Dhospital.phoneNumber)}`}>
                <img
                src={call}
                alt="Call Icon"
                className="HInfo-info-icon"
                />
                </a>
                </li>
                </ul>
                </div>
                </div>
                </div>
                </div>

            {/*    <div className="HInfo-hospital-info-section HInfo-procedure-section">*/}
            {/*    <div className="HInfo-procedure-header">*/}
            {/*    <div className="HInfo-title-container">*/}
            {/*    <h4 className="HInfo-procedure-title">공지사항</h4>*/}
            {/*    <span className="HInfo-subtitle">*/}
            {/*    해당 정보는 60분마다 갱신됩니다.*/}
            {/*    </span>*/}
            {/*    <span className="HInfo-update-time">*/}
            {/*    </span>*/}
            {/*    </div>*/}
            {/*    <div className="HInfo-header-right">*/}
            {/*    <img src={reset} alt="Update" className="HInfo-reset-icon" />*/}
            {/*    </div>*/}
            {/*    </div>*/}
            {/*    <div className="HInfo-congestion-container">*/}
            {/*    <div className="HInfo-procedure-content">*/}
            {/*    <div className="HInfo-procedure-list-container">*/}
            {/*    <ul className="HInfo-procedure-list-left">*/}
            {/*{Ehospital.map((item, index) => (*/}
            {/*    <li key={index} className="HInfo-procedure-item">*/}
            {/*{item.emgMessage}*/}
            {/*    </li>*/}
            {/*    ))}*/}
            {/*    </ul>*/}
            {/*    </div>*/}
            {/*    </div>*/}
            {/*    </div>*/}
            {/*    </div>*/}

                <div className="HInfo-hospital-info-section HInfo-procedure-section">
            {isLoggedIn ? (
                <div className="HInfo-hospital-info-section HInfo-procedure-section">
                <div className="HInfo-procedure-header">
                <div className="HInfo-title-container">
                <h4 className="HInfo-procedure-title">별점</h4>
                <span className="HInfo-subtitle">병원에 대해 별점을 남겨보세요!</span>
                </div>
                <div className="HInfo-header-right">
                <img src={star} alt="Update" className="HInfo-reset-icon" />
                </div>
                </div>
                <div className="HInfo-congestion-container">
                <div className="HInfo-procedure-content">
                <div className="HInfo-procedure-list-container">
                <ul className="HInfo-procedure-list-left">
            {Ehospital.map((item, index) => (
                <li key={index} className="HInfo-procedure-item">
            {item.star}
                </li>
                ))}
                </ul>
                    <ul className="HInfo-procedure-list-left">
                        {Ehospital.map((item, index) => (
                            <li key={index} className="HInfo-procedure-item">
                                {item.shortMessage}
                                <button onClick={() => deletesurvey()}>삭제</button>
                            </li>
                        ))}
                    </ul>
                    <input
                        type="Star"
                        placeholder="Star"
                        value={Star}
                        onChange={(e) => setStar(e.target.value)}
                        className="regist-input"
                    />
                    <button onClick={() => handlesurvey()}></button>
                </div>
                </div>
                </div>
                </div>
                ) : (
                <div className="HInfo-hospital-info-section HInfo-procedure-section">
                <div className="HInfo-procedure-header">
                <div className="HInfo-title-container">
                <span className="HInfo-subtitle"></span>
                <span className="HInfo-update-time"></span>
                </div>
                <div className="HInfo-header-right">
                <img src={star} alt="Update" className="HInfo-star-icon" />
                <img src={star} alt="Update" className="HInfo-star-icon" />
                <img src={star} alt="Update" className="HInfo-star-icon" />
                <img src={star} alt="Update" className="HInfo-star-icon" />
                <img src={star} alt="Update" className="HInfo-star-icon" />
                </div>
                </div>
                <div className="HInfo-congestion-container">
                <div className="HInfo-procedure-content">
                <div className="HInfo-procedure-list-container">
                <ul className="HInfo-procedure-list-left">
                <h4 className="HInfo-procedure-title">
                로그인을 하면 병원에 대한 별점을 남길 수 있어요!
                </h4>
                </ul>
                </div>
                </div>
                </div>
                </div>
                )}
                </div></>
                )
                : (
                <div>Loading...</div>
                )}
        </div>
    );
}

export default HInfo;