package next.controller;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import next.dao.QuestionDao;
import next.model.Question;
import core.mvc.Controller;
import core.mvc.FrontController;

public class ListController implements Controller {
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		response.setContentType("application/json");

		PrintWriter out = response.getWriter();
		Gson gson = new Gson();
		
		QuestionDao questionDao = new QuestionDao();
		List<Question> questions = questionDao.findAll();
		
		if (request.getRequestURI().equals("/api/list.next")) {
			out.println(gson.toJson(questions.toString()));
			return FrontController.getDefaulApiPrefix();
		}
		
		request.setAttribute("questions", questions);
		return "list.jsp";
	}
}
