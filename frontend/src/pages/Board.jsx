import React from 'react'
import Banner from '../components/Banner'
import SideBar from '../components/SideBar'
import BoardOneCom from '../components/BoardOneCom'
import axios from 'axios'


const Board = () => {
    const checkIsJoined = 1;
    // = async () =>{
    //     try {
    //         const response = await axios.get(
    //             `해당 주소가 들어갑니다`, 
    //             {
    //                 withCredentials: true,
    //             }
    //         )

    //     } catch (error) {
    //         console.log("가입되었는지 확인불가", error)
    //     }
    //}

//세션확인해서 그룹아이디 있는지 체크
//값이 없을 경우에 소셜 컴포넌넌 띄우고,
//값이 있는 경우 바로 board 컴포넌트를 띄운다.

//가져온다

  return (
      <div className="main_container">
      <Banner />
      <div className="sidebar_and_content">
        <SideBar />
        <div className="main_content">
          <div className="social_container">
            <BoardOneCom />
          </div>
        </div>
      </div>
    </div>
  )
}

export default Social
