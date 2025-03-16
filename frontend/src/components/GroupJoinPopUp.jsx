import React, { useState } from 'react'
import { useNavigate } from 'react-router-dom';

const GroupJoinPopUp = () => {

    const [isJoined, setIsJoined] = useState(false);
    const groupName = "Group 1"; //하드코딩 해둔 자료료
    const navigate = useNavigate(); 
  
    const handleYesClick = () => {
        // “가입하셨습니다” 화면을 잠시 보여준 뒤 이동할 수도 있지만,
        // 바로 group 페이지로 이동하고 싶다면 navigate("/group") 호출
        //navigate("/board");
      };

      const handleNoClick = () => {
        alert("가입이 취소되었습니다!");
        navigate("/social"); // 취소 시 Social로 이동(원하는 경로로)
      };
  
   return ( <>     
        
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
                {/* 만약 가입 완료 화면을 잠깐 보여주고 싶다면 여기서 처리.
                    현재는 handleYesClick에서 바로 /group으로 이동하므로
                    isJoined를 쓰지 않아도 돼. */}
                <h2 className="join_title">
                  {groupName}에 가입하신 걸 환영합니다!
                </h2>
              </>
            )}
          
        
          </> )
}

export default GroupJoinPopUp
