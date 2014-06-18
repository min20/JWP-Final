package next.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import next.dao.QuestionDao;
import next.model.Question;
import core.mvc.Controller;

public class ListController implements Controller {
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		QuestionDao questionDao = new QuestionDao();
		List<Question> questions = questionDao.findAll();
		
		request.setAttribute("questions", questions);
		return "list.jsp";
	}
}
