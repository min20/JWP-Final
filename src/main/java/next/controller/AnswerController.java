package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import core.mvc.Controller;
import core.mvc.FrontController;

public class AnswerController implements Controller {
	private AnswerDao answerDao = new AnswerDao();
	private QuestionDao questionDao = new QuestionDao();

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long questionId = Long.parseLong(request.getParameter("questionId"));
		Answer answer = new Answer(request.getParameter("writer"), request.getParameter("contents"), questionId);

		answerDao.insert(answer);
		questionDao.addCountOfComment(questionId);
		return FrontController.getDefaulApiPrefix();
	}
}
