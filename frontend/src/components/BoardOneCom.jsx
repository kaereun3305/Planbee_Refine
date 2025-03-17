import axios from 'axios';
import React, { useEffect, useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom';
import Banner from './Banner';
import SideBar from './SideBar';

const BoardOneCom = () => {
    const sessionId = "팥붕"; // TODO: 실제 로그인 정보에서 가져오도록 변경
    const navigate = useNavigate(); 
    const {id} = useParams();
    console.log("receivedId:", id);

    const [thisPost, setThisPost] = useState({});
    const [reply, setReply] = useState([]);
    const [isEditing, setIsEditing] = useState(false);
    const [editedPost, setEditedPost] = useState({ postTitle: "", postContent: "" });

    const handleGoBack = () => navigate(-1);
    const handleModify = () => setIsEditing(true);
    const handleChange = (e) => setEditedPost({ ...editedPost, [e.target.name]: e.target.value });
    const handleSave = () => {
      axios.put(
        `http://localhost:8080/planbee/board/boardModi/${id}`,{
          postTitle: editedPost.title,
          postContent: editedPost.contents,
        },
        {
          withCredentials:true,
        }
      ).then(response=> {
        console.log("게시글 수정 성공", response);
        setIsEditing(false);
        window.location.reload();
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
          axios.delete(`http://localhost:8080/planbee/board/boardDel/${id}`,{
            withCredentials:true,
            
          })
          setThisPost({});
            console.log({id}, "번 글 삭제되었습니다");
           navigate("/boardList/");
        } else {
            console.log("삭제 취소");
        }
    };

    useEffect(() => {
        const fetchThisPost = async () => {
            try {
                const response = await axios.get(
                    `http://localhost:8080/planbee/board/viewOne/${id}`,
                    { withCredentials: true }
                );
                setThisPost(response.data);
                console.log("thisPost", response.data);
            } catch (error) {
                console.log("이 게시글 로딩 에러", error);
            }
        };

        fetchThisPost(); // useEffect 내부에서 실행
    }, [id]);

    useEffect(() => {
        if (thisPost.postTitle && thisPost.postContent) { 
            setEditedPost({ title: thisPost.postTitle, contents: thisPost.postContent });
        }
    }, [thisPost]); //thisPost가 변경될 때 실행

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
                      <span>{thisPost?.userId}</span> 
                      <span>{thisPost?.postHit}</span> 
                      <span>{thisPost?.postDate}</span>
                    </div>
                    <p>{thisPost.postContent}</p>
                    <p style={{ textAlign: 'right' }}>
                      <a style={{ marginRight: '10px', cursor: 'pointer' }} onClick={handleGoBack}>목록보기</a>
                      {thisPost?.userId && thisPost.userId === sessionId && (
                        <>
                          <a style={{ marginRight: '10px', cursor: 'pointer' }} onClick={handleModify}>수정</a>
                          <a style={{ marginRight: '10px', cursor: 'pointer' }} onClick={() => handleDel(thisPost.postId)}>삭제</a>
                        </>
                      )}
                    </p>
                    <hr />
                    <div className="comments">
                      {/* {commentView} */}
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
