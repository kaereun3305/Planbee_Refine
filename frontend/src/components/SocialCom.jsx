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
    const [groups, setGroups] = useState([]);
    
    
    useEffect(()=>{
      const fetchGroupsList = async () => { //모든 그룹 가져오는 api 연결하기
        
        
        // try {
        //     const response = await axios.get(
        //         `해당API주소`,
        //         {
        //             withCredentials: true
        //         }
        //     )
        // } catch (error) {
        //     console.log("group리스트 가져오기 실패", error)
            
        // }

        setGroups( [//하드코딩
          { id: 1, title: "PlanBEE", desc: "PlanBEE만드는 그룹입니다다" },
          { id: 2, title: "여행", desc: "도시여행하기 좋아하는 그룹입니다" },
          { id: 3, title: "취업", desc: "취업어렵다는데 할 수 있겠죠?" },
          { id: 4, title: "dev", desc: "developers" },
          { id: 5, title: "맛집", desc: "맛집 공유해요!" },
          { id: 6, title: "요새 고민", desc: "고민상담방, 악플금지!" },
          { id: 7, title: "아무거나", desc: "아무거나 그룹" },
          { id: 8, title: "게임", desc: "게임에 대한 심도있는 대화를 합니다" },
          { id: 9, title: "나무위키", desc: "나무위키 수정하는 사람들" },
        ])
    }
    fetchGroupsList(); //처음 한 번만 실행하게 됨
    },[])
   

    const handleOpenModal = (data) =>{
        console.log("선택된 것", data)
        setSelectedGroup(data);
        console.log("selectedGroup", selectedGroup)
        setIsJoinOpen(true);
    };
    useEffect(()=>{
      console.log("selectedGroup 변경", selectedGroup)
    },[selectedGroup])

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
