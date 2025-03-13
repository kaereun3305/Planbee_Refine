import React, { useEffect, useState } from "react";
import Sidebar from "../components/SideBar";
import Banner from "../components/Banner";
import "../css/Main.css";
import "../css/Archive.css";
import { Icon } from "@iconify/react";
import axios from "axios";

const Archive = () => {
  const [archiveData, setArchiveData] = useState(null);
  const [cache, setCache] = useState([]);
  const [currentIndex, setCurrentIndex] = useState(0);
  const [page, setPage] = useState(0);

  const formatDate = (dateStr) => {
    const year = `20${dateStr.slice(0, 2)}`;
    const month = dateStr.slice(2, 4);
    const day = dateStr.slice(4, 6);
    return `${year}-${month}-${day}`;
  };

  const formatTime = (timeStr) => {
    const padded = timeStr.padStart(4, "0");
    const hour = padded.slice(0, 2);
    const minute = padded.slice(2);
    return `${hour}:${minute}`;
  };

  useEffect(() => {
    const makeSession = async () => {
      try {
        const response = await axios.post(
          `http://localhost:8080/planbee/archive/makeSession`,
          null,
          { withCredentials: true }
        );
        console.log("세션 요청 여부:", response.data);
        checkSession();
      } catch (error) {
        console.error("세션 fetching 실패!", error);
      }
    };
    const checkSession = async () => {
      try {
        const response = await axios.get(
          `http://localhost:8080/planbee/archive/checkSession`,
          { withCredentials: true }
        );
        console.log("세션 확인", response.data);
      } catch (error) {
        console.error("에러", error);
      }
    };
    const fetchData = async () => {
      try {
        const response = await axios.get(
          `http://localhost:8080/planbee/archive/`,
          { withCredentials: true }
        );
        console.log("페이징 데이터", response.data);
        setArchiveData(response.data);
      } catch (error) {
        console.error("페이징 에러", error);
      }
    };
    makeSession();
    fetchData();
  }, []);

  return (
    <div className="main_container">
      <Banner />
      <div className="sidebar_and_content">
        <Sidebar />
        <div className="main_content">
          <div className="archive_container">
            <div className="archive_search">
              <div className="archive_search_calendar">
                <input
                  type="date"
                  id="date"
                  name="archiveDate"
                  placeholder="Date"
                />
                <button>
                  <Icon
                    icon="ic:baseline-search"
                    width="36px"
                    height="36px"
                    style={{ color: "#F4CC3A" }}
                  />
                </button>
              </div>
            </div>
            <div className="archive_content">
              <button className="archive_btn_left">
                <Icon
                  icon="material-symbols-light:arrow-back-2-rounded"
                  width="72"
                  height="72"
                  style={{ color: "#cccccc" }}
                />
              </button>
              <div className="archive_page_left">
                {archiveData && archiveData.length > 0 && (
                  <>
                    <div className="archive_left_content">
                      <h2 className="archive_date">
                        {formatDate(archiveData[0].archiveDate).replace(
                          /-/g,
                          "."
                        )}
                      </h2>
                      <ul className="archive_checklist">
                        {archiveData[0].archiveDetails.map((detail) => (
                          <li
                            key={detail.archDetailId}
                            className="archive_check_item"
                          >
                            <input
                              type="checkbox"
                              checked={detail.archDetailState}
                              readOnly
                            />
                            <span
                              className={
                                detail.archDetailState ? "checked" : ""
                              }
                              style={{ marginLeft: "8px", marginRight: "12px" }}
                            >
                              {detail.archDetail}
                            </span>
                            <span className="archive_time">
                              {formatTime(detail.archDetailTime)}
                            </span>
                          </li>
                        ))}
                      </ul>
                    </div>
                    <div className="archive_left_memo">
                      <p>{archiveData[0].archiveMemo}</p>
                    </div>
                  </>
                )}
              </div>

              <div className="archive_page_right">
                {archiveData && archiveData.length > 1 && (
                  <>
                    <div className="archive_right_content">
                      <h2 className="archive_date">
                        {formatDate(archiveData[1].archiveDate).replace(
                          /-/g,
                          "."
                        )}
                      </h2>
                      <ul className="archive_checklist">
                        {archiveData[1].archiveDetails.map((detail) => (
                          <li
                            key={detail.archDetailId}
                            className="archive_check_item"
                          >
                            <input
                              type="checkbox"
                              checked={detail.archDetailState}
                              readOnly
                            />
                            <span
                              className={
                                detail.archDetailState ? "checked" : ""
                              }
                              style={{ marginLeft: "8px", marginRight: "12px" }}
                            >
                              {detail.archDetail}
                            </span>
                            <span className="archive_time">
                              {formatTime(detail.archDetailTime)}
                            </span>
                          </li>
                        ))}
                      </ul>
                    </div>
                    <div className="archive_right_memo">
                      <p>{archiveData[1].archiveMemo}</p>
                    </div>
                  </>
                )}
              </div>
              <button className="archive_btn_right">
                <Icon
                  icon="material-symbols-light:play-arrow-rounded"
                  width="72"
                  height="72"
                  style={{ color: "#cccccc" }}
                />
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Archive;
