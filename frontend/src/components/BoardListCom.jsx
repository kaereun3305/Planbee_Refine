import React, { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom';
import social from '../social';
import axios from 'axios';

const BoardListCom = () => {
    const navigate = useNavigate;
    const [board, setBoard] = useState([]);
    useEffect(()=>{
        setBoard(social.boardList());
        // const fetchBoardList = async ()=>{
        //   try {
        //     const response  = await axios.get( //세션아이디 기반으로 group글 가져오므로 동일한지 확인 필요없음음
        //       `http://localhost:8080/planbee/board/boardGroup`,
        //       {
        //         withCredentials: true,
        //       }
        //     );
        //     console.log("게시글목록", response.data);
        //   } catch (error) {
        //     console.log("게시판 글가져오기 실패",error)
        //   }
        // };
        //fetchBoardList();
    },[])
    console.log("MListCom board 길이 확인", board.length)
    const response = [ //일단 임시데이터 넣어서 확인, 이후 이건 지우고 위에만 하면됨
      {id:"1", title:"첫글입니다", contents:"안녕하세요 첫 글 작성해보겠습니다", date:"25-03-13 15:28", hit:"0", userId: "팥붕", groupId:"1"},
      {id:"2", title:"두번째글입니다", contents:"점심 뭐드실래요?", date:"25-03-13 15:30", hit:"0", userId: "슈붕", groupId:"1"},
      {id:"3", title:"세번째글입니다", contents:"팥붕어빵이요....", date:"25-03-13 15:31", hit:"0", userId: "배즙", groupId:"2"},
      {id:"4", title:"네번째글입니다", contents:"아뇨 저는 포케먹고싶어요", date:"25-03-13 15:40", hit:"0", userId: "coffeeNine", groupId:"2"},
      {id:"5", title:"다섯번째글입니다", contents:"집에 가고싶다", date:"25-03-13 15:50", hit:"0", userId: "userId", groupId:"1"},
      {id:"6", title:"하하 수정삭제제", contents:"왜 되지? 왜 안되지?", date:"25-03-13 16:00", hit:"0", userId: "팥붕", groupId:"1"},

    ]
  const onePost=(id)=>{ //하나 누르면 id를 전달하면서 그 페이지로 이동함
    navigate(`/board/${id}`)
  }





  return (
    <>
    <h1>PlanBEE Group Board</h1>
    
    {response.map((item)=>{
      return(
      <div key={item.id} style={{ border: '1px solid #ccc', padding: '10px', margin: '10px', height: '100px', // 세로 크기 늘리기
        overflowY: 'auto' }}>
      <p style={{cursor:'pointer'}}onClick={()=>onePost(item.id)}>{item.title}</p>
      <div style={{ display: 'flex', justifyContent: 'space-between' }}>
        <div>
          <span>작성자</span>
          <span>{item.userId}</span>
        </div>
        <div>
          <span>{item.hit}</span>
          <span>{item.date}</span>
        </div>
      </div>
    </div>
      )
    })}
    </>
  )
}

export default BoardListCom
