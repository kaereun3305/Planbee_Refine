import React, { useState } from 'react'
import { useNavigate } from 'react-router-dom';
import '../css/GroupJoin.css';
import { select } from 'framer-motion/client';

const GroupJoinPopUp = ({groupName, onClose}) => {

    const [isJoined, setIsJoined] = useState(false);
    const [isJoinOpen, setIsJoinOpen] = useState(true)
    const navigate = useNavigate(); 
    console.log("selectedGroup", groupName)
    const handleYesClick = () => {
        setIsJoined(true);
        navigate("/boardList");
      };

      const handleNoClick = () => {
        alert("가입이 취소되었습니다!");
        setIsJoinOpen(false);
        onClose();
        navigate("/social"); // 취소 시 Social로 이동(원하는 경로로)
      };
  
   return (
    <div className='popup_overlay'>
   <div className="join_container">  
    {!isJoined ? (
        <>
            <h2 className="join_title">
                {groupName}에 가입하시겠습니까?
            </h2>
            <div className="join_button_area">
                <button className="join_yes" onClick={()=>handleYesClick()}>
                    YES
                </button>
                <button className="join_no" onClick={() => handleNoClick()}>
                    NO
                </button>
            </div>
        </>
    ) : (
        <>
            <h2 className="join_title">
                {groupName}에 가입하신 걸 환영합니다!
            </h2>
        </>
    )}
</div>
</div> )
}

export default GroupJoinPopUp
