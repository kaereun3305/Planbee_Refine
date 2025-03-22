import React, { useEffect, useState } from "react";
import { useLocation, useNavigate, useParams } from "react-router-dom";
import Banner from "../components/Banner";
import SideBar from "../components/SideBar";
import "../css/WriteForm.css";
import axios from "axios";

const BoardWriteCom = ({thisGroupId}) => { 
  //BoardListCom-> BoardWriteCom을 거쳐 전달됨
  const { postId } = useParams(); //List에서 글작성을 눌렀을 경우에 받아오는 글번호
  const mode = postId? "edit" : "create"; //postId가 있으면 수정, 없으면 create
  const navigate = useNavigate();
  const location = useLocation();
  const status = location.state; //수정인지 신규 글쓰기인지 상태를 받아옴
  const [postStatus, setPostStatus] = useState(""); //위의 상태를 저장할 변수
 
  const [title, setTitle] = useState(""); // 제목 입력값
  const [content, setContent] = useState(""); // 내용 입력값
  const [showProgress, setShowProgress] = useState(false); // 진척도 표시 상태
  const [postTitle, setPostTitle] = useState("");
  const [postContent, setPostContent] = useState("");

  // "진척도 가져오기" 버튼 클릭 → 제목/내용 & 버튼 사라지고 진척도 표시
  const handleShowProgress = () => setShowProgress(true);

  // "닫기" 버튼 클릭 → 진척도 사라지고 제목/내용 & 버튼 다시 표시
  const handleHideProgress = () => setShowProgress(false);

  useEffect(()=>{
    if(mode === "edit"){ //수정모드인 경우에는 기존 글 내용을 불러옴
        axios.get(
        `http://localhost:8080/planbee/groups/${thisGroupId}/boards/${postId}`
      )
      .then(response =>{
        const data = response.data;
        setTitle(data.postTitle)
        setContent(data.postContent)
      })
      .catch(error =>{
        console.log("수정정보 가져오기 실패", error)
      })
    }
  }, [mode, postId])
  
  // "Post" 버튼 클릭 (게시글 작성 후 이동)
  const handleAddPost = async () =>{
    try {
        const response = await axios.post(
          `http://localhost:8080/planbee/groups/${thisGroupId}/boards`,
            {
              "postTitle": postTitle,
             "postContent": postContent
           }
          ,{
            withCredentials: true,
          }
        )
        console.log("글 등록한 후 해당 글번호 받아오기", response.data)
        console.log("입력된 제목", postTitle);
        console.log("입력된 내용", postContent)
        //다 되면 글 하나 보는 장면으로 넘어가야함
        //navigate(`/boardOne/${postId}`)
      } catch (error) {
        console.log("글쓰기 실패",error)
      }
}
  if(mode === "create"){
  //handleAddPost();
  }


  return (
    <div className="main_container">
      <Banner />
      <div className="sidebar_and_content">
        <SideBar />
        <div className="main_content">
          <div className="white_box_writeform">
            <h2>글쓰기</h2>

            {/* 제목 & 내용 입력란 (진척도를 가져오면 숨김) */}
            {!showProgress && (
              <>
                <div className="input-group-writeform">
                  <label>제목: </label>
                  <input
                    type="text"
                    className="title-input-writeform"
                    value={postTitle}
                    onChange={(e) => {setPostTitle(e.target.value)}}
                  />
                </div>

                <div className="input-group-writeform">
                  <label>내용: </label>
                  <textarea
                    rows={5}
                    className="content-input-writeform"
                    value={postContent}
                    onChange={(e) => {setPostContent(e.target.value)}}
                  />
                </div>

              <div className="post_writeform">
                {/* 진척도 가져오기 버튼 (진척도를 가져오기 전만 표시) */}
                <button onClick={()=>handleShowProgress()} className="progress-button-writeform">
                  진척도 가져오기
                </button>
                <button onClick={()=>handleAddPost()} className="progress-button-writeform">
                  post
                </button>
              </div>
              </>
            )}

            {/* 진척도 가져오기를 누르면 아래의 진척도 표시됨 */}
            {/* 진척도 가져오기를 누르면 아래의 진척도 표시됨 */}
{showProgress && (
  <>
    <p className="progress_share_info">
      *진척도 공유시 아래의 사진과 같이 공유됩니다.*
    </p>

        {/* ✅ 🔹 가로 정렬을 위한 부모 컨테이너 추가 */}
        <div className="progress_container">
          {/* 오늘의 진척도 */}
          <div className="progress_box_writeform" style={{position : "relative"}}>
          <input type="checkbox" className="progress_checkbox" />
            <h2>하늘다람쥐님의 오늘 진척도</h2>
            <h3>2025-03-18</h3>
            <p>오늘의 진척도</p>
            <p>8개 / 10개</p>
            <div className="progress_bar_outer_writeform">
              <div className="progress_bar_inner_writeform" style={{ width: "80%" }}>
                80%
              </div>
            </div>
          </div>

          {/* 주간 진척도 */}
          <div className="weekly_progress_box_writeform" style={{position : "relative"}}>
            <input type="chceckbox" className="progress_checkbox" /> 
            <h2>하늘다람쥐님의 주간 진척도</h2>
            <input type="checkbox" className="progress_checkbox" />
            <h3>03-11 ~ 03-17</h3>
            <div className="weekly_progress_list">
              <div className="weekly_progress_item">
                <span>03-11</span>
                <div className="progress_bar_outer">
                  <div className="progress_bar_inner red" style={{ width: "20%" }}>20%</div>
                </div>
              </div>
              <div className="weekly_progress_item">
                <span>03-12</span>
                <div className="progress_bar_outer">
                  <div className="progress_bar_inner yellow" style={{ width: "45%" }}>45%</div>
                </div>
              </div>
              <div className="weekly_progress_item">
                <span>03-13</span>
                <div className="progress_bar_outer">
                  <div className="progress_bar_inner yellow" style={{ width: "60%" }}>60%</div>
                </div>
              </div>
              <div className="weekly_progress_item">
                <span>03-14</span>
                <div className="progress_bar_outer">
                  <div className="progress_bar_inner green" style={{ width: "75%" }}>75%</div>
                </div>
              </div>
              <div className="weekly_progress_item">
                <span>03-15</span>
                <div className="progress_bar_outer">
                  <div className="progress_bar_inner red" style={{ width: "30%" }}>30%</div>
                </div>
              </div>
              <div className="weekly_progress_item">
                <span>03-16</span>
                <div className="progress_bar_outer">
                  <div className="progress_bar_inner green" style={{ width: "90%" }}>90%</div>
                </div>
              </div>
              <div className="weekly_progress_item">
                <span>03-17</span>
                <div className="progress_bar_outer">
                  <div className="progress_bar_inner yellow" style={{ width: "50%" }}>50%</div>
                </div>
              </div>
            </div>
          </div>
        </div>

        {/* 닫기 + Post 버튼 */}
        <div className="button_container_writeform">
          <button onClick={handleHideProgress} className="close_button_writeform">
            닫기
          </button>
          <button onClick={()=>handleAddPost()} className="post_button_writeform">
            Post
          </button>
        </div>
      </>
    )}

          </div>
        </div>
      </div>
    </div>
  );
};

export default BoardWriteCom;
