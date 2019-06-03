package com.cafe24.jblog.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.cafe24.jblog.service.BlogService;
import com.cafe24.jblog.service.UserService;
import com.cafe24.jblog.vo.BlogVO;
import com.cafe24.jblog.vo.CategoryVO;
import com.cafe24.jblog.vo.PostVO;
import com.cafe24.jblog.vo.UserVO;
import com.cafe24.security.Auth;
import com.cafe24.security.AuthUser;

@Controller
@RequestMapping("/{userId:(?!assets).*}")
public class BlogController {

	@Autowired
	private BlogService blogService;
	
	@Autowired
	private UserService userService;
	
	//블로그-메인페이지 이동(회원 아이디 필요 -> 각자의 블로그 정보 표시)
	@RequestMapping({"","/","/{pathNo1}","/{pathNo1}/{pathNo2}"})
	public String blog(@PathVariable Optional<String> userId,
					   @PathVariable Optional<Long> pathNo1,
					   @PathVariable Optional<Long> pathNo2,
					   //Wrapper 클래스 타입으로 명시
					   @RequestParam (value="pageNum", required=true, defaultValue="1") Integer pageNum,
					   Model model) {
		
		//회원 아이디
		String id = "";
		Long categoryNo = 0L;
		Long postNo = 0L;
		PostVO post = null;
		//blog-main에서 필요한 정보 = 블로그 타이틀, 카테고리 리스트, 카테고리 별 게시물 리스트, 기본 표시할 게시물 제목 및 내용
		BlogVO bvo = null;
		List<CategoryVO> categoryList = null;
		 
		if(userId.isPresent()) {
			id = userId.get();
			bvo = blogService.getInfo(id);
			//해당 카테고리 내 해당 게시물 표시
			if(pathNo2.isPresent()) {
				categoryNo = pathNo1.get();
				postNo = pathNo2.get();
				
				//메인에 표시할 게시물 (없으면 "없음 문구" 표시)
				post = blogService.getOne(categoryNo, postNo, id);
				
			}
			//해당 카테고리 내 max번 게시물 표시
			else if(pathNo1.isPresent()) {
				categoryNo = pathNo1.get();
				postNo = blogService.getOnePost(categoryNo);
				
				//메인에 표시할 게시물 (없으면 "없음 문구" 표시)
				post = blogService.getOne(categoryNo, postNo, id);
				
			}
			//둘 다 없는 경우, 대표 게시물 선정 = 포스트 중 1개 선택 + 해당 포스트의 카테고리 번호 함께 반환
			else {
				//단 하나의 게시물도 없으면 "없음 문구" 표시 and 메인으로 이동
				post = blogService.getSpecificPost(id);
				if(post==null) {
					categoryList = blogService.getCategoryList(id);
					model.addAttribute("categoryList", categoryList);
					model.addAttribute("userId", id);
					model.addAttribute("bvo", bvo);
					model.addAttribute("post", post);
					return "blog/blog-main";
				}
				categoryNo = post.getCategoryNo();
			}
		}
		
		categoryList = blogService.getCategoryList(id);	
		Map<String, Object> map = blogService.getPostList(categoryNo, pageNum, id);
	
		//현재 접속한 블로그 회원 아이디
		model.addAttribute("userId", id);
		model.addAttribute("bvo", bvo);
		model.addAttribute("categoryList", categoryList);
		//게시물 페이징 처리
		model.addAttribute("map", map);
		model.addAttribute("post", post);
		model.addAttribute("categoryNo", categoryNo);
		model.addAttribute("pageNum", pageNum);
		
		return "blog/blog-main";
	}
	
