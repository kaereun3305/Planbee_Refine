import React, { useEffect, useState } from 'react'
import { Link, useNavigate } from 'react-router-dom';
import social from '../social';
import axios from 'axios';
import Banner from './Banner';
import SideBar from './SideBar';

const BoardListCom = () => {
    const [isEditing, setIsEditing] = useState(false);
    const [postTitle, setPostTitle] = useState('');
    const [postContent, setPostContent] = useState('');
    const navigate = useNavigate();
    const [board, setBoard] = useState([]);
    
 
        const fetchBoardList = async ()=>{
          try {
            const response  = await axios.get( //세션아이디 기반으로 group글 가져오므로 동일한지 확인 필요없음음
              `http://localhost:8080/planbee/board/boardGroup`,
              {
                withCredentials: true,
              }
            );
            console.log("게시글목록", response.data);
            setBoard(response.data); //결과를 보드에 등록
            console.log("MListCom board 길이 확인", board.length)
          } catch (error) {
            console.log("게시판 글가져오기 실패",error)
            setBoard("");
          }
        };
        
    fetchBoardList();
    const handleSave = () =>{
      console.log("제목:", postTitle);
      console.log("내용:", postContent);
      axios
      .post(
        `http://localhost:8080/planbee/board/boardWrite`,{
            "postTitle": postTitle,
            "postContent" : postContent
      },{
        withCredentials: true,
      })
      .then((response)=>{
        console.log("글 등록성공", response.data)
        setIsEditing(false);
        onclose();
        setPostTitle("");
        setPostContent("");
        fetchBoardList();
      })
      .catch((error)=>{
        console.log("글 등록 실패", error);
      })
     
    }
    



  return (
    <>
    <h1 style={{marginBottom:'30px'}}>PlanBEE Group Board</h1>
    
      {!board ? (
        <div stye={{textAlign:'center', padding:'50px', marginTop:'20px'}}>게시판에 글이 없습니다.</div> // div 태그 닫기
      ) : (
        <>
          
  
          {board.map((item) => {
            return (
              <div
                key={item.id}
                style={{
                  border: '1px solid #ccc',
                  padding: '10px',
                  margin: '10px',
                  height: '100px', // 세로 크기 늘리기
                  overflowY: 'auto',
                }}
              >
                <Link to={`/boardOne/${item.postId}`} style={{ cursor: 'pointer' }}>
                  {item.postTitle}
                </Link>
                <div style={{ display: 'flex', justifyContent: 'space-between' }}>
                  <div>
                    <span>작성자</span>
                    <span>{item.userId}</span>
                  </div>
                  <div>
                    <span>{item.postHit}</span>
                    <span>{item.postDate}</span>
                  </div>
                </div>
              </div>
            );
          })}
          
        </>
      )}
      <div className="write-box" onClick={() => setIsEditing(true)}>
                {isEditing ? (
                    <div className="edit-mode">
                        <input 
                            type="text"
                            placeholder="제목을 입력하세요"
                            value={postTitle}
                            onChange={(e) => setPostTitle(e.target.value)}
                        /><br/>
                        <textarea 
                            placeholder="내용을 입력하세요"
                            value={postContent}
                            onChange={(e) => setPostContent(e.target.value)}
                        />
                        <button onClick={handleSave}>등록</button>
                        <button onClick={() => setIsEditing(false)}>취소</button>
                    </div>
                ) : (
                    <p className="placeholder">+ 새로운 글을 작성하려면 클릭하세요.</p>
                )}
            </div>
    </>
  );
  
}
export default BoardListCom
