import React, { useState, useEffect } from "react";
import { getFormattedTodayYYMM } from "./DateUtils";
import axios from "axios";
import "../css/CalendarMemoForm.css";

const CalendarMemoForm = ({
  dateKey,
  onClose,
  memoData,
  onUpdate,
  isEditMode,
}) => {
  const [form, setForm] = useState({
    calDetail1: "",
    calDetail2: "",
    calDetail3: "",
  });

  useEffect(() => {
    if (memoData) {
      setForm({
        calDetail1: memoData.calDetail1 || "",
        calDetail2: memoData.calDetail2 || "",
        calDetail3: memoData.calDetail3 || "",
      });
    }
  }, [memoData]);

  const handleChange = (field, value) => {
    setForm((prev) => ({ ...prev, [field]: value }));
  };

  const handleAdd = async () => {
    const clickDate = getFormattedTodayYYMM();
    const formattedDateKey = dateKey.toString().padStart(2, "0"); // 예: "11" (11일)
    const calDate = clickDate + formattedDateKey;
    const requestData = {
      ...form,
      calDate,
      userId: "팥붕", // 아직 세션 설정을 안해놔서 하드 코딩 해놓겠습니다
    };

    try {
      // POST 요청으로 메모 추가
      const response = await axios.post(
        `http://localhost:8080/planbee/calendar/addmemo/${calDate}`,
        requestData,
        { withCredentials: true }
      );
      console.log("추가 요청 데이터:", requestData);
      onUpdate(response.data);
      onClose(); // 팝업 닫기
    } catch (error) {
      console.error("메모 추가 실패", error);
    }
  };

  const handleUpdate = async () => {
    const clickDate = getFormattedTodayYYMM();
    const formattedDateKey = dateKey.toString().padStart(2, "0"); // 예: "11" (11일)
    const calDate = clickDate + formattedDateKey;
    const requestData = {
      ...form,
      calDate,
      userId: "팥붕", // 아직 세션 설정을 안해놔서 하드 코딩 해놓겠습니다
    };

    try {
      // PUT 요청으로 메모 수정
      const response = await axios.put(
        `http://localhost:8080/plandbee/calendar/modimemo/${calDate}`,
        requestData,
        { withCredentials: true }
      );
      console.log("수정 요청 데이터:", requestData);
      onUpdate(response.data);
      onClose(); // 팝업 닫기
    } catch (error) {
      console.error("메모 수정 실패", error);
    }
  };

  const handleDelete = async () => {
    try {
      await axios.delete(
        `http://localhost:8080/planbee/calendar/memo/${dateKey}`,
        {
          withCredentials: true,
        }
      );
      onUpdate(null);
      onClose();
    } catch (error) {
      console.error("메모 삭제 실패", error);
    }
  };

  return (
    <div className="memo_form">
      <h3>{dateKey} 메모</h3>
      <textarea
        placeholder="메모 1"
        value={form.calDetail1}
        onChange={(e) => handleChange("calDetail1", e.target.value)}
      />
      <textarea
        placeholder="메모 2"
        value={form.calDetail2}
        onChange={(e) => handleChange("calDetail2", e.target.value)}
      />
      <textarea
        placeholder="메모 3"
        value={form.calDetail3}
        onChange={(e) => handleChange("calDetail3", e.target.value)}
      />
      <div className="memo_buttons">
        {isEditMode ? (
          // 수정 버튼
          <button onClick={handleUpdate}>수정</button>
        ) : (
          // 추가 버튼
          <button onClick={handleAdd}>추가</button>
        )}
        <button onClick={handleDelete}>삭제</button>
        <button onClick={onClose}>닫기</button>
      </div>
    </div>
  );
};

export default CalendarMemoForm;
