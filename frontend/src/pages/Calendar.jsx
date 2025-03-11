import React, { useEffect, useState } from "react";
import Sidebar from "../components/SideBar";
import Banner from "../components/Banner";
import CalCom from "../components/CalCom";
import CurStreak from "../components/CurStreak";
import MaxStreak from "../components/MaxStreak";
import axios from "axios";
import "../css/Main.css";
import "../css/Calendar.css";

const Calendar = () => {
  const [sessionReady, setSessionReady] = useState(false);

  useEffect(() => {
    const makeSession = async () => {
      try {
        const response = await axios.post(
          `http://localhost:8080/planbee/calendar/makeSession`,
          null,
          {
            withCredentials: true,
          }
        );
        console.log("세션 요청 여부:", response.data);
        checkSession();
      } catch (error) {
        console.error("세션 fetching 실패!", error);
      }
    };

    const checkSession = async () => {
      try {
        const response = await axios.get(
          `http://localhost:8080/planbee/calendar/checkSession`,
          {
            withCredentials: true,
          }
        );
        console.log("세션 확인 :", response.data);
        setSessionReady(true);
      } catch (error) {
        console.error("에러", error);
      }
    };

    makeSession();
  }, []);
  if (!sessionReady) {
    return <div>세션 설정중...</div>;
  }
  return (
    <div className="main_container">
      <Banner />
      <div className="sidebar_and_content">
        <Sidebar />
        <div className="main_content">
          <div className="calendar_container">
            <div className="calendar_boxL">
              <div className="calendar_box_test">
                <CalCom />
              </div>
            </div>
            <div className="calendar_boxR">
              <MaxStreak />
              <CurStreak />
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Calendar;
