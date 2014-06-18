package next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import next.model.Question;
import next.support.db.ConnectionManager;

public class QuestionDao {
	
	public void addCountOfComment(long questionId) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ConnectionManager.getConnection();
			String sql = "UPDATE QUESTIONS SET countOfComment = countOfComment + 1 WHERE questionId = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, questionId);
			
			pstmt.executeUpdate();
		} finally {
			if (pstmt != null) {
				pstmt.close();
			}

			if (con != null) {
				con.close();
			}
		}		
	}

	public void insert(Question question) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ConnectionManager.getConnection();
			String sql = "INSERT INTO QUESTIONS (writer, title, contents, createdDate, countOfComment) VALUES (?, ?, ?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, question.getWriter());
			pstmt.setString(2, question.getTitle());
			pstmt.setString(3, question.getContents());
			pstmt.setTimestamp(4, new Timestamp(question.getTimeFromCreateDate()));
			pstmt.setInt(5, question.getCountOfComment());

			pstmt.executeUpdate();
		} finally {
			if (pstmt != null) {
				pstmt.close();
			}

			if (con != null) {
				con.close();
			}
		}		
	}

	public List<Question> findAll() throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ConnectionManager.getConnection();
			String sql = "SELECT questionId, writer, title, createdDate, countOfComment FROM QUESTIONS " + 
					"order by questionId desc";
			pstmt = con.prepareStatement(sql);

			rs = pstmt.executeQuery();

			List<Question> questions = new ArrayList<Question>();
			Question question = null;
			while (rs.next()) {
				question = new Question(
						rs.getLong("questionId"),
						rs.getString("writer"),
						rs.getString("title"),
						null,
						rs.getTimestamp("createdDate"),
						rs.getInt("countOfComment"));
				questions.add(question);
			}

			return questions;
		} finally {
			closeAll(rs, pstmt, con);
		}
	}

	public Question findById(long questionId) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ConnectionManager.getConnection();
			String sql = createQuery();
			pstmt = con.prepareStatement(sql);
			setValues(questionId, pstmt);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				return mapRow(rs);
			}
			
			return null;
		} finally {
			closeAll(rs, pstmt, con);
		}
	}
	
	private void closeAll(ResultSet rs, PreparedStatement pstmt, Connection con) throws SQLException {
		if (rs != null) {
			rs.close();
		}
		if (pstmt != null) {
			pstmt.close();
		}
		if (con != null) {
			con.close();
		}
	}
	
	private Question mapRow(ResultSet rs) throws SQLException {
		return new Question (
			rs.getLong("questionId"),
			rs.getString("writer"),
			rs.getString("title"),
			rs.getString("contents"),
			rs.getTimestamp("createdDate"),
			rs.getInt("countOfComment"));
	}

	private void setValues(long questionId, PreparedStatement pstmt) throws SQLException {
		pstmt.setLong(1, questionId);
	}
	
	private String createQuery() {
		return "SELECT questionId, writer, title, contents, createdDate, countOfComment FROM QUESTIONS "
				+ "WHERE questionId = ?";
	}
}
