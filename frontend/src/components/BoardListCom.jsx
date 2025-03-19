import React, { useEffect, useState } from 'react'
import { Link, useNavigate } from 'react-router-dom';
import axios from 'axios';
import Banner from './Banner';
import SideBar from './SideBar';
import { option } from 'framer-motion/client';

const BoardListCom = ({joinedGroup, groupData, sessionId}) => {

    console.log("BoardListCom social에서 받아온 글 정보들", groupData)
    const navigate = useNavigate();
    const [currentView, setCurrentTeam] = useState("list");

    const [currentTeam, SetCurrentTeam] = useState("joinedGroup");
    const [isEditing, setIsEditing] = useState(false); //수정 상태 변경
    const [postTitle, setPostTitle] = useState(''); //수정을 위한 제목
    const [postContent, setPostContent] = useState(''); //수정을 위한 내용
    const [sortBy, setSortBy] = useState("최신순"); //정렬하기 위한 상태
    const sortOptions = ["최신순", "오래된 순", "조회수 많은 순"];
    const filteredOptions = sortOptions.filter(option => option !==sortBy)
    
    const [board, setBoard] = useState([]); //게시글 지정
    const [searchTerm, setSearchTerm] = useState(""); //검색키워드 지정
    const [isOpen, setIsOpen] = useState(false);

    const postData = groupData.posts.map(post=>({//받아온 정보 중에서 게시글 관련 정보만 postData에 정리리
      postId: post.postId,
      postTitle: post.postTitle,
      userId: post.userId,
      postDate: post.postDate,
    }))
    
    const handleToggle = () => setIsOpen(!isOpen); //드롭다운 열기,닫기 상태변경
    const handleOptionClick = (option) => {
      setSortBy(option);
      if (option === "최신순"){
        fetchLatestBoard();
      }else if (option ==="오래된 순"){
        fetchOldestBoard();
      }else if(option === "조회수 많은 순"){
        fetchMaxHitBoard();
      }
      console.log(`${option} 정렬 선택됨`);
    setIsOpen(false);
    };
  const handleSearchChange = (e) => setSearchTerm(e.target.value);
  const handleSearch = () => console.log(`검색어: ${searchTerm}`);


   
    // const fetchBoardList = async ()=>{ //게시판 글 가져오는 
    //   try {
    //     const response  = await axios.get( //세션아이디 기반으로 group글 가져오므로 동일한지 확인 필요없음
    //       `http://localhost:8080/planbee/group/${groupId}`,
    //       {
    //         withCredentials: true,
    //       }
    //       );
    //       console.log("게시글목록", response.data);
    //       setBoard(response.data); //결과를 보드에 등록
    //       console.log("MListCom board 길이 확인", board.length)
    //       } catch (error) {
    //         console.log("게시판 글가져오기 실패",error)
    //       }
    //     };

  // useEffect(()=>{
  //         fetchBoardList();
  // },[])

  
    const handleSave = () =>{ //글 수정 후 저장하는 기능
      console.log("제목:", postTitle);
      console.log("내용:", postContent);
      axios
      .post(
        `http://localhost:8080/planbee/board/boardWrite`,{
            "postTitle": postTitle,
            "postContent" : postContent
      },{
        withCredentials: true,
      })
      .then((response)=>{
        console.log("글 등록성공", response.data)
        setIsEditing(false);
        setPostTitle("");
        setPostContent("");
      })
      .catch((error)=>{
        console.log("글 등록 실패", error);
      })
     
    }
    const exitGroup = async () => { //그룹 탈퇴하는 코드
      try {
        console.log("현재가입그룹", )
      //   const response = await axios
      //   .put(
      //     `그룹에서 탈퇴하는 api 등록`
      //   ,{
      //     withCredentials: true,
      //   }
      // )
      SetCurrentTeam("");
      navigate("/social");
      console.log("그룹에서 탈퇴하였습니다");
      } catch (error) {
        console.log("그룹탈퇴 실패", error);
      }
    }
    const fetchLatestBoard = async () =>{ //최신순으로 정렬하는 코드
      try {
        await axios.get(
          `http://localhost:8080/plannbee/board/` //최신순순
        )
      } catch (error) {
        console.log("최신순으로 정렬 실패", error)
      }
    }
    const fetchOldestBoard = async () =>{ //오래된 순으로 정렬하는 코드
      try {
        await axios.get(
          `http://localhost:8080/planbee/board/`
        )
      } catch (error) {
        console.log("오래된 순 정렬 실패", error)
      }
    }
    const fetchMaxHitBoard = async () => { //조회수 많은 순으로 정렬하는 코드
      try {
        const response = await axios.get(
          `http://localhost:8080/planbee/board/maxHit`,
          {
            withCredentials: true,
          }
        )
        console.log("BoardListCom", response);
        setBoard(response.data)
      } catch (error) {
        console.log("조회수 많은 순으로 정렬 실패", error)
      }
    }
    const fetchSearchedBoard = () => { //검색어로 검색하는 코드드

    }
    const writePost = async () => {
      navigate()

    }
    



    return (
      <>
        <div className="main_content group_container">
          <div className="white_box">
            <div className="group_top_bar">
              <h2 className="group_name">{groupData.groupName}</h2>
    
              <div className="group_top_right">
                <span className="group_member_count">현재 인원 : {groupData.groupMemberCount}</span>
                <button className="leave_icon" onClick={() => exitGroup()}>
                  탈퇴하기
                </button>
    
                {/* 🔹 search_sort_box 내부에 input과 search_icon 이동 */}
                <div className="search_sort_box">
                  <div className="sort_button" onClick={handleToggle}>
                    {sortBy}
                    {isOpen && (
                      <div className="sort_dropdown">
                        {filteredOptions.map((opt) => (
                          <div
                            key={opt}
                            className="dropdown_option"
                            onClick={() => handleOptionClick(opt)}
                          >
                            {opt}
                          </div>
                        ))}
                      </div>
                    )}
                  </div>
    
                  
                  <input
                    type="text"
                    className="search_input"
                    value={searchTerm}
                    onChange={handleSearchChange}
                  />
                  <div className="search_icon" onClick={handleSearch}>
                    🔍
                  </div>
                </div>
              </div>
            </div>
    
            <hr className="group_black_line" />
    
            {postData.length === 0 ? (
              <div style={{ textAlign: 'center', padding: '50px', marginTop: '20px' }}>
                게시판에 글이 없습니다.
              </div>
            ) : (
              <>
                {postData.map((item) => {
                  return (
                    <div
                      key={item.postId}
                      style={{
                        border: '1px solid #ccc',
                        padding: '10px',
                        margin: '10px',
                        height: '100px',
                        overflowY: 'auto',
                      }}
                    >
                      <Link to={`/boardOne/${item.postId}`} state={{sessionId : sessionId}} style={{ cursor: 'pointer' }}>
                        {item.postTitle}
                      </Link>
                      <div style={{ display: 'flex', justifyContent: 'space-between' }}>
                        <div>
                          <span>작성자</span>
                          <span>{item.userId}</span>
                        </div>
                        <div>
                          <span>조회수0</span> {/*{item.postHit} */}
                          <span>{item.postDate}</span>
                        </div>
                      </div>
                    </div>
                  );
                })}
              </>
            )}
    
            <div className="write_icon" onClick={() => setIsEditing(true)}>
              <p className="placeholder" onClick={() => writePost()}>+ </p>
            </div>
          </div>
        </div>
      </>
    );
    
  
}
export default BoardListCom
