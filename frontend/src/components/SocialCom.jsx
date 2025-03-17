import React, { useEffect } from 'react'
import { useState, useNavigate } from 'react';
import { Link } from 'react-router-dom';
import '../css/SocialList.css';
import axios from 'axios';
import GroupJoinPopUp from './GroupJoinPopUp';
import social from '../social'
import { select } from 'framer-motion/client';

const SocialCom = () => {
    const [isJoinOpen, setIsJoinOpen] = useState(false);
    const [selectedGroup, setSelectedGroup] = useState(null);
    const groups = social.groups();
    //console.log(groups)
    // = () => {

    //     try {
    //         const response = await axios.get(
    //             `해당API주소`,
    //             {
    //                 withCredentails: true;
    //             }
    //         )
    //     } catch (error) {
    //         console.log("group리스트 가져오기 실패", error)
            
    //     }
    // }
    const handleOpenModal = (data) =>{
        console.log("선택된 것", data)
        setSelectedGroup(data);
        console.log("selectedGroup", selectedGroup)
        setIsJoinOpen(true);
    };
    useEffect(()=>{
      console.log("selectedGroup 변경", selectedGroup)
    },[selectedGroup])

    useEffect(() => {
      const makeSession = async () => {
        try {
          const response = await axios.post(
            `http://localhost:8080/planbee/board/makeSession`,
            null, // POST 요청 시 body를 전달할 필요 없으면 null
            {
              withCredentials: true, // 쿠키 전송을 허용
            }
          );
          console.log("세션 요청 여부:", response.data);
          console.log("쿠키확인", document.cookie);
        } catch (error) {
          console.error("세션 fetching 실패!", error);
        }
      };

      const checkSession = async () => {
        try {
          const response = await axios.get(
            `http://localhost:8080/planbee/todolist/checkSession`,
            {
              withCredentials: true,
            }
          );
          console.log("세션 확인 :", response.data);
        } catch (error) {
          console.error("에러", error);
        }
      };

      makeSession();
      checkSession();
    },[])

    const handleCloseModal = () => {
      setIsJoinOpen(false);
  };


    return(<>
            
            <div className="social_header">
            </div>
  
            {/* 소셜 컨테이너 */}
            <div className="social_container">
              {/* 그룹 리스트 */}
              
              <div className="group_list">
                  {groups.map((group) => (
                    <div key= {group.id} className="group_item">
                    <div className="group_left">
                    <div className="group_title">{group.title}</div>
                    <div className="group_desc">{group.desc}</div>
                    </div>
                    <div className="group_right">
                    
                    <button className="join_button" onClick={()=>handleOpenModal(group.title)}>
                    <div style={{ textDecoration: "none", color: "inherit", cursor: "pointer" }} >
                        Join
                    </div>
                    
                    </button>
                  
                    </div>
                    </div>
                  ))}
                 
  
              </div> 
              {/* end of group_list */}
            </div>
            {/* end of social_container */}
            {isJoinOpen && <GroupJoinPopUp groupName={selectedGroup} onClose={handleCloseModal} />}
            </>
  
    )
}

export default SocialCom
