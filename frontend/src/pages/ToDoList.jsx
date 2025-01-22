import React from "react";
import Sidebar from "../components/SideBar";
import Banner from "../components/Banner";
import "../css/Main.css";
import "../css/ToDoList.css";

const ToDoList = () => {
  return (
    <div className="main_container">
      <Banner />
      <div className="sidebar_and_content">
        <Sidebar />
        <div className="main_content">
          <div className="todolist_container">
            <div className="todolist">today</div>
            <div className="todo_progress">
              <div className="todo_gauge">게이지</div>
              <div className="todo_save_btn">SAVE</div>
            </div>
            <div className="todolist">tomorrow</div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ToDoList;
