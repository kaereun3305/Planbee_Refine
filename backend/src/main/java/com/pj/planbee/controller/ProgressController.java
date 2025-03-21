package com.pj.planbee.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pj.planbee.dto.BoardDTO;
import com.pj.planbee.dto.ProgressShareDTO;
import com.pj.planbee.dto.UserProgressDTO;
import com.pj.planbee.service.BoardService;
import com.pj.planbee.service.GroupService;
import com.pj.planbee.service.ProgressService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", allowCredentials = "true")
@RequestMapping("/groups")
public class ProgressController {

	// http://localhost:8080/planbee/groups/1/boards

	@Autowired
	ProgressService ps;
	@Autowired
	BoardService bs;
	@Autowired
	GroupService gs;
	@Autowired
	HttpSession se;

	@PostMapping(value = "/{groupId}/boards/daily", produces = "application/json; charset=utf-8")
	public int createDailyProgressPost(HttpSession session, @PathVariable int groupId,
			@RequestParam(value = "date", required = false) String date) {
		String sessionId = (String) session.getAttribute("sessionId");

		// date가 없으면 현재 날짜(YYMMDD 형식) 사용
		if (date == null || date.isEmpty()) {
			date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd"));
		}

		// 진척도 데이터 생성
		ProgressShareDTO progressDTO = ps.getDailyProgress(sessionId, date);
		String progressHtml = ps.generateProgressHtml(progressDTO);

		// 게시글 DTO 생성
		BoardDTO dto = new BoardDTO();
		dto.setUserId(sessionId);
		dto.setGroupId(groupId);
		dto.setPostTitle(sessionId + "님의 오늘 진척도"); // 제목: "~님의 진척도"
		dto.setPostContent(progressHtml); // 내용: 진척도 HTML

		// 게시글 저장
		return bs.writePost(dto);
	}

	@PostMapping(value = "/{groupId}/boards/weekly", produces = "application/json; charset=UTF-8")
	public int createWeeklyProgressPost(HttpSession session, @PathVariable int groupId) {
		String sessionId = (String) session.getAttribute("sessionId");

		// 주간 진척도 데이터 생성
		String progressHtml = ps.getWeeklyProgress(sessionId);

		// 게시글 DTO 생성
		BoardDTO dto = new BoardDTO();
		dto.setUserId(sessionId);
		dto.setGroupId(groupId);
		dto.setPostTitle(sessionId + "님의 주간 진척도"); // 제목: "~님의 주간 진척도"
		dto.setPostContent(progressHtml); // 내용: 주간 진척도 HTML

		// 게시글 저장
		return bs.writePost(dto);
	}

	@PostMapping("/auto-post/{groupId}")
	public ResponseEntity<String> createAutoPost(@PathVariable int groupId) {
	        String groupName = gs.getGroupName(groupId);
	        String content = generateMonthlyRankingContent(groupId);

	        BoardDTO dto = new BoardDTO();
	        dto.setPostTitle(groupName + " 그룹 월간 순위");
	        dto.setPostContent(content);
	        dto.setUserId(groupName); // 작성자
	        dto.setGroupId(groupId);

	        bs.writePost(dto);
	        return ResponseEntity.ok(groupName + " 그룹의 월간 순위 게시글이 자동으로 등록되었습니다.");
	    
	}
	
	 private String generateMonthlyRankingContent(int groupId) {
	        String lastMonth = LocalDate.now().minusMonths(1).format(DateTimeFormatter.ofPattern("yyMM"));

	        List<UserProgressDTO> ranking = ps.getGroupMonthlyProgressRanking(groupId, lastMonth);
	        List<Map<String, Object>> maxStreaks = ps.getGroupMaxStreaks(groupId);
	        List<Map<String, Object>> curStreaks = ps.getGroupCurrentStreaks(groupId);

	        StringBuilder content = new StringBuilder();
	        content.append("📅 **").append(LocalDate.now().minusMonths(1).getMonthValue()).append("월 순위**\n\n");
	        content.append("🏆 **월간 평균 진척도 순위**\n");

	        for (int i = 0; i < ranking.size(); i++) {
	            String medal = (i == 0) ? "🥇" : (i == 1) ? "🥈" : (i == 2) ? "🥉" : "📉";
	            content.append(medal).append(" ").append(ranking.get(i).getUserId())
	                   .append(" - ").append(ranking.get(i).getProgress()).append("%\n");
	        }

	        content.append("\n🔥 **역대 최대 스트릭 순위**\n");
	        for (int i = 0; i < Math.min(3, maxStreaks.size()); i++) {
	            content.append((i == 0 ? "🥇" : i == 1 ? "🥈" : "🥉"))
	                   .append(" ").append(maxStreaks.get(i).get("user_id"))
	                   .append(" - ").append(maxStreaks.get(i).get("streakDays")).append("일\n");
	        }

	        content.append("\n💡 **현재 최대 스트릭 순위**\n");
	        for (int i = 0; i < Math.min(3, curStreaks.size()); i++) {
	            content.append((i == 0 ? "🥇" : i == 1 ? "🥈" : "🥉"))
	                   .append(" ").append(curStreaks.get(i).get("user_id"))
	                   .append(" - ").append(curStreaks.get(i).get("streakDays")).append("일\n");
	        }

	        return content.toString();
	    }

}
