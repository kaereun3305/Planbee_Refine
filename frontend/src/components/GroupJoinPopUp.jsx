import React, { useState } from 'react'
import { useNavigate } from 'react-router-dom';

const GroupJoinPopUp = ({groupN, onClose}) => {

    const [isJoined, setIsJoined] = useState(false);
    const groupName = "PlanBEE"; //하드코딩 해둔 자료료
    const navigate = useNavigate(); 
  
    const handleYesClick = () => {
        // “가입하셨습니다” 화면을 잠시 보여준 뒤 이동할 수도 있지만,
        // 바로 group 페이지로 이동하고 싶다면 navigate("/group") 호출
        navigate("/board");
      };

      const handleNoClick = () => {
        alert("가입이 취소되었습니다!");
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
                <button className="join_yes" onClick={handleYesClick}>
                    YES
                </button>
                <button className="join_no" onClick={handleNoClick}>
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
