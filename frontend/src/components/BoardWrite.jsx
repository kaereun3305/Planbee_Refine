import React from 'react'
import Banner from './Banner'
import SideBar from './SideBar'
import { useNavigate } from 'react-router-dom';

const BoardWrite = () => {
    const navigate = useNavigate();
    const [title, setTitle] = useState("");
    const [content, setContent] = useState("");

    const [showProgress, setShowProgress] = useState(false);

     
  const handleShowProgress = () => {// "진척도 가져오기" 버튼
    setShowProgress(true);
  };

  const handleHideProgress = () => {// "닫기" 버튼
    setShowProgress(false);
  };



    const handleAddPost = async () =>{
        try {
            const response = await axios.post(
              `http://localhost:8080/planbee/board/boardWrite`,
                {
                  "postTitle": postTitle,
                 "postContent": postContent
               }
              ,{
                withCredentials: true,
              }
            )
            console.log("제목", postTitle);
            console.log("내용", postContent)
          } catch (error) {
            console.log("글쓰기 실패",error)
          }
    }
    




  return (
    <div className="main_container">
    <Banner />
    <div className="sidebar_and_content">
      <SideBar />
      <div className="main_content">
        <div className="boardWrite_container">
         
          
        </div>
      </div>
    </div>
  </div>
  )
}

export default BoardWrite
