package com.cafe24.jblog.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cafe24.jblog.repository.BlogDAO;
import com.cafe24.jblog.repository.CategoryDAO;
import com.cafe24.jblog.repository.PostDAO;
import com.cafe24.jblog.vo.BlogVO;
import com.cafe24.jblog.vo.CategoryVO;
import com.cafe24.jblog.vo.PostVO;

@Service
public class BlogService {

	private static final int PAGE_SIZE = 5;
	
	@Autowired
	private BlogDAO blogDao;
	
	@Autowired
	private CategoryDAO categoryDao;
	
	@Autowired
	private PostDAO postDao;
	
	//post 페이징 처리
	public Map<String, Object> getPostList(Long categoryNo, int pageNum, String id){
		
		///현재 페이지
		int currentPage = pageNum;
		
		//한 페이지에 10개의 게시물 표시
		int pageBlock = 3;
		
		//한 페이지의 시작행, 끝행
		int startRow = (currentPage-1)*PAGE_SIZE+1;
		
		//카테고리 내 총 게시물 수
		int count = categoryDao.getCount(categoryNo, id);
		int pageCount = count/PAGE_SIZE+(count%PAGE_SIZE==0? 0:1);
		int startPage = ((currentPage-1)/pageBlock)*pageBlock+1;
		int endPage = startPage+pageBlock-1;
		
		System.out.println(pageCount);
		System.out.println(endPage);
		
		if(endPage>pageCount) {
			endPage=pageCount;
		}

		List<PostVO> postList = postDao.getPostList(categoryNo, startRow-1, PAGE_SIZE, id);
		
		System.out.println("count : "+count);
		
		Map<String, Object> map = new HashMap<>();
		map.put("postList", postList);
		map.put("currentPage", currentPage);
		map.put("startPage", startPage);
		map.put("pageBlock", pageBlock);
		map.put("pageCount", pageCount);
		map.put("endPage", endPage);
		map.put("count", count);
		
		return map;
	}
	
	//blog-main
	public List<CategoryVO> getCategoryList(String id) {
		return categoryDao.getCategoryList(id);
	}
	
	//blog-main
	public BlogVO getInfo(String id) {
		return blogDao.getInfo(id);
	}
	
	//blog-main
	public Long getOnePost(Long categoryNo) {
		return postDao.getOnePost(categoryNo);
	}
	
	//blog-main
	public PostVO getOne(Long categoryNo, Long postNo, String id) {
		return postDao.getOne(categoryNo, postNo, id);
	}
	
	//blog-main
	public Long getOneCategory(String id) {
		return categoryDao.getOneCategory(id);
	}
	
	//blog-admin-basic
	public boolean update(BlogVO bvo) {
		return blogDao.update(bvo);
	}
	
	//blog-admin-category
	public boolean insertCategory(CategoryVO cvo) {
		return categoryDao.insertCategory(cvo);
	}
	
	//blog-admin-write
	public boolean insertPost(PostVO pvo) {
		return postDao.insertPost(pvo);
	}
	
	//blog-admin-category
	public boolean deletePost(Long categoryNo) {
		return postDao.deletePost(categoryNo);
	}
	
	//blog-admin-category
	public boolean deleteCategory(Long no) {
		return categoryDao.deleteCategory(no);
	}
	
	//blog-main-getSpecificPost
	public PostVO getSpecificPost(String id) {
		return postDao.getSpecificPost(id);
	}
	
}