	//블로그-관리 페이지 이동
	@Auth
	@RequestMapping("/admin/basic")
	public String basic(@PathVariable Optional<String> userId,
						@AuthUser UserVO authUser,
						Model model) {
		
		String id = "";
		
		if(userId.isPresent()) {
			id=userId.get();
		}
		
		
		//다른 사용자가 접근 시
		if(!authUser.getId().equals(id)) {
			//본인 메인으로 이동
			return "redirect:/";
		}
		
		BlogVO bvo = blogService.getInfo(id);
		
		model.addAttribute("userId", id);
		model.addAttribute("bvo", bvo);
		
		return "blog/blog-admin-basic";
	}
	
	//블로그-관리 정보 변경
	@Auth
	@RequestMapping("/admin/update")
	public String update(@PathVariable Optional<String> userId,
						 @RequestParam (value="title", required=true, defaultValue="") String title,
						 @RequestParam (value="logo") MultipartFile logo,
						 @AuthUser UserVO authUser) {
		
		String id = "";
		
		if(userId.isPresent()) {
			id=userId.get();
		}
		
		//다른 사용자가 접근 시
		if(!authUser.getId().equals(id)) {
			//본인 메인으로 이동
			return "redirect:/";
		}
		
		String url = userService.restore(logo);
		
		BlogVO bvo = new BlogVO();
		bvo.setUserId(id);
		bvo.setTitle(title);
		bvo.setLogo(url);
		
		boolean flag = blogService.update(bvo);
		
		return "redirect:/"+id;
	}
	
	//블로그-카테고리 페이지 이동
	@Auth
	@RequestMapping("/admin/category")
	public String category(@PathVariable Optional<String> userId,
						   Model model,
						   @AuthUser UserVO authUser) {
		
		String id = "";
		
		if(userId.isPresent()) {
			id=userId.get();
		}
		
		//다른 사용자가 접근 시
		if(!authUser.getId().equals(id)) {
			//본인 메인으로 이동
			return "redirect:/";
		}

		BlogVO bvo = blogService.getInfo(id);
		List<CategoryVO> categoryList = blogService.getCategoryList(id);

		model.addAttribute("bvo", bvo);
		model.addAttribute("userId", id);
		model.addAttribute("categoryList", categoryList);
		
		return "blog/blog-admin-category";
	}
	
	//블로그-글쓰기 페이지 이동
	@Auth
	@RequestMapping("/admin/write")
	public String write(@PathVariable Optional<String> userId,
						Model model,
						@ModelAttribute("pvo") PostVO pvo,
						@AuthUser UserVO authUser) {
		
		String id = "";
		
		if(userId.isPresent()) {
			id=userId.get();
		}
		
		//다른 사용자가 접근 시
		if(!authUser.getId().equals(id)) {
			//본인 메인으로 이동
			return "redirect:/";
		}

		List<CategoryVO> categoryList = blogService.getCategoryList(id);
		model.addAttribute("listSize", categoryList.size());
		model.addAttribute("categoryList", categoryList);

		return "blog/blog-admin-write";
	}
	
	//블로그-글쓰기 페이지 작성
	@Auth
	@RequestMapping(value="/admin/write", method=RequestMethod.POST)
	public String write(@PathVariable Optional<String> userId,
						@ModelAttribute("pvo") @Valid PostVO pvo,
						BindingResult result,
						@RequestParam (value="category", required=true, defaultValue="0") Long categoryNo,
						Model model,
						@AuthUser UserVO authUser) {
		
		String id = "";
		
		if(userId.isPresent()) {
			id=userId.get();
		}
		
		//유효성 검사를 통과하지 못한 경우
		if(result.hasErrors()) {
			List<CategoryVO> categoryList = blogService.getCategoryList(id);
			model.addAttribute("categoryList", categoryList);
			model.addAllAttributes(result.getModel());
			return "blog/blog-admin-write";
		}
		
		//다른 사용자가 접근 시
		if(!authUser.getId().equals(id)) {
			//본인 메인으로 이동
			return "redirect:/";
		}
		
		pvo.setCategoryNo(categoryNo);
		blogService.insertPost(pvo);
		
		return "redirect:/"+id;
	}
	
}
