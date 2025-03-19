import React, { useEffect } from 'react'
import { useState, useNavigate } from 'react';
import { Link } from 'react-router-dom';
import '../css/SocialList.css';
import axios from 'axios';
import GroupJoinPopUp from './GroupJoinPopUp';
import { select } from 'framer-motion/client';

const SocialCom = ( {groups} ) => {
    const [isJoinOpen, setIsJoinOpen] = useState(false);
    const [selectedGroup, setSelectedGroup] = useState(null);
    
    const handleOpenModal = (data) =>{
        console.log("선택된 것", data)
        setSelectedGroup(data); //selectedGroup에 저장함
        console.log("selectedGroup", selectedGroup)
        setIsJoinOpen(true);
    };

    const handleCloseModal = () => {
      setIsJoinOpen(false);
  };
  
 if(!groups){
  return <div>Loading...</div>
 }
   
  


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
                    <div className="group_title">{group.groupName}</div>
                    <div className="group_desc">{group.groupKeywords}</div>
                    </div>
                    <div className="group_right">
                    
                    <button className="join_button" onClick={()=>handleOpenModal(group)}>
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
            {console.log("social컴포넌트", selectedGroup.groupName)}
            {console.log("social컴포넌트id", selectedGroup.groupId)}
            {isJoinOpen && <GroupJoinPopUp groupName={selectedGroup.groupName} 
            groupId= {selectedGroup.groupId}
            onClose={handleCloseModal} />}
            </>
  
    )

}

export default SocialCom
