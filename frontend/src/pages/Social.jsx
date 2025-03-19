import React, { useEffect, useState } from 'react'
import Banner from '../components/Banner'
import SideBar from '../components/SideBar'
import SocialCom from '../components/SocialCom'
import BoardListCom  from '../components/BoardListCom';
import axios from 'axios'


const Social = () => {
  
  const [data, setdata] = useState(null);
  const [loading, setLoading]= useState(true);
  const [sessionId, setSessionId] = useState('')
      useEffect(() => {
        const makeSession = async () => {
          try {
            const response = await axios.post(
              `http://localhost:8080/planbee/group/makeSession`,
              null, // POST 요청 시 body를 전달할 필요 없으면 null
              {
                withCredentials: true, // 쿠키 전송을 허용
              }
            );
            console.log("세션 요청 여부:", response.data);
            setSessionId(response.data);
          } catch (error) {
            console.error("세션 fetching 실패!", error);
          }
        };
    
        const checkSession = async () => {
          try {
            const response = await axios.get(
              `http://localhost:8080/planbee/archive/checkSession`,
              {
                withCredentials: true,
              }
            );
            console.log("세션 확인 :", response.data);
          } catch (error) {
            console.error("에러", error);
          }
        };
      const checkIsJoined = async () =>{
        try {
            const response = await axios.get(
                `http://localhost:8080/planbee/group`, 
                {
                    withCredentials: true,
                }
            )
        console.log("Social jsx api 실행결과:", response.data)
        setdata(response.data); //결과값을 저장함
        } catch (error) {
            console.log("api실행 실패", error)
        } finally {
          setLoading(false);
        }
    }
    makeSession();
    checkSession();
    checkIsJoined();
    
    },[])
    

//세션확인해서 그룹아이디 있는지 체크
//값이 없을 경우에 소셜 컴포넌트트 띄우고,
//값이 있는 경우 바로 board 컴포넌트를 띄운다.

    if(loading) return <p> 로딩중...</p>

  return (
      <div className="main_container">
      <Banner />
      <div className="sidebar_and_content">
        <SideBar />
        <div className="main_content">
          {Array.isArray(data)?(
            <SocialCom groups={data} sessionId={sessionId}/> //현재 있는 게시판들을 보여줄 예정
          ) : (
            <BoardListCom groupData={data} sessionId={sessionId}/> //가입된 그룹의 게시판을 표시할 예정정
          )}
        </div>
      </div>
    </div>
  )
}

export default Social
