import React from "react";
import Sidebar from "../components/SideBar";
import Banner from "../components/Banner";
import TodayCom from "../components/TodayCom";
import TomorrowCom from "../components/TomorrowCom";
import "../css/Main.css";
import "../css/ToDoList.css";

const ToDoList = () => {
  {
    /*useEffect(() => {
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
  }, []); */
  }

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
