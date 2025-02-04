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
            <div className="calendar_boxR">최대 스트릭</div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Calendar;
