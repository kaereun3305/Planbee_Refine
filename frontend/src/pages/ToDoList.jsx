import React, { useEffect, useState } from "react";
import Sidebar from "../components/SideBar";
import Banner from "../components/Banner";
import "../css/Main.css";
import "../css/ToDoList.css";
import axios from "axios";
import {
  getFormattedTodayYYMMDD,
  getFormattedTomorrowYYMMDD,
  getFormattedTodayYYYYMMDD,
  getFormattedTomorrowYYYYMMDD,
} from "../components/DateUtils";
const ToDoList = () => {
  const [todoDetailsToday, setTodoDetailsToday] = useState([]);
  const [todoDetailsTomorrow, setTodoDetailsTomorrow] = useState([]);

  useEffect(() => {
    axios
      .get(
        `http://localhost:8080/planbee/todolist/${getFormattedTodayYYMMDD()}`
      )
      .then((response) => {
        if (Array.isArray(response.data)) {
          setTodoDetailsToday(response.data.map((item) => item.tdDetail));
        } else {
          console.error("에러", response.data);
        }
      })
      .catch((error) => {
        console.error("fetch data error", error);
      });
    axios
      .get(
        `http://localhost:8080/planbee/todolist/${getFormattedTomorrowYYMMDD()}`
      )
      .then((response) => {
        if (Array.isArray(response.data)) {
          setTodoDetailsTomorrow(response.data.map((item) => item.tdDetail));
        } else {
          console.error("내일의 데이터 에러", response.data);
        }
      })
      .catch((error) => {
        console.error("내일의 데이터 fetch 에러", error);
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
                <h2 className="todolist_date">{getFormattedTodayYYYYMMDD()}</h2>
                <div className="todolist_checkbox">
                  {todoDetailsToday.length > 0 ? (
                    <ul>
                      {todoDetailsToday.map((detail, index) => (
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
                <h2 className="todolist_date">
                  {getFormattedTomorrowYYYYMMDD()}
                </h2>
                <div className="todolist_checkbox">
                  {todoDetailsTomorrow.length > 0 ? (
                    <ul>
                      {todoDetailsTomorrow.map((detail, index) => (
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
