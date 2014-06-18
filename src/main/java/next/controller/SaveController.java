package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import next.dao.QuestionDao;
import next.model.Question;
import core.mvc.Controller;
import core.mvc.FrontController;

public class SaveController implements Controller {
	private QuestionDao questionDao = new QuestionDao();

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Question question =	new Question(
				request.getParameter("writer"), request.getParameter("title"),
				request.getParameter("contents"));
		
		questionDao.insert(question);
		return FrontController.getDefaultRedirectPrefix() + "list.next";
	}

}
