import React, { useEffect, useState } from "react";
import axios from "axios";
import "../css/Streak.css";

const CurStreak = () => {
  const [curStreak, setCurStreak] = useState(null);
  useEffect(() => {
    const fetchCurStreak = async () => {
      try {
        const response = await axios.get(
          `http://localhost:8080/planbee/calendar/curStreak`,
          {
            withCredentials: true,
          }
        );
        console.log(response.data);
        setCurStreak(response.data);
      } catch (error) {
        console.error("현재 진척도 fetch 에러", error);
      }
    };

    fetchCurStreak();
  }, []);
  return (
    <div className="current_streak">
      <h3>현재 연속 달성일</h3>
      <div className="current_streak_days">
        <span className="curStreak_data">
          {curStreak !== null ? curStreak : "로딩 중..."}
        </span>
        <span className="curStreak_days">days</span>
      </div>
      <div className="current_streak_comment"></div>
    </div>
  );
};

export default CurStreak;
