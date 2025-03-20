import React, { useEffect, useState } from 'react'
import { Link, useNavigate } from 'react-router-dom';
import axios from 'axios';
import Banner from './Banner';
import SideBar from './SideBar';
import { option } from 'framer-motion/client';
import '../css/BoardList.css';
import Board from '../pages/Board';

const BoardListCom = ({Info}) => {

    console.log("BoardListCom 에 도착한 글 정보들", Info)
    const navigate = useNavigate();
    const [currentView, setCurrentTeam] = useState("list");
    const [requestUrl, setRequestUrl] = useState(Info.redirectUrl); //리다이렉트되는 url을 저장
    const [thisGroupId, setThisGroupId] = useState(Info.groupId);//그룹아이디 받은거 저장
    const [thisGroupInfo, setThisGroupInfo] = useState(null);
    const [board, setBoard] = useState(null); //게시글 지정
    const [currentTeam, SetCurrentTeam] = useState("joinedGroup");
    const [isEditing, setIsEditing] = useState(false); //수정 상태 변경
    const [postTitle, setPostTitle] = useState(''); //수정을 위한 제목
    const [postContent, setPostContent] = useState(''); //수정을 위한 내용
    const [sortBy, setSortBy] = useState("최신순"); //정렬하기 위한 상태
    const sortOptions = ["최신순", "오래된 순", "조회수 많은 순"];
    const filteredOptions = sortOptions.filter(option => option !==sortBy)
    
    
   
    const [searchTerm, setSearchTerm] = useState(""); //검색키워드 지정
    const [isOpen, setIsOpen] = useState(false);

    // const postData = data.posts.map(post=>({//받아온 정보 중에서 게시글 관련 정보만 postData에 정리리
    //   postId: post.postId,
    //   postTitle: post.postTitle,
    //   userId: post.userId,
    //   postDate: post.postDate,
    // }))
    const fetchBoardList = async ()=>{ //게시판 글 가져오는 
      try {
        const response  = await axios.get( //세션아이디 기반으로 group글 가져오므로 동일한지 확인 필요없음
          `http://localhost:8080/${requestUrl}`,
          {
            withCredentials: true,
          }
          );
          console.log("게시글목록", response.data);
          if (response.data && response.data.posts) {
            setBoard(response.data.posts);
            setThisGroupInfo(response.data);
          }
          console.log("일단 저장", thisGroupInfo);
          } catch (error) {
            console.log("게시판 글가져오기 실패",error)
          }
        };

  useEffect(()=>{
    fetchBoardList();
  },[])
  useEffect(() => {
    // groupData가 있고 posts 배열이 존재하면 board에 저장합니다.
    
  }, [Info]);

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
    // 검색 드롭다운 관련
    const [isSearchOpen, setIsSearchOpen] = useState(false);
    const [searchType, setSearchType] = useState("최신순");

    const handleSearchToggle = () => {
      setIsSearchOpen(!isSearchOpen);
    };

    const handleSearchOptionClick = (option) => {
      setSearchType(option);
      setIsSearchOpen(false);
    };

// 정렬 드롭다운 관련
const [sortOption, setSortOption] = useState("정렬");

  const handleSearchChange = (e) => setSearchTerm(e.target.value);
  const handleSearch = () => console.log(`검색어: ${searchTerm}`);


   
    

  
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
    // const exitGroup = async () => { //그룹 탈퇴하는 코드
    //   try {
    //     console.log("현재가입그룹", )
    //     const response = await axios
    //     .put(
    //       // `http://localhost:8080/planbee/group/${groupId}/leave`
    //     ,{
    //       withCredentials: true,
    //     }
    //   )
    //   SetCurrentTeam("");
    //   navigate("/social");
    //   // console.log({groupId} , "그룹에서 탈퇴하였습니다");
    //   } catch (error) {
    //     console.log("그룹탈퇴 실패", error);
    //   }
    // }
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
    const fetchSearchedBoard = () => { //검색어로 검색하는 코드

    }
    const writePost = async () => {
      navigate()

    }
    



    return (
      <>
        {/* 게시판 영역: white_box */}
        <div className="white_box">
          {/* 그룹 상단 바 */}
          <div className="group_top_bar">
            <h2 className="group_name">
              {thisGroupInfo ? thisGroupInfo.groupName : ""}
              {/* thisGroupInfo가 없으면 빈 문자열 */}
            </h2>
            <div className="group_top_right">
              <span className="group_member_count">
                현재 인원 : {thisGroupInfo ? thisGroupInfo.groupMemberCount : ""}
              </span>
              <div className="group_drop">
                <button className="leave_icon" onClick={() => { /* 그룹 탈퇴 로직 추가 */ }}>
                  탈퇴하기
                </button>
              </div>
                <div className="search_dropdown" onClick={handleSearchToggle}>
                  {searchType}
                  {isSearchOpen && (
                    <div className="search_dropdown_menu">
                      <div className="dropdown_option" onClick={() => handleSearchOptionClick("최신순")}>
                        최신순
                      </div>
                      <div className="dropdown_option" onClick={() => handleSearchOptionClick("오래된 순")}>
                        오래된 순
                      </div>
                      <div className="dropdown_option" onClick={() => handleSearchOptionClick("조회수 많은 순")}>
                        조회수 많은 순
                      </div>
                    </div>
                  )}
                </div>
             
              <div className="group_sort">
                <div className="sort_button" onClick={handleToggle}>
                  {sortBy}
                  {isOpen && (
                    <div className="sort_dropdown visible">
                      <div className="dropdown_option" onClick={() => handleOptionClick("제목")}>
                        제목
                      </div>
                      <div className="dropdown_option" onClick={() => handleOptionClick("내용")}>
                        내용
                      </div>
                    </div>
                  )}
                </div>
                {/* 검색창 */}
                <div className="group_search">
                  <input
                    type="text"
                    className="search_input"
                    value={searchTerm}
                    onChange={handleSearchChange}
                  />
                  <div className="search_icon" onClick={handleSearch}>
                    <img src="../img/search_icon.png" alt="search icon" />
                  </div>
                </div>
              </div>
            </div>
          </div>
          <hr className="group_black_line" />
          {/* 게시글 목록 */}
          {board && board.length === 0 ? (
            <div style={{ textAlign: "center", padding: "50px", marginTop: "20px" }}>
              게시판에 글이 없습니다.
            </div>
          ) : (
            board?.map((item) => (
              <div
                key={item.postId}
                style={{
                  border: "1px solid #ccc",
                  padding: "10px",
                  margin: "10px",
                  height: "100px",
                  overflowY: "auto",
                }}
              >
                <Link to={`/boardOne/${item.postId}`} style={{ cursor: "pointer" }}>
                  {item.postTitle}
                </Link>
                <div style={{ display: "flex", justifyContent: "space-between" }}>
                  <div>
                    <span>작성자 </span>
                    <span>{item.userId}</span>
                  </div>
                  <div>
                    <span>조회수 0 </span>
                    <span>{item.postDate}</span>
                  </div>
                </div>
              </div>
            ))
          )}
          {/* 글쓰기 아이콘 */}
          <div className="write_icon" onClick={writePost}>
            <div>+</div>
          </div>
        </div>
        {/* 글쓰기 모달 (isEditing true일 때) */}
        {isEditing && (
          <div className="modal_overlay">
            <div className="modal_content">
              <h2>글쓰기</h2>
              <input
                type="text"
                placeholder="제목"
                value={postTitle}
                onChange={(e) => setPostTitle(e.target.value)}
              />
              <textarea
                placeholder="내용"
                value={postContent}
                onChange={(e) => setPostContent(e.target.value)}
              />
              <button onClick={handleSave}>등록</button>
              <button onClick={() => setIsEditing(false)}>취소</button>
            </div>
          </div>
        
        )}
      </>
    );
  
}
export default BoardListCom
