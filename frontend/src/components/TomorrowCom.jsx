import React, { useEffect, useState } from "react";
import { getFormattedTomorrowYYYYMMDD } from "./DateUtils";
import "../css/TodayCom.css";
const TomorrowCom = () => {
  const [todoDetailsTomorrow, setTodoDetailsTomorrow] = useState([]);
  const [isAdding, setIsAdding] = useState(false);
  const [newTask, setNewTask] = useState({ tdDetail: "", tdDetailTime: "" });
  const [dropdownOpen, setDropdownOpen] = useState(null);

  useEffect(() => {
    // 가짜 데이터 생성
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
    setTodoDetailsTomorrow(fakeDataTomorrow);
  }, []);
  const handleCheckboxChange = (id) => {
    setTodoDetailsTomorrow((prev) =>
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
    setTodoDetailsTomorrow((prev) =>
      prev.filter((item) => item.tdDetailId !== id)
    );
  };
  const handleCompleteClick = (id) => {
    setTodoDetailsTomorrow((prev) =>
      prev.map((item) =>
        item.tdDetailId === id ? { ...item, tdDetailState: true } : item
      )
    );
  };
  const handleAddTask = () => {
    if (newTask.tdDetail.trim() && newTask.tdDetailTime.trim()) {
      setTodoDetailsTomorrow((prev) => [
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
      <div className="todolist_index">Tomorrow</div>
      <div className="todolist_content">
        <h2 className="todolist_date">{getFormattedTomorrowYYYYMMDD()}</h2>
        <table className="todolist_checkbox">
          <tbody>
            {todoDetailsTomorrow.map((item) => (
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

export default TomorrowCom;
