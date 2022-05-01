package nowon.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import nowon.domain.dto.MemberInsertDTO;

@WebServlet(urlPatterns = {"/member/join", "/member/insert"})
public class MemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private SqlSessionFactory sqlSessionFactory;
	
	@Override
	public void init() throws ServletException {
		sqlSessionFactory = (SqlSessionFactory) getServletContext().getAttribute("sqlSessionFactory");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		StringBuffer url = request.getRequestURL();
		System.out.println(url); //url정보
		
		String uri = request.getRequestURI();
		System.out.println(uri); //uri정보
		
		String[] strs = uri.split("/");
		String key = strs[strs.length-1]; //'/'나누어서 uri의 마지막 문자열값
		
		String path = null; //실제보여줄 페이지jsp정보
		
		if(key.equals("join")) {
			path = "/WEB-INF/member/join.jsp";
		}
		else if(key.equals("insert")) {
			request.setCharacterEncoding("utf-8");
			String email = request.getParameter("email");
			String pass = request.getParameter("pass");
			String name = request.getParameter("name");
			//3개의 데이터를 DB저장 --DTO로 한꺼번에 전달
			MemberInsertDTO dto = new MemberInsertDTO(email, pass, name);
			
			SqlSession sqlSession = sqlSessionFactory.openSession(true);
			int n = sqlSession.insert("memberMapper.save", dto);
			sqlSession.close();
			System.out.println(n+"명의 회원 저장완료!");
			
			String msg = name+"님! 회원가입을 축하합니다.</br>"
					+ "로그인후 이용 가능합니다.";
			request.setAttribute("msg", msg);
			//로그인페이지로 이동..
			path = "/WEB-INF/member/login.jsp";
		}		
		//페이지 이동
		if(path!=null) request.getRequestDispatcher(path).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
