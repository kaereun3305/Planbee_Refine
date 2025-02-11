import React, { useEffect, useState } from "react";
import { getFormattedTodayYYYYMMDD } from "./DateUtils";
import "../css/TodayCom.css";
const TodayCom = () => {
  const [todoDetailsToday, setTodoDetailsToday] = useState([]);
  const [isAdding, setIsAdding] = useState(false);
  const [newTask, setNewTask] = useState({ tdDetail: "", tdDetailTime: "" });
  const [dropdownOpen, setDropdownOpen] = useState(null);

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
    setTodoDetailsToday(fakeDataToday);
  }, []);
  const handleCheckboxChange = (id) => {
    setTodoDetailsToday((prev) =>
      prev.map((item) =>
        item.tdDetailId === id
          ? { ...item, tdDetailState: !item.tdDetailState }
          : item
      )
    );
  };
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
                    onChange={() => handleCheckboxChange(item.tdDetailId)}
                  />
                </td>
                <td>{item.tdDetail}</td>
                <td>{item.tdDetailTime}</td>
                <td>
                  <span onClick={() => toggleDropdown(item.tdDetailId)}>🖉</span>
                  {dropdownOpen === item.tdDetailId && (
                    <div className="dropdown-menu">
                      <button onClick={() => handleEditClick(item.tdDetailId)}>
                        수정
                      </button>
                      <button
                        onClick={() => handleDeleteClick(item.tdDetailId)}
                      >
                        삭제
                      </button>
                      <button
                        onClick={() => handleCompleteClick(item.tdDetailId)}
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
  );
};

export default TodayCom;
