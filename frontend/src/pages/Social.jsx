import React, { useEffect, useState } from 'react'
import Banner from '../components/Banner'
import SideBar from '../components/SideBar'
import SocialCom from '../components/SocialCom'
import BoardListCom  from '../components/BoardListCom';
import axios from 'axios'


const Social = () => {
    const [myGroup, setMyGroup] = useState(null);
    // useEffect(()=>{
    //   const checkIsJoined = async () =>{ //해당 사람이 그룹에 가입되어있는지 확인하는 api연결
    //     try {
    //         const response = await axios.get(
    //             `해당 주소가 들어갑니다`, 
    //             {
    //                 withCredentials: true,
    //             }
    //         )
    //     console.log("가입그룹 검색결과:", response.data)
    //     setMyGroup(response.data)
    //     } catch (error) {
    //         console.log("가입되었는지 확인불가", error)
    //     }
    // }
    // checkIsJoined();
    
    // },[])
    

//세션확인해서 그룹아이디 있는지 체크
//값이 없을 경우에 소셜 컴포넌넌 띄우고,
//값이 있는 경우 바로 board 컴포넌트를 띄운다.



  return (
      <div className="main_container">
      <Banner />
      <div className="sidebar_and_content">
        <SideBar />
        <div className="main_content">
          <div className="social_container">
            {console.log("myGroup", myGroup)}
            {myGroup === null || myGroup === undefined ?
            (<SocialCom />) :
            (<BoardListCom joinedGroup={myGroup} />)
            }
          </div>
        </div>
      </div>
    </div>
  )
}

export default Social
