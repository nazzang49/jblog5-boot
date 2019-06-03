package com.cafe24.jblog.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cafe24.jblog.vo.CategoryVO;

@Repository
public class CategoryDAO {

	@Autowired
	private SqlSession sqlSession;
	
	//블로그 카테고리 추출
	public List<CategoryVO> getCategoryList(String id) {
		return sqlSession.selectList("blog.getCategoryList", id);
	}
	
	//특정 카테고리 내 전체 게시물 수
	public int getCount(Long categoryNo, String id) {
		Map<String, Object> map = new HashMap<>();
		map.put("categoryNo", categoryNo);
		map.put("userId", id);
		return sqlSession.selectOne("blog.getCount", map);
	}
	
	//블로그 정보 추출(대표 카테고리)
	public Long getOneCategory(String id) {
		return sqlSession.selectOne("blog.getOneCategory", id);
	}
	
	//카테고리 추가
	public boolean insertCategory(CategoryVO cvo) {
		return sqlSession.insert("blog.insertCategory", cvo)==1;
	}
	
	//카테고리 삭제
	public boolean deleteCategory(Long no) {
		return sqlSession.delete("blog.deleteCategory", no)==1;
	}
	
}
