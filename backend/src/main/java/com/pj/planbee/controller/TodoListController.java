package com.pj.planbee.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.pj.planbee.dto.TDdetailDTO;
import com.pj.planbee.dto.TodoListDTO;
import com.pj.planbee.dto.TodoListDTO.SubTodoListDTO;
import com.pj.planbee.service.TodoListService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

@Api(value = "ToDoList API", description = "투두리스트 관련 API")
@RestController
@RequestMapping("/todolist")
@CrossOrigin(origins = "*", allowedHeaders = "*", allowCredentials = "true")
public class TodoListController {

    @Autowired
    TodoListService ts;

    @ApiOperation(value = "투두리스트 가져오기", notes = "YYMMDD 형식의 날짜를 입력하면 해당 날짜의 투두리스트 정보를 가져옵니다.")
    @GetMapping(value = "/getTodo/{tdDate}", produces = "application/json; charset=utf-8")
    public List<TDdetailDTO> getToday(
            @ApiParam(value = "YYMMDD 형식의 날짜 (예: 230315)", required = true) @PathVariable String tdDate,
            @ApiIgnore HttpSession se) {
        // 세션에서 sessionId를 가져옵니다.
        String sessionId = (String) se.getAttribute("sessionId");

        int todoId;
        int result = ts.checkRow(tdDate, sessionId); // 열이 있는지 확인

        if (result == 0) {
            ts.inputRow(tdDate, sessionId);
            todoId = ts.tdIdSearch(tdDate, sessionId); // 새로 생성 후 todoId 반환
        } else {
            todoId = result;
        }
        List<TDdetailDTO> list = new ArrayList<>();
        list = ts.getTodo(todoId);
        return list;
    }

    @ApiOperation(value = "투두리스트 작성", notes = "YYMMDD 형식의 날짜와 함께 투두리스트 세부내용을 작성합니다. 작성이 성공하면 입력된 번호(tdDetailId)를 반환합니다.")
    @PostMapping(value = "/write/{tdDate}", produces = "application/json; charset=utf-8")
    @ResponseBody
    public Map<String, Integer> todoWrite(
            @ApiParam(value = "투두리스트 작성 정보 (tdDetail, tdDetailTime, tdId)", required = true) @RequestBody TDdetailDTO dto,
            @ApiParam(value = "YYMMDD 형식의 날짜 (예: 230315)", required = true) @PathVariable String tdDate,
            HttpSession se) {

        String sessionId = (String) se.getAttribute("sessionId");
        // 세션ID와 tdDate를 이용해 tdId를 가져옴.
        int tdId = ts.tdIdSearch(tdDate, sessionId);
        dto.setTdId(tdId);
        Map<String, Integer> response = new HashMap<>();
        int result = ts.todoWrite(dto);
        int returnTdDetailId = ts.getTdDetailId(dto.getTdDetail(), tdId);
        if (result == 1) {
            response.put("tdDetailId", returnTdDetailId);
            double newProgress = ts.todoProgress(tdId);
            ts.regiProgress(tdId, newProgress);
            return response;
        } else {
            return null;
        }
    }

    @ApiOperation(value = "투두리스트 삭제", notes = "tdDetailId를 입력받아 해당 투두리스트 항목을 삭제합니다.")
    @DeleteMapping(value = "/del", produces = "application/json; charset=utf-8")
    public int todoDel(
            @ApiParam(value = "삭제할 투두리스트의 tdDetailId", required = true) @RequestBody TDdetailDTO dto) {
        return ts.todoDel(dto.getTdDetailId());
    }

    @ApiOperation(value = "투두리스트 상태 업데이트", notes = "투두리스트 항목의 완료 상태를 업데이트하고, 변경된 진척도를 반환합니다.")
    @PutMapping(value = "/state", produces = "application/json; charset=utf-8")
    public double updateState(
            @ApiParam(value = "업데이트할 항목 정보 (tdDetailId, tdDetailState, tdId)", required = true) @RequestBody TDdetailDTO dto,
            HttpSession se) {
        ts.updateState(dto.getTdDetailId(), dto.isTdDetailState());
        double progress = ts.todoProgress(dto.getTdId());
        ts.regiProgress(dto.getTdId(), progress);
        return progress;
    }

    @ApiOperation(value = "투두리스트 수정", notes = "투두리스트 항목의 세부내용을 수정합니다.")
    @PutMapping(value = "/modify", produces = "application/json; charset=utf-8")
    public int todoModify(
            @ApiParam(value = "수정할 투두리스트 항목 정보", required = true) @RequestBody TDdetailDTO dto) {
        return ts.todoModify(dto);
    }

    @ApiOperation(value = "하루 메모 가져오기", notes = "YYMMDD 형식의 날짜를 입력하면 해당 날짜의 메모를 가져옵니다. 만약 메모가 없으면 기본 행을 생성합니다.")
    @Transactional
    @GetMapping(value = "/getMemo/{tdDate}", produces = "application/json; charset=utf-8")
    public List<SubTodoListDTO> getMemo(
            @ApiParam(value = "YYMMDD 형식의 날짜 (예: 230315)", required = true) @PathVariable String tdDate,
            HttpSession se) {
        String sessionId = (String) se.getAttribute("sessionId");
        int tdId = ts.tdIdSearch(tdDate, sessionId);
        List<SubTodoListDTO> list = ts.getMemo(tdId);
        if (list.isEmpty()) {
            ts.inputRow(tdDate, sessionId);
            tdId = ts.tdIdSearch(tdDate, sessionId);
            list = ts.getMemo(tdId);
        }
        return list;
    }

    @ApiOperation(value = "메모 작성/수정", notes = "투두리스트의 메모를 작성하거나 수정합니다.")
    @PutMapping(value = "/memoWrite", produces = "application/json; charset=utf-8")
    public int memoWrite(
            @ApiParam(value = "메모 작성 또는 수정 정보", required = true) @RequestBody TodoListDTO listDto) {
        return ts.memoWrite(listDto);
    }

    @ApiOperation(value = "투두리스트 진척도 조회", notes = "YYMMDD 형식의 날짜와 세션 정보를 이용해 투두리스트의 진척도를 계산하여 반환합니다.")
    @GetMapping(value = "/progress/{tdDate}", produces = "application/json; charset=utf-8")
    public double getProgress(
            @ApiParam(value = "YYMMDD 형식의 날짜 (예: 230315)", required = true) @PathVariable String tdDate,
            HttpSession se) {
        String sessionId = (String) se.getAttribute("sessionId");
        int tdId = ts.tdIdSearch(tdDate, sessionId);
        double progress = ts.todoProgress(tdId);
        ts.regiProgress(tdId, progress);
        return ts.todoProgress(tdId);
    }

    @ApiOperation(value = "테스트용 저장", notes = "테스트용 저장 기능(현재 주석 처리되어 있음)")
    @GetMapping(value = "/testSaveDetail", produces = "application/json; charset=utf-8")
    public void testSave(HttpSession se) {
        // 테스트용 메서드 - 필요에 따라 구현
        // ts.saveArchiveDetail();
    }
}
