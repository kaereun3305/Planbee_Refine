import React from "react";
import Sidebar from "../components/SideBar";
import Banner from "../components/Banner";
import "../css/Main.css";
import "../css/Archive.css";
import { Icon } from "@iconify/react";

const Archive = () => {
  return (
    <div className="main_container">
      <Banner />
      <div className="sidebar_and_content">
        <Sidebar />
        <div className="main_content">
          <div className="archive_container">
            <div className="archive_search">
              <div className="archive_search_calendar">
                <input
                  type="date"
                  id="date"
                  name="archiveDate"
                  placeholder="Date"
                />
                <button>
                  <Icon
                    icon="ic:baseline-search"
                    width="36px"
                    height="36px"
                    style={{ color: "#F4CC3A" }}
                  />
                </button>
              </div>
            </div>
            <div className="archive_content">
              <button className="archive_btn_left">
                <Icon
                  icon="material-symbols-light:arrow-back-2-rounded"
                  width="72"
                  height="72"
                  style={{ color: "#cccccc" }}
                />
              </button>
              <div className="archive_page_left">
                <div className="archive_left_content"></div>
                <div className="archive_left_memo"></div>
              </div>
              <div className="archive_page_right">
                <div className="archive_right_content"></div>
                <div className="archive_right_memo"></div>
              </div>
              <button className="archive_btn_right">
                <Icon
                  icon="material-symbols-light:play-arrow-rounded"
                  width="72"
                  height="72"
                  style={{ color: "#cccccc" }}
                />
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Archive;
