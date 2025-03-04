import React, { useEffect } from "react";
import Sidebar from "../components/SideBar";
import Banner from "../components/Banner";
import CalCom from "../components/CalCom";
import CurStreak from "../components/CurStreak";
import MaxStreak from "../components/MaxStreak";
import axios from "axios";
import "../css/Main.css";
import "../css/Calendar.css";

const Calendar = () => {
  useEffect(() => {
    const makeSession = async () => {
      try {
        const response = await axios.post(
          `http://localhost:8080/planbee/calendar/makeSession`,
          null,
          { withCredential: true }
        );
        console.log("세션 요청 여부:", response.data);
      } catch (error) {
        console.error("세션 fetching 실패", error);
      }
    };
    makeSession();
  }, []);
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
