import React, { useEffect, useState } from "react";
import {
  getFormattedTodayYYYYMMDD,
  getFormattedTodayYYMMDD,
} from "./DateUtils";
import axios from "axios";
import "../css/TodayCom.css";

const TodayCom = () => {
  const [todoDetailsToday, setTodoDetailsToday] = useState([]);
  const [memo, setMemo] = useState("");
  const [isEditingMemo, setIsEditingMemo] = useState(false);
  const [newMemo, setNewMemo] = useState(""); //서버에 전송할 수정된 memo값
  const [isAdding, setIsAdding] = useState(false);
  const [newTask, setNewTask] = useState({ tdDetail: "", tdDetailTime: "" });
  const [dropdownOpen, setDropdownOpen] = useState(null);
  const [todayTdId, setTodayTdId] = useState(null);

  useEffect(() => {
    //checklist 불러오는 함수 -> 세션연결 성공, 테스트완료
    const fetchTodoDetails = async () => {
      try {
        const response = await axios.get(
          `http://localhost:8080/planbee/todolist/getTodo/${getFormattedTodayYYMMDD()}`,
          {
            withCredentials: true,
          }
        );
        if (Array.isArray(response.data)) {
          setTodoDetailsToday(response.data);
          setTodayTdId(response.data[0].tdId);
        } else {
          console.error("오늘의 데이터 에러", response.data);
        }
      } catch (error) {
        console.error("오늘의 데이터 fetch 에러", error);
      }
    };

    //memo 불러오는 함수 -> 세션연결 성공, 테스트완료
    const fetchMemo = async () => {
      try {
        const response = await axios.get(
          `http://localhost:8080/planbee/todolist/getMemo/${getFormattedTodayYYMMDD()}`,
          {
            withCredentials: true,
          }
        );
        console.log(response.data);
        if (Array.isArray(response.data) && response.data.length > 0) {
          setMemo(response.data[0].tdMemo); // 배열에서 tdMemo만 추출하여 설정
        } else {
          console.error("메모 데이터 형식 오류", response.data);
        }
      } catch (error) {
        console.error("메모 데이터 fetch 에러", error);
      }
    };

    fetchTodoDetails();
    fetchMemo();
  }, []);

  //메모 데이터를 제대로 받아온건지 확인하는 값 추후에 삭제할 코드
  useEffect(() => {
    console.log("현재 memo 값:", memo);
  }, [memo]);

  //memo 수정 함수
  const handleSaveMemo = async () => {
    if (todayTdId === null) {
      console.error("tdId를 가져올 수 없습니다");
      return;
    }

    const requestData = {
      tdId: todayTdId,
      tdMemo: newMemo,
    };

    console.log("전송하는 데이터:", requestData);

    try {
      await axios.put("http://localhost:8080/planbee/todolist/memoWrite", {
        tdId: todayTdId,
        tdMemo: newMemo,
      });
      setMemo(newMemo);
      setIsEditingMemo(false);
    } catch (error) {
      console.error("메모 수정 실패: ", error);
    }
  };

  //todolist 체크박스 상태 변경 함수
  const handleCheckboxChange = async (id) => {
    const updatedTodoDetails = todoDetailsToday.map((item) =>
      item.tdDetailId === id
        ? { ...item, tdDetailState: !item.tdDetailState } //false인 경우 true로 바꿈
        : item
    );

    setTodoDetailsToday(updatedTodoDetails);

    //변경된 상태를 저장한 후 api 요청 보내기기
    const changedItem = updatedTodoDetails.find(
      (item) => item.tdDetailId === id
    );

    try {
      await axios.put("http://localhost:8080/planbee/todolist/state", {
        tdDetailId: changedItem.tdDetailId,
        tdId: changedItem.tdId,
        tdDetail: changedItem.tdDetail,
        tdDetailTime: changedItem.tdDetailTime,
        tdDetailState: changedItem.tdDetailState, // 반전된 상태값을 저장시켜서 전송
      });
    } catch (error) {
      console.error("체크박스 처리 오류:", error);
    }
  };

  const handleEditClick = (id) => {
    console.log("수정 버튼 클릭, 아이디:", id);
  };

  const handleDeleteClick = (id) => {
    axios
      .delete(`http://localhost:8080/planbee/todolist/detail/${id}`)
      .then(() => {
        setTodoDetailsToday((prev) =>
          prev.filter((item) => item.tdDetailId !== id)
        );
      })
      .catch((error) => {
        console.error("삭제 실패:", error);
      });
  };
  const handleCompleteClick = (id) => {
    setTodoDetailsToday((prev) =>
      prev.map((item) =>
        item.tdDetailId === id ? { ...item, tdDetailState: true } : item
      )
    );
  };
  //checklist 생성
  const handleAddTask = () => {
    if (newTask.tdDetail.trim() && newTask.tdDetailTime.trim()) {
      const newTaskData = {
        tdDetail: newTask.tdDetail,
        tdDetailTime: newTask.tdDetailTime,
      };

      axios
        .post(
          `http://localhost:8080/planbee/todolist/write/${getFormattedTodayYYMMDD}`,
          newTaskData,
          {
            withCredentials: true,
          }
        )
        .then((response) => {
          setTodoDetailsToday((prev) => [...prev, response.data]);
          setNewTask({ tdDetail: "", tdDetailTime: "" });
          setIsAdding(false);
        })
        .catch((error) => {
          console.error("추가 실패:", error);
        });
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
          {isEditingMemo ? (
            <div>
              <textarea
                value={newMemo}
                onChange={(e) => setNewMemo(e.target.value)}
              />
              <button onClick={handleSaveMemo}>저장</button>
              <button onClick={() => setIsEditingMemo(false)}>취소</button>
            </div>
          ) : (
            <div onClick={() => setIsEditingMemo(true)} className="memomemo">
              {memo}
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default TodayCom;
