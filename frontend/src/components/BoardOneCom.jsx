import axios from 'axios';
import React, { useEffect, useState } from 'react'
import { FaArrowLeft, FaEllipsisV } from 'react-icons/fa';
import { useNavigate, useParams, useLocation } from 'react-router-dom';
import Banner from './Banner';
import SideBar from './SideBar';
import '../css/BoardOne.css'
import ReplyInputCom from './ReplyInputCom';

const BoardOneCom = ({thisPostId, thisGroupId}) => { //BoardDetail.jsx에서 받아온 해당글번호와 그룹번호
    //thisGroupId.thisGroupId로 써야함함
    //console.log("boardOneCom", thisPostId, thisGroupId)
    const sessionId = "팥붕"; //일단 하드코딩해둠
    const location = useLocation();
    const navigate = useNavigate(); 
    const {postId} = useParams(); //postId는 useParams에서 받아서 저장해둔다
    const [thisPost, setThisPost] = useState([]);
    const [reply, setReply] = useState([]); //현재 쓰여있는 댓글관리: 배열
    const [comment, setComment] = useState("") //새로쓰는 댓글 관리: String
    const [isEditing, setIsEditing] = useState(false); //글 보기상태인지 수정상태인지, 기초값은 글보기 false
    const [editedPost, setEditedPost] = useState({ postTitle: "", postContent: "" });
    const [activeMenu, setActiveMenu] = useState(null);
    const [editingReplyId, setEditingReplyId] = useState(null); //댓글 수정시, 댓글아이디 저장
    const [editingReplyContent, setEditingReplyContent] = useState("");//댓글 수정시 그 내용 저장
    
    const fetchThisPost = async () => { //이 글의 내용과 댓글을 불러오는 함수
        try {
          const response = await axios.get(
            `http://localhost:8080/planbee/groups/${thisGroupId.thisGroupId}/boards/${thisPostId.id}`,
            {
              withCredentials:true,
            }
          )
          console.log("글 하나 불러오기결과", response.data.replies)
          setThisPost(response.data.post);
          setReply(response.data.replies);
        } catch (error) {
          console.log("BoardOneCom, 글 하나 가져오기 실패", error)
      }
    }
    const handleHit = () =>{ //조회수 올리는 함수
      try {
        axios.put(
          `http://localhost:8080/planbee/groups/${thisGroupId.thisGroupId}/boards/${thisPostId.id}/hit`,{
            withCredentials: true,
          }
        )
        console.log("조회수 증가 성공?")
      } catch (error) {
        console.log("조회수 증가 실패", error)
      }   
    }
    
    useEffect(()=>{
      handleHit();
    },[])

    useEffect(() => { //글 수정해서 thisPostId와 thisGroupId가 변경될때마다 이 포스트를 새로 가져옴옴
      if (thisPostId && thisGroupId) {
        fetchThisPost();
      }
    }, [thisPostId, thisGroupId]);

    
    const handleGoBack = () => navigate(-1);
    const handleModify = () => {
      setIsEditing(true)
    }
    const handleChange = (e) => {
      setEditedPost({ ...editedPost, [e.target.name]: e.target.value })}
    
    const handleModifyingSave = () => { //게시글 수정함수
      axios.put(
        `http://localhost:8080/planbee/groups/${thisGroupId.thisGroupId}/boards/${thisPostId.id}`,{
          postTitle: editedPost.title,
          postContent: editedPost.contents,
        },
        {
          withCredentials:true,
        }
      ).then(response=> {
        console.log("게시글 수정 성공", response);
        setThisPost({
          ...thisPost,
          postTitle: editedPost.title,
          postContent: editedPost.contents
      });
      setIsEditing(false);
      }).catch(error => {
        console.log("게시글 수정 실패", error)
      })
      
    }
    const handleCancel = () => {
      setIsEditing(false);
    }

    const handleDel = async () => { //삭제기능 구현완료료
      console.log("삭제버튼 클릭: thisPostId", thisPostId, "thisGroupId", thisGroupId)
        if (window.confirm("삭제하시겠습니까?")) {
          try {
            const response = await
            axios.delete(`http://localhost:8080/planbee/groups/${thisGroupId.thisGroupId}/boards/${thisPostId.id}`,{
              withCredentials:true,
              
            })
            setThisPost({});
            console.log("삭제 실행결과:", response)
            console.log({thisPostId}, "번 글 삭제되었고", {thisGroupId},"글을 호출합니다");
            navigate(`/boardList/${thisGroupId.thisGroupId}`, 
              {state: {groupId : thisGroupId.thisGroupId, redirectUrl: `/planbee/groups/${thisGroupId.thisGroupId}`}

            });
          } catch (error) {
            console.log("삭제 실패", error)
          }         
        } else {
            console.log("삭제 취소");
        }
    };
     // 드롭다운 메뉴 토글 함수: 이미 활성화된 메뉴 클릭 시 닫고, 아니면 해당 메뉴를 활성화
  const toggleMenu = (id) => {
    setActiveMenu(activeMenu === id ? null : id);
  };
    
    useEffect(() => {
        if (thisPost.postTitle && thisPost.postContent) { 
            setEditedPost({ title: thisPost.postTitle, contents: thisPost.postContent });
        }
    }, [thisPost]); //thisPost가 변경될 때 실행

    const handleAddReply = async () => { //댓글 등록 기능
      try {
          const response = await axios.post(
              `http://localhost:8080/planbee/groups/${thisGroupId.thisGroupId}/boards/${thisPostId.id}/reply`,
              {
                  "replyContent" : comment
              },{
                  withCredentials: true,
              }
          )
          console.log("댓글입력결과확인: ", response.data)
          fetchThisPost();
          setComment("");
      } catch (error) {
          console.log("댓글입력 에러", error)
      }      
  }
  const handleModifyReply = (replyId, currentContent) => { //댓글 수정상태로 만드는 함수
    setEditingReplyId(replyId);
    setEditingReplyContent(currentContent);
    handleModiSaveReply();
  };
  const handleModiSaveReply = async (replyId) =>{ //수정한 댓글을 저장하는 함수 
    try {
      const response = await axios.put(
        `http://localhost:8080/planbee/groups/${thisGroupId.thisGroupId}/boards/${thisPostId.id}/reply/${replyId}`,{
          replyContent: editingReplyContent
        },
        {
          withCredentials:true,
        }
      )
      console.log("댓글 수정 성공", response.data)
      setEditingReplyId(null);
    setEditingReplyContent("");
      fetchThisPost();
      
    } catch (error) {
      console.log("댓글 수정 실패", error)
    }
  }
  // 댓글 수정 취소 함수
const handleCancelReply = () => {
  setEditingReplyId(null);
  setEditingReplyContent("");
};
  const handleDeleteReply = async (replyId) =>{
    try {
      const response = await axios.delete(
        `http://localhost:8080/planbee/groups/${thisGroupId.thisGroupId}/boards/${thisPostId.id}/reply/${replyId}`,
        {
          withCredentials:true,
        }
      )
      console.log("댓글 삭제 실행결과", response.data)
      fetchThisPost();
    } catch (error) {
      console.log("댓글 삭제 실패ㅠㅠ", error)
    }
  }

  const renderReplies = (replies, indent = 0) => {
    return replies.map((reply) => (
      <div key={reply.replyId} style={{ marginLeft: indent }}>
        <div className="comment">
          <div className="comment_user">
            <div className={`user_avatar ${reply.avatarClass || ""}`} />
            <span className="user_name">{reply.userId}</span>
            {/* 댓글 옵션 버튼은 수정 모드가 아닐 때만 표시 */}
            {reply.userId === sessionId && (
              <button className="options_button" onClick={() => toggleMenu(reply.replyId)}>
                <FaEllipsisV />
              </button>
            )}
            {/* 드롭다운 메뉴 (여기서는 간단히 수정/삭제 버튼으로 처리) */}
            {activeMenu === reply.replyId &&  (
              <div className="dropdown_menu comment_dropdown">
                <button onClick={() => handleModifyReply(reply.replyId, reply.replyContent)}>수정</button>
                <button onClick={() => handleDeleteReply(reply.replyId)}>삭제</button>
              </div>
            )}
          </div>
          <div className="comment_text_box">
            {editingReplyId === reply.replyId ? (
              // 수정 모드인 댓글이면 textarea와 저장/취소 버튼을 보여줌
              <>
                <textarea
                  value={editingReplyContent}
                  onChange={(e) => setEditingReplyContent(e.target.value)}
                  style={{
                    width: "100%",
                    padding: "8px",
                    border: "1px solid #ddd",
                    borderRadius: "5px",
                    resize: "vertical",
                  }}
                />
                <div style={{ marginTop: "5px", textAlign: "right" }}>
                  <button onClick={() => handleModiSaveReply(reply.replyId)} style={{ marginRight: "5px" }}>
                    저장
                  </button>
                  <button onClick={handleCancelReply}>취소</button>
                </div>
              </>
            ) : (
              // 일반 모드
              <>
                <p className="comment_text">{reply.replyContent}</p>
                <span className="comment_time">{reply.replyDate}</span>
              </>
            )}
          </div>
        </div>
        {/* 자식 댓글 (대댓글)이 있을 경우 재귀적으로 렌더링 */}
        {reply.replies && reply.replies.length > 0 && renderReplies(reply.replies, indent + 20)}
      </div>
    ));
  };

    const renderEditMode = () => (
      <div className="post_container">
        <button className="back_button" onClick={() => setIsEditing(false)}>
          <FaArrowLeft className="back_icon" />
        </button>
        <div className="post_header">
          <input
            type="text"
            name="title"
            value={editedPost.title}
            onChange={handleChange}
            className="post_title_input"
          />
        </div>
        <hr className="post_divider" />
        <div className="post_content">
          <textarea
            name="contents"
            value={editedPost.contents}
            onChange={handleChange}
            className="post_content_input"
            rows="10"
          />
        </div>
        <div className="edit_buttons">
          <button onClick={handleModifyingSave}>저장</button>
          <button onClick={handleCancel}>취소</button>
        </div>
      </div>
    );
  
    // 일반 보기 모드일 때 화면 JSX
    const renderViewMode = () => (
      <div className="post_container">
        <button className="back_button" onClick={() => navigate(-1)}>
          <FaArrowLeft className="back_icon" />
        </button>
        <div className="post_header">
          <h2 className="post_title">{thisPost.postTitle}</h2>
          {thisPost.userId === sessionId && (
            <button className="options_button" onClick={() => toggleMenu("post")}>
              <FaEllipsisV />
            </button>
          )}
          {activeMenu === "post" && (
            <div className="dropdown_menu post_dropdown">
              <button onClick={handleModify}>수정</button>
              <button onClick={handleDel}>삭제</button>
            </div>
          )}
        </div>
        <hr className="post_divider" />
        <div className="post_info">
          <span>{thisPost.userId}</span>
          <span>조회수: {thisPost.postHit}</span>
          <span>{thisPost.postDate}</span>
        </div>
        <div className="post_content">
          {thisPost.postContent}
        </div>
        <div className="comment_section">
          {renderReplies(reply)}
          <ReplyInputCom 
              comment={comment} 
              setComment={setComment} 
              handleAddReply={handleAddReply}
              fetchThisPost={fetchThisPost} />
        </div>
      </div>
    );
  



    return (
        <div className="main_container">
          <Banner />
          <div className="sidebar_and_content">
            <SideBar />
            <div className="main_content">
              {isEditing? renderEditMode() : renderViewMode()}
            </div>
          </div>
        </div>
    );
}

export default BoardOneCom;
