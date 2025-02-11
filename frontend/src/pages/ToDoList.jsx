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
  const [isAdding, setIsAdding] = useState(false);
  const [newTask, setNewTask] = useState({ tdDetail: "", tdDetailTime: "" });
  const [dropdownOpen, setDropdownOpen] = useState(null);
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
  useEffect(() => {
    // 가짜 데이터 생성
    const fakeDataToday = [
      {
        tdDetailId: 4,
        tdId: 3,
        tdDetail: "추가적으로 작성해보기",
        tdDetailTime: "1730",
        tdDetailState: false,
      },
      {
        tdDetailId: 5,
        tdId: 3,
        tdDetail: "다른날짜도 될까?",
        tdDetailTime: "1730",
        tdDetailState: false,
      },
    ];
    const fakeDataTomorrow = [
      {
        tdDetailId: 6,
        tdId: 4,
        tdDetail: "내일 해야 할 일 1",
        tdDetailTime: "1500",
        tdDetailState: false,
      },
      {
        tdDetailId: 7,
        tdId: 4,
        tdDetail: "내일 해야 할 일 2",
        tdDetailTime: "1800",
        tdDetailState: false,
      },
    ];
    setTodoDetailsToday(fakeDataToday);
    setTodoDetailsTomorrow(fakeDataTomorrow);
  }, []);
  // 체크박스 상태 변경 함수
  const handleCheckboxChange = (id) => {
    setTodoDetailsToday((prev) =>
      prev.map((item) =>
        item.tdDetailId === id
          ? { ...item, tdDetailState: !item.tdDetailState }
          : item
      )
    );
  };
  // 수정 버튼 클릭 함수 (추후 기능 추가 가능)
  const handleEditClick = (id) => {
    console.log("수정 버튼 클릭, 아이디:", id);
  };

  const handleDeleteClick = (id) => {
    setTodoDetailsToday((prev) =>
      prev.filter((item) => item.tdDetailId !== id)
    );
  };

  const handleCompleteClick = (id) => {
    setTodoDetailsToday((prev) =>
      prev.map((item) =>
        item.tdDetailId === id ? { ...item, tdDetailState: true } : item
      )
    );
  };

  const handleAddTask = () => {
    if (newTask.tdDetail.trim() && newTask.tdDetailTime.trim()) {
      setTodoDetailsToday((prev) => [
        ...prev,
        { tdDetailId: Date.now(), ...newTask, tdDetailState: false },
      ]);
      setNewTask({ tdDetail: "", tdDetailTime: "" });
      setIsAdding(false);
    }
  };

  const toggleDropdown = (id) => {
    setDropdownOpen(dropdownOpen === id ? null : id);
  };

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
                <table className="todolist_checkbox">
                  <tbody>
                    {todoDetailsToday.map((item) => (
                      <tr key={item.tdDetailId}>
                        <td>
                          <input
                            type="checkbox"
                            checked={item.tdDetailState}
                            onChange={() =>
                              handleCheckboxChange(item.tdDetailId)
                            }
                          />
                        </td>
                        <td>{item.tdDetail}</td>
                        <td>{item.tdDetailTime}</td>
                        <td>
                          <span onClick={() => toggleDropdown(item.tdDetailId)}>
                            🖉
                          </span>
                          {dropdownOpen === item.tdDetailId && (
                            <div className="dropdown-menu">
                              <button
                                onClick={() => handleEditClick(item.tdDetailId)}
                              >
                                수정
                              </button>
                              <button
                                onClick={() =>
                                  handleDeleteClick(item.tdDetailId)
                                }
                              >
                                삭제
                              </button>
                              <button
                                onClick={() =>
                                  handleCompleteClick(item.tdDetailId)
                                }
                              >
                                완료
                              </button>
                            </div>
                          )}
                        </td>
                      </tr>
                    ))}
                  </tbody>
                </table>
                {isAdding ? (
                  <div>
                    <input
                      type="text"
                      placeholder="할 일 입력"
                      value={newTask.tdDetail}
                      onChange={(e) =>
                        setNewTask({ ...newTask, tdDetail: e.target.value })
                      }
                    />
                    <input
                      type="text"
                      placeholder="목표 시간"
                      value={newTask.tdDetailTime}
                      onChange={(e) =>
                        setNewTask({ ...newTask, tdDetailTime: e.target.value })
                      }
                    />
                    <button onClick={handleAddTask}>완료</button>
                  </div>
                ) : (
                  <button onClick={() => setIsAdding(true)}>일정 추가</button>
                )}
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
                <div className="todolist_checkbox"></div>
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
