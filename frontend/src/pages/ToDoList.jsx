import React from "react";
import Sidebar from "../components/SideBar";
import Banner from "../components/Banner";
import TodayCom from "../components/TodayCom";
import TomorrowCom from "../components/TomorrowCom";
import "../css/Main.css";
import "../css/ToDoList.css";
import axios from "axios";

const ToDoList = () => {
  return (
    <div className="main_container">
      <Banner />
      <div className="sidebar_and_content">
        <Sidebar />
        <div className="main_content">
          <div className="todolist_container">
            <TodayCom />
            <div className="todo_progress">
              <div className="todo_percent">50%</div>
              <div className="todo_bar">
                <div className="todo_bar_ex"></div>
              </div>
            </div>
            <TomorrowCom />
          </div>
        </div>
      </div>
    </div>
  );
};

export default ToDoList;
