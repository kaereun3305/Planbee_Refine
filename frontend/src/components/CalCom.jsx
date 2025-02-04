import React from "react";
import FullCalendar from "@fullcalendar/react";
import dayGridPlugin from "@fullcalendar/daygrid";
import "../css/CalCom.css";

const tasks = {
  "2025-02-05": { completed: 75, memo: "운동, 독서" },
  "2025-02-10": { completed: 50, memo: "미팅, 프로젝트 진행" },
};

const CalCom = () => {
  return (
    <FullCalendar
      plugins={[dayGridPlugin]}
      initialView="dayGridMonth"
      height="100%" // 부모 div의 크기에 맞춰서 크기 조정
      contentHeight="100%" // 달력 내용 영역이 부모 div의 크기에 맞게 조정
      dayCellContent={({ date }) => {
        const dateStr = date.toISOString().split("T")[0];
        const task = tasks[dateStr];

        return (
          <div className="custom-cell">
            <div className="date">{date.getDate()}</div>
            <div
              className="progress"
              style={{
                background: `rgba(0, 150, 0, ${task?.completed / 100 || 0})`,
              }}
            />
            <div className="memo">{task?.memo || ""}</div>
          </div>
        );
      }}
    />
  );
};

export default CalCom;
