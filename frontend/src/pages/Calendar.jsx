import React from "react";
import Sidebar from "../components/SideBar";
import Banner from "../components/Banner";
import CalCom from "../components/CalCom";
import "../css/Main.css";
import "../css/Calendar.css";

const Calendar = () => {
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
              <div className="max_streak">
                <h3>최대 연속 달성일</h3>
                <div className="max_streak_days"></div>
              </div>
              <div className="current_streak">
                <h3>현재 연속 달성일</h3>
                <div className="current_streak_days"></div>
                <div className="current_streak_comment"></div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Calendar;
