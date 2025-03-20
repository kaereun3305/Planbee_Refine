import React, { useEffect, useState } from 'react'
import Banner from '../components/Banner'
import SideBar from '../components/SideBar'
import SocialCom from '../components/SocialCom'
import BoardListCom  from '../components/BoardListCom';
import axios from 'axios'
import '../css/Main.css';


const Social = () => {
  
  const [data, setdata] = useState(null); //객체가 들어올 예정이라면 초기화는 null이나{빈객체}로 하는 것이 좋다고
  const [loading, setLoading]= useState(true);
  const [sessionId, setSessionId] = useState('')
      useEffect(() => {
        const makeSession = async () => {
          try {
            const response = await axios.post(
              `http://localhost:8080/planbee/groups/makeSession`,
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
              `http://localhost:8080/planbee/groups/checkSession`,
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
                `http://localhost:8080/planbee/groups`, 
                {
                    withCredentials: true,
                }
            )
        //console.log("Social jsx api 실행결과:", response.data.redirectUrl)
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
          {data.groupId == null?(
            <SocialCom Info={data} sessionId={sessionId}/> //groupId가 null일 경우
          ) : (
            <BoardListCom Info={data} sessionId={sessionId}/> //groupId가 있을 경우
          )}
        </div>
      </div>
    </div>
  )
}

export default Social
