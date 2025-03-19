import React from 'react'
import Banner from './Banner'
import SideBar from './SideBar'
import { useNavigate } from 'react-router-dom';
import '../css/WriteForm.css';

const BoardWrite = () => {
    const navigate = useNavigate();
    const [title, setTitle] = useState("");
    const [content, setContent] = useState("");

    const [showProgress, setShowProgress] = useState(false);

     
  const handleShowProgress = () => {// "진척도 가져오기" 버튼
    setShowProgress(true);
  };

  const handleHideProgress = () => {// "닫기" 버튼
    setShowProgress(false);
  };

   
   const handlePost = () => {// "Post" 버튼 -> DB 없이 /group 페이지로 이동 + 새 글 정보 전달
    navigate("/group", {
      state: {
        title,
        content,
      },
    });
  };




    const handleAddPost = async () =>{
        try {
            const response = await axios.post(
              `http://localhost:8080/planbee/board/boardWrite`,
                {
                  "postTitle": postTitle,
                 "postContent": postContent
               }
              ,{
                withCredentials: true,
              }
            )
            console.log("제목", postTitle);
            console.log("내용", postContent)
          } catch (error) {
            console.log("글쓰기 실패",error)
          }
    }
    




  return (
    <div className="main_container">
      <Banner />
      <div className="sidebar_and_content">
        <SideBar />
        <div className="main_content">
          <div className="white_box_writeform">
            <h2>글쓰기</h2>

            {/* 제목/내용 입력란 (진척도 안보일 때) */}
            {!showProgress && (
              <>
                <div className="input-group-writeform">
                  <label>제목: </label>
                  <input
                    type="text"
                    className="title-input-writeform"
                    value={title}
                    onChange={(e) => setTitle(e.target.value)}
                  />
                </div>

                <div className="input-group-writeform">
                  <label>내용: </label>
                  <textarea
                    rows={5}
                    className="content-input-writeform"
                    value={content}
                    onChange={(e) => setContent(e.target.value)}
                  />
                </div>
              </>
            )}

            {/* 진척도 가져오기 버튼 */}
            <button onClick={handleShowProgress} className="progress-button-writeform">
              진척도 가져오기
            </button>

            {/* 진척도 가져오기 버튼 클릭 후 보여질 화면 */}
            {showProgress && (
              <>
                <p className="progress_share_info">
                  *진척도 공유시 아래의 사진과 같이 공유됩니다.*
                </p>

                {/* 오늘의 진척도 */}
                <div className="progress_box_writeform">
                  <h2>하늘다람쥐님의 오늘 진척도</h2>
                  <h3>2025-03-18</h3>
                  <p>오늘의 진척도</p>
                  <p>8개 / 10개</p>
                  <div className="progress_bar_outer_writeform">
                    <div
                      className="progress_bar_inner_writeform"
                      style={{ width: "80%" }}
                    >
                      80%
                    </div>
                  </div>
                </div>

                {/* 주간 진척도 */}
                <div className="weekly_progress_box_writeform">
                  <h2>하늘다람쥐님의 주간 진척도</h2>
                  <h3>03-11 ~ 03-17</h3>
                  <div className="weekly_progress_list">
                    {[
                      { date: "03-11", percent: 20, color: "red" },
                      { date: "03-12", percent: 45, color: "yellow" },
                      { date: "03-13", percent: 60, color: "yellow" },
                      { date: "03-14", percent: 75, color: "green" },
                      { date: "03-15", percent: 30, color: "red" },
                      { date: "03-16", percent: 90, color: "green" },
                      { date: "03-17", percent: 50, color: "yellow" },
                    ].map((day, index) => (
                      <div key={index} className="weekly_progress_item">
                        <span>{day.date}</span>
                        <div className="progress_bar_outer">
                          <div className={`progress_bar_inner ${day.color}`} style={{ width: `${day.percent}%` }}>
                            {day.percent}%
                          </div>
                        </div>
                      </div>
                    ))}
                  </div>
                </div>
              </>
            )}

            {/* 닫기 버튼 & Post 버튼 함께 배치 */}
            {showProgress && (
              <div className="button_container_writeform">
                <button onClick={handleHideProgress} className="close_button_writeform">
                  닫기
                </button>
                <button onClick={handlePost} className="post_button_writeform">
                  <FaPaperPlane style={{ marginRight: "5px" }} /> Post
                </button>
              </div>
            )}

            {/* Post 버튼 (진척도를 가져오지 않았을 때) */}
            {!showProgress && (
              <button onClick={handlePost} className="post_button_writeform">
                <FaPaperPlane style={{ marginRight: "5px" }} /> Post
              </button>
            )}
          </div>
        </div>
      </div>
    </div>
  );
}

export default BoardWrite
