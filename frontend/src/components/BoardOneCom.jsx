import axios from 'axios';
import React, { useEffect, useState } from 'react'
import { useNavigate, useParams, useLocation } from 'react-router-dom';
import Banner from './Banner';
import SideBar from './SideBar';

const BoardOneCom = () => {
    const sessionId = "팥붕"; //일단 하드코딩해둠
    const location = useLocation();
    const navigate = useNavigate(); 
    const {postId} = useParams(); //postId는 useParams에서 받아서 저장해둔다
    const thisGroupId = location.state; //groupId를 저장해두자.
    const [thisPost, setThisPost] = useState({});
    const [reply, setReply] = useState([]);
    const [isEditing, setIsEditing] = useState(false);
    const [editedPost, setEditedPost] = useState({ postTitle: "", postContent: "" });

    const fetchThisPost = async () => {
      try {
        const response = await axios.get(
          `http://localhost:8080/planbee/groups/${thisGroupId}/boards/${postId}`,
          {
            withCredentials:true,
          }
        )
        console.log("글 하나 불러오기결과", response.data)
      } catch (error) {
        
      }
    }
    const handleHit = () =>{
      try {
        axios.put(
          `http://localhost:8080/planbee/groups/${thisGroupId}/boards/${postId}/hit`,{
            withCredentials: true,
          }
        )
      } catch (error) {
        console.log("조회수 증가 실패", error)
      }
      
    }
    
    useEffect(()=>{
      handleHit();

    },[])
    const handleGoBack = () => navigate(-1);
    const handleModify = () => setIsEditing(true);
    const handleChange = (e) => setEditedPost({ ...editedPost, [e.target.name]: e.target.value });
    
    const handleSave = () => {
      axios.put(
        `http://localhost:8080/planbee/board/boardModi/${postId}`,{
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
      navigate(-1);
    }

    const handleDel = (id) => {
      console.log("삭제버튼 클릭: 번호", id)
        if (window.confirm("삭제하시겠습니까?")) {
          axios.delete(`http://localhost:8080/planbee/groups/${thisGroupId}/${postId}`,{
            withCredentials:true,
            
          })
          setThisPost({});
            console.log({postId}, "번 글 삭제되었습니다");
           navigate(`/boardList/${thisGroupId}`);
        } else {
            console.log("삭제 취소");
        }
    };
    
    

    // useEffect(() => {
    //     const fetchThisPost = async () => {
    //         try {
    //             const response = await axios.get(
    //                 `http://localhost:8080/planbee/board/${groupId}/viewOne/${id}`,
    //                 { withCredentials: true }
    //             );
    //             setThisPost(response.data);
    //             console.log("thisPost", response.data);
    //         } catch (error) {
    //             console.log("이 게시글 로딩 에러", error);
    //         }
    //     };

    //     fetchThisPost(); // useEffect 내부에서 실행
    // }, [id]);

    useEffect(() => {
        if (thisPost.postTitle && thisPost.postContent) { 
            setEditedPost({ title: thisPost.postTitle, contents: thisPost.postContent });
        }
    }, [thisPost]); //thisPost가 변경될 때 실행

    const commentView = reply.map(reply =>{ //코멘트 보여주는 기능
      return(
          <div class="comment">
          <span class="username">{reply.userId}</span>
          <span class="comment-text">{reply.content}</span>
          <span class="time">{reply.date}</span>
           </div>)
      })


    return (
        <div className="main_container">
          <Banner />
          <div className="sidebar_and_content">
            <SideBar />
            <div className="main_content">
              <div className="boardOneView">
                {isEditing ? (
                  <>
                    <input
                      type="text"
                      name="title"
                      value={editedPost.title}
                      onChange={handleChange}
                      style={{ width: '100%', marginBottom: '10px' }}
                    />
                    <textarea
                      name="contents"
                      value={editedPost.contents}
                      onChange={handleChange}
                      rows="5"
                      style={{ width: '100%' }}
                    />
                    <a style={{ marginRight: '10px', cursor: 'pointer' }} onClick={handleSave}>
                      저장
                    </a>
                    <a style={{ marginRight: '10px', cursor: 'pointer' }} onClick={handleCancel}> 취소 </a>
                  </>
                ) : (
                  <>
                    <h2>{thisPost.postTitle}</h2>
                    <hr />
                    <div className="post-info">
                      <span style={{ marginRight: '10px' }}>{thisPost?.userId}</span> 
                      <span style={{ marginRight: '10px' }}>{thisPost?.postHit}</span> 
                      <span>{thisPost?.postDate}</span>
                    </div>
                    <p>{thisPost.postContent}</p>
                    <p style={{ textAlign: 'right' }}>
                      <a style={{ marginRight: '10px', cursor: 'pointer' }} onClick={handleGoBack}>목록보기</a>
                      {thisPost?.userId && thisPost.userId == {sessionId} && (
                        <>
                          <a style={{ marginRight: '10px', cursor: 'pointer' }} onClick={() => handleModify(thisPost.postId)}>수정</a>
                          <a style={{ marginRight: '10px', cursor: 'pointer' }} onClick={() => handleDel(thisPost.postId)}>삭제</a>
                        </>
                      )}
                    </p>
                    <hr />
                    <div className="comments">
                      {!reply || reply.length ===0 ?
                      (null) :commentView}
                    </div>
                  </>
                )}
              </div>
            </div>
          </div>
        </div>
    );
}

export default BoardOneCom;
