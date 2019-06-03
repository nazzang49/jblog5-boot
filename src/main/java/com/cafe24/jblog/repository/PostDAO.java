package com.cafe24.jblog.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cafe24.jblog.vo.PostVO;

@Repository
public class PostDAO {

	@Autowired
	private SqlSession sqlSession;
	
	//블로그 정보 추출(대표 게시물)
	public Long getOnePost(Long categoryNo) {
		return sqlSession.selectOne("blog.getOnePost", categoryNo);
	}
	
	//포스트 추가
	public boolean insertPost(PostVO pvo) {
		return sqlSession.insert("blog.insertPost",pvo)==1;
	}
	
	//블로그 게시물 추출
	public List<PostVO> getPostList(Long categoryNo, int startRow, int pageSize, String id) {
		Map<String, Object> map = new HashMap<>();
		map.put("categoryNo", categoryNo);
		map.put("startRow", startRow);
		map.put("pageSize", pageSize);
		map.put("userId", id);
		return sqlSession.selectList("blog.getPostList", map);
	}

	//카테고리 삭제 전 내부 게시물 먼저 삭제
	public boolean deletePost(Long categoryNo) {
		return sqlSession.delete("blog.deletePost", categoryNo)==1;
	}
	
	//블로그 정보 추출(대표 카테고리)
	public PostVO getOne(Long categoryNo, Long postNo, String id) {
		Map<String, Object> map = new HashMap<>();
		map.put("categoryNo", categoryNo);
		map.put("postNo", postNo);
		map.put("userId", id);
		return sqlSession.selectOne("blog.getOne", map);
	}
	
	//블로그 정보 추출(대표 게시물)
	public PostVO getSpecificPost(String id) {
		return sqlSession.selectOne("blog.getSpecificPost", id);
	}
}
