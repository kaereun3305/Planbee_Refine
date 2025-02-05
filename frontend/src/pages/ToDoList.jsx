import React, { useEffect, useState } from "react";
import Sidebar from "../components/SideBar";
import Banner from "../components/Banner";
import "../css/Main.css";
import "../css/ToDoList.css";
import axios from "axios";

const ToDoList = () => {
  const [todoDetails, setTodoDetails] = useState([]);

  useEffect(() => {
    axios
      .get("http://localhost:8080/planbee/todolist")
      .then((response) => {
        if (Array.isArray(response.data)) {
          setTodoDetails(response.data.map((item) => item.tdDetail));
        } else {
          console.error("에러", response.data);
        }
      })
      .catch((error) => {
        console.error("fetch data error", error);
      });
  }, []);

  return (
    <div className="main_container">
      <Banner />
      <div className="sidebar_and_content">
        <Sidebar />
        <div className="main_content">
          <div className="todolist_container">
            <div className="todolist">
              <div className="todolist_index">Today</div>
              <div className="todolist_content">
                <h2 className="todolist_date">2025.02.05</h2>
                <div className="todolist_checkbox">
                  {todoDetails.length > 0 ? (
                    <ul>
                      {todoDetails.map((detail, index) => (
                        <li key={index}>{detail}</li>
                      ))}
                    </ul>
                  ) : (
                    <p>데이터를 불러오는 중...</p>
                  )}
                </div>
                <div className="todolist_memo">
                  <h3>Memo</h3>
                </div>
              </div>
            </div>
            <div className="todo_progress">
              <div className="todo_percent">50%</div>
              <div className="todo_bar">
                <div className="todo_bar_ex"></div>
              </div>
            </div>
            <div className="todolist">
              <div className="todolist_index">Tomorrow</div>
              <div className="todolist_content">
                <h2 className="todolist_date">2025.02.06</h2>
                <div className="todolist_checkbox">
                  {todoDetails.length > 0 ? (
                    <ul>
                      {todoDetails.map((detail, index) => (
                        <li key={index}>{detail}</li>
                      ))}
                    </ul>
                  ) : (
                    <p>데이터를 불러오는 중...</p>
                  )}
                </div>
                <div className="todolist_memo">
                  <h3>Memo</h3>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ToDoList;
