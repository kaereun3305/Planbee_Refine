import React from "react";
import Sidebar from "../components/SideBar";
import Banner from "../components/Banner";
import "../css/Main.css";
import "../css/Archive.css";

const Archive = () => {
  return (
    <div className="main_container">
      <Banner />
      <div className="sidebar_and_content">
        <Sidebar />
        <div className="main_content">
          <div className="archive_container">
            <div className="archive_search">
              <div className="archive_search_calendar">달력 검색~</div>
              <button className="archive_search_btn">검색</button>
            </div>
            <div className="archive_content">
              <button className="archive_btn_left">⬅️</button>
              <div className="archive_page_left">왼쪽 페이지</div>
              <div className="archive_page_right">오른쪽 페이지</div>
              <button className="archive_btn_right">➡️</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Archive;
