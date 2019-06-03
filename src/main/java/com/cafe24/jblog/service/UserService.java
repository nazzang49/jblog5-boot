package com.cafe24.jblog.service;

import java.io.FileOutputStream;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cafe24.jblog.repository.BlogDAO;
import com.cafe24.jblog.repository.UserDAO;
import com.cafe24.jblog.vo.BlogVO;
import com.cafe24.jblog.vo.UserVO;

@Service
public class UserService {

	private static final String SAVE_PATH = "/jblog-uploads";
	private static final String URL = "/image";
	
	@Autowired
	private UserDAO userDao;
	
	@Autowired
	private BlogDAO blogDao;
	
	public boolean existId(String id) {
		UserVO uvo = userDao.get(id);
		//true면 사용자 있음
		//false면 사용자 없음(이메일 사용 가능)
		return uvo!=null;
	}
	
	public boolean join(UserVO uvo) {
		return userDao.insert(uvo);
	}
	
	//auth
	public UserVO getUser(UserVO uvo) {
		return userDao.get(uvo);
	}
	
	//makeBlog
	public boolean makeBlog(BlogVO bvo) {
		return blogDao.insert(bvo);
	}
	
	//logo
	public String restore(MultipartFile multipartFile) {
		String url = "";

		try {
		
			if(multipartFile.isEmpty()) {
				return url;
			}
			
			String originalFilename = 
					multipartFile.getOriginalFilename();
			String extName = originalFilename.substring(originalFilename.lastIndexOf('.')+1);
			String saveFileName = generateSaveFileName(extName);
			long fileSize = multipartFile.getSize();
			
			System.out.println("##########" + originalFilename);
			System.out.println("##########" + extName);
			System.out.println("##########" + saveFileName);
			System.out.println("##########" + fileSize);
			
			byte[] fileData = multipartFile.getBytes();
			OutputStream os = new FileOutputStream(SAVE_PATH + "/" + saveFileName);
			os.write(fileData);
			os.close();
			
			url = URL + SAVE_PATH + "/" + saveFileName;
			
		} catch (IOException e) {
			throw new RuntimeException("Fileupload error:" + e);
		}
		
		return url;
	}
	
	private String generateSaveFileName(String extName) {
		String filename = "";
		Calendar calendar = Calendar.getInstance();
		
		filename += calendar.get(Calendar.YEAR);
		filename += calendar.get(Calendar.MONTH);
		filename += calendar.get(Calendar.DATE);
		filename += calendar.get(Calendar.HOUR);
		filename += calendar.get(Calendar.MINUTE);
		filename += calendar.get(Calendar.SECOND);
		filename += calendar.get(Calendar.MILLISECOND);
		filename += ("." + extName);
		
		return filename;
	}
}
