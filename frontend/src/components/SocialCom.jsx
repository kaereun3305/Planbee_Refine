import React from 'react'
import { useState, useNavigate } from 'react';
import { Link } from 'react-router-dom';
import '../css/SocialList.css';
import axios from 'axios';
import GroupJoinPopUp from './GroupJoinPopUp';
import social from '../social'

const SocialCom = () => {
    const [isJoinOpen, setIsJoinOpen] = useState(false);
    const [selectedGroup, setSelectedGroup] = useState(null);
    const groups = social.groups();
    console.log(groups)
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
        setSelectedGroup(data);
        setIsJoinOpen(true);
    };

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
                    
                    <button className="join_button" onClick={()=> handleOpenModal(group.title)}>
                    <div style={{ textDecoration: "none", color: "inherit", cursor: "pointer" }} 
                      onClick={() => setIsJoinOpen(true)}>
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
