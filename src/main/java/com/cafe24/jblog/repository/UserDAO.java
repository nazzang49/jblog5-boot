package com.cafe24.jblog.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.cafe24.jblog.vo.UserVO;

@Repository
public class UserDAO {

	@Autowired
	private SqlSession sqlSession;
	
	//회원가입
	public boolean insert(UserVO uvo) {
		return sqlSession.insert("user.join", uvo)==1;
	}
	
	//회원정보 변경
	public boolean update(UserVO vo) {
		return sqlSession.update("user.update", vo)==1;
	}
	
	public UserVO get(Long no) {
		UserVO vo = sqlSession.selectOne("user.getByNo",no);
		return vo;
	}
	
	//checkid
	public UserVO get(String id) {
		UserVO uvo = sqlSession.selectOne("user.getById",id);
		return uvo;
	}
	
	//auth
	public UserVO get(UserVO uvo) {
		UserVO authUser = sqlSession.selectOne("user.getByIdAndPw",uvo);
		return authUser;
	}
}
