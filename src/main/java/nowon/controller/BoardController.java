package nowon.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import nowon.domain.dto.BoardDTO;
import nowon.domain.dto.BoardInsertDTO;
import nowon.domain.dto.BoardUpdateDTO;

@WebServlet(urlPatterns = {
		"/board/list", "/board/write", "/board/insert", "/board/detail", "/board/delete", "/board/update"})
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SqlSessionFactory sqlSessionFactory;
       
	@Override
	public void init() throws ServletException {
		sqlSessionFactory = (SqlSessionFactory) getServletContext().getAttribute("sqlSessionFactory");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/board 로 요청시 실행");
		
		String uri = request.getRequestURI();
		String[] strs = uri.split("/");
		String key = strs[strs.length-1];
		System.out.println(key);
		
		String path = null;
		
		if(key.equals("list")) {
			SqlSession sqlSession = sqlSessionFactory.openSession();
			
			List<BoardDTO> result = sqlSession.selectList("boardMapper.all");
			sqlSession.close();
			
			request.setAttribute("list", result);
			//페이지이동
			path = "/WEB-INF/board/list.jsp";
			
		}
		else if(key.equals("write")) {
			//페이지이동
			path="/WEB-INF/board/write.jsp";
			
		}
		else if(key.equals("insert")) {
			request.setCharacterEncoding("utf-8");
			String subject = request.getParameter("subject");
			String content = request.getParameter("content");
			String writer = request.getParameter("writer");
			
			BoardInsertDTO dto = new BoardInsertDTO(subject, content, writer);
			
			SqlSession sqlSession = sqlSessionFactory.openSession(true);
			//쿼리실행
			int n = sqlSession.insert("boardMapper.save", dto);
			sqlSession.close();
			System.out.println(n+"개 저장완료!");
			//리스트페이지로 이동 : 응답처리
			response.sendRedirect("list");
		}
		else if(key.equals("detail")) {
			String _no = request.getParameter("no");
			//DB의 no는 숫자
			int no = Integer.parseInt(_no);
			System.out.println(no);
			//DB에서 조회
			SqlSession sqlSession = sqlSessionFactory.openSession();
			BoardDTO result = sqlSession.selectOne("boardMapper.detail", no);
			sqlSession.close();
			
			request.setAttribute("detail", result);
			System.out.println(result);
			
			path = "/WEB-INF/board/detail.jsp";			
		}
		else if(key.equals("delete")) {
			//1.파리미터 받기
			String _no = request.getParameter("no");
			int no = Integer.parseInt(_no);
			//2.DB접속
			SqlSession sqlSession = sqlSessionFactory.openSession(true);
			//3.삭제작업
			sqlSession.delete("boardMapper.del", no);
			sqlSession.close();
			//4.list로 이동
			response.sendRedirect("list");
		}
		else if(key.equals("update")) {
			request.setCharacterEncoding("utf-8");
			String _no = request.getParameter("no");
			int no = Integer.parseInt(_no);
			String subject = request.getParameter("subject");
			String content = request.getParameter("content");
			
			BoardUpdateDTO dto = new BoardUpdateDTO(no, subject, content);
			
			SqlSession sqlSession = sqlSessionFactory.openSession(true);
			//쿼리실행
			sqlSession.update("boardMapper.updateB", dto);
			sqlSession.close();
			//리스트페이지로 이동 : 응답처리
			response.sendRedirect("list");
		}
		//end if
		
		//페이지이동: 응답처리
		if(path!=null) request.getRequestDispatcher(path).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
