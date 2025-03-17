import React, { useEffect, useState } from 'react'
import { Link, useNavigate } from 'react-router-dom';
import social from '../social';
import axios from 'axios';
import Banner from './Banner';
import SideBar from './SideBar';

const BoardListCom = () => {
    const navigate = useNavigate();
    const [board, setBoard] = useState([]);
    useEffect(()=>{
        
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
        //임시데이터 넣을 경우
        // const response = [
        //   { postId: "1", postTitle: "첫글입니다", postContents: "안녕하세요 첫 글 작성해보겠습니다", postDate: "25-03-13 15:28", postHit: "0", userId: "팥붕", groupId: "1" },
        //   { postId: "2", postTitle: "두번째글입니다", postContents: "점심 뭐드실래요?", postDate: "25-03-13 15:30", postHit: "0", userId: "슈붕", groupId: "1" },
        //   { postId: "3", postTitle: "세번째글입니다", postContents: "팥붕어빵이요....", postDate: "25-03-13 15:31", postHit: "0", userId: "배즙", groupId: "2" },
        //   { postId: "4", postTitle: "네번째글입니다", postContents: "아뇨 저는 포케먹고싶어요", postDate: "25-03-13 15:40", postHit: "0", userId: "coffeeNine", groupId: "2" },
        //   { postId: "5", postTitle: "다섯번째글입니다", postContents: "집에 가고싶다", postDate: "25-03-13 15:50", postHit: "0", userId: "userId", groupId: "1" },
        //   { postId: "6", postTitle: "하하 수정삭제제", postContents: "왜 되지? 왜 안되지?", postDate: "25-03-13 16:00", postHit: "0", userId: "팥붕", groupId: "1" }
        // ]
        //setBoard(response);
        
        
    },[])
    
    
  // const onePost=(id)=>{ //하나 누르면 id를 전달하면서 그 페이지로 이동함
  //   navigate(`/board/${id}`)
  // }




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
    </>
  );
  
}
export default BoardListCom
