let board=[
    {id:"1", title:"첫글입니다", contents:"안녕하세요 첫 글 작성해보겠습니다", date:"25-03-13 15:28", hit:"0", userId: "팥붕", groupId:"1"},
    {id:"2", title:"두번째글입니다", contents:"점심 뭐드실래요?", date:"25-03-13 15:30", hit:"0", userId: "슈붕", groupId:"1"},
    {id:"3", title:"세번째글입니다", contents:"팥붕어빵이요....", date:"25-03-13 15:31", hit:"0", userId: "배즙", groupId:"2"},
    {id:"4", title:"네번째글입니다", contents:"아뇨 저는 포케먹고싶어요", date:"25-03-13 15:40", hit:"0", userId: "coffeeNine", groupId:"2"},
    {id:"5", title:"다섯번째글입니다", contents:"집에 가고싶다", date:"25-03-13 15:50", hit:"0", userId: "userId", groupId:"1"},
    {id:"6", title:"하하 수정삭제제", contents:"왜 되지? 왜 안되지?", date:"25-03-13 16:00", hit:"0", userId: "팥붕", groupId:"1"},

]

let comments = [
    {replyId:"1", content:"이 분 뭐하시는 분이져?", date:"25-03-14 10:00", rep_id:"", depth:"1", userId:"슈붕", postId:"1"},
    {replyId:"2", content:"원래 인생이 그런거죠", date:"25-03-14 10:00", rep_id:"", depth:"1", userId:"팥붕", postId:"1"},
    {replyId:"3", content:"배고픔을 참아봅시다", date:"25-03-14 09:00", rep_id:"", depth:"1", userId:"팥붕", postId:"2"},
    {replyId:"4", content:"커피마셔도 잠이 안깨요", date:"25-03-14 10:00", rep_id:"", depth:"1", userId:"댓댓", postId:"2"},
    {replyId:"5", content:"엉엉 나 할 수 있을까?", date:"25-03-14 10:00", rep_id:"", depth:"1", userId:"안녕", postId:"2"},
    {replyId:"6", content:"아 반반치킨 먹고싶다", date:"25-03-14 10:00", rep_id:"", depth:"1", userId:"coffeeNine", postId:"3"},
    {replyId:"7", content:"이 게시판 좀 팬시하네요", date:"25-03-14 10:00", rep_id:"", depth:"1", userId:"흑흑", postId:"3"},
    {replyId:"8", content:"머라도 먹어야겠어요요", date:"25-03-14 10:00", rep_id:"", depth:"2", userId:"토리", postId:"3"}




]
let groupMember = [
    {userId: "팥붕", groupId: "1"},
    {userId: "슈붕", groupId: "1"},
    {userId: "댓댓", groupId: "2"},
    {userId: "흑흑", groupId: "2"}


]
let groupData = [
    { id: 1, title: "PlanBEE", desc: "PlanBEE만드는 그룹입니다다" },
    { id: 2, title: "여행", desc: "도시여행하기 좋아하는 그룹입니다" },
    { id: 3, title: "취업", desc: "취업어렵다는데 할 수 있겠죠?" },
    { id: 4, title: "dev", desc: "developers" },
    { id: 5, title: "맛집", desc: "맛집 공유해요!" },
    { id: 6, title: "요새 고민", desc: "고민상담방, 악플금지!" },
    { id: 7, title: "아무거나", desc: "아무거나 그룹" },
    { id: 8, title: "게임", desc: "게임에 대한 심도있는 대화를 합니다" },
    { id: 9, title: "나무위키", desc: "나무위키 수정하는 사람들" },
  ];
const boardList = () => board;
const reply = () => comments;
const groupMem = () => groupMember;
const groups = () => groupData;


export default {boardList, reply, groupMem, groups};