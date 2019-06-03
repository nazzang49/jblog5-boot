package com.cafe24.jblog.controller.api;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cafe24.jblog.dto.JSONResult;
import com.cafe24.jblog.service.BlogService;
import com.cafe24.jblog.vo.CategoryVO;

@Controller("blogAPIController")
@RequestMapping("/api")
public class BlogController {

	@Autowired
	private BlogService blogService;
	
	@ResponseBody
	@RequestMapping("/delete")
	public JSONResult delete(@RequestParam(value="no", required=true, defaultValue="0") Long no,
							 @RequestParam(value="userId", required=true, defaultValue="") String userId) {
		
		//카테고리 내 모든 게시물 먼저 삭제
		blogService.deletePost(no);
		blogService.deleteCategory(no);
		
		List<CategoryVO> categoryList = blogService.getCategoryList(userId);
		
		//JSON 형태로 변형하여 전송
		JSONResult result = JSONResult.success(categoryList);
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/insert")
	public JSONResult insert(@RequestParam(value="subject", required=true, defaultValue="") String subject,
							 @RequestParam(value="description", required=true, defaultValue="") String description,
							 @RequestParam(value="userId", required=true, defaultValue="") String userId) {
		
		CategoryVO cvo = new CategoryVO();
		cvo.setSubject(subject);
		cvo.setDescription(description);
		cvo.setUserId(userId);
		
		blogService.insertCategory(cvo);
		
		List<CategoryVO> categoryList = blogService.getCategoryList(userId);
		
		//JSON 형태로 변형하여 전송
		JSONResult result = JSONResult.success(categoryList);
		return result;
	}
	
}