package com.cafe24.jblog.repository;


import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.cafe24.jblog.vo.BlogVO;

@Repository
public class BlogDAO {

	@Autowired
	private SqlSession sqlSession;
	
	//블로그 자동 생성 from UserService
	public boolean insert(BlogVO bvo) {
		return sqlSession.insert("blog.makeBlog", bvo)==1;
	}
	
	//블로그 정보 추출
	public BlogVO getInfo(String id) {
		return sqlSession.selectOne("blog.getInfo", id);
	}
	
	//블로그 정보 변경
	public boolean update(BlogVO bvo) {
		return sqlSession.insert("blog.updateBlog", bvo)==1;
	}
}
