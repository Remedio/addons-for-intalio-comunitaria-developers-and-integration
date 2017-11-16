package org.openinfomanager.bam.treegrid;

import java.sql.ResultSet;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openinfomanager.modules.common.sql.SqlHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.BaseCommandController;

public class TreeGridController extends BaseCommandController {
	private SqlHelper hlp;
	private static final Logger _log = LoggerFactory
			.getLogger(TreeGridController.class);

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest req,
			HttpServletResponse res) throws Exception {
		// TODO Auto-generated method stub
		String vista = req.getParameter("vista");
		String level = req.getParameter("level");

		String query = "select query,baseparam,isleaf from `bam_treegridview` where vista='"
				+ vista + "' and level=" + level + ";";
		ResultSet resultSet = hlp.query(query);
		resultSet.next();
		String sql = resultSet.getString(1);
		sql = setParametersInSQL(req, sql);
		
		String json=getJSON(sql,resultSet.getString(2),resultSet.getInt(3),level);
		HashMap map = new HashMap();
        map.put("json", json);
		return new ModelAndView("bam/"+vista, map);
	}

	private String getJSON(String sql,String baseParam, int isleaf,String level) throws Exception {
		String separator="";
		StringBuilder sa=new StringBuilder();
		sa.append("[");
		boolean leaf=isleaf==0?false:true;
		ResultSet res=hlp.query(sql);
		while(res.next()){
			String child="{baseParam:'"+baseParam+"',level:"+level+",leaf:"+leaf;
			String sep=",";
			for(int i=1;i<=res.getMetaData().getColumnCount();i++){
				 child=child+sep+ 
				 res.getMetaData().getColumnName(i)+":'"+
				 res.getString(i)+"'";
			}
			sa.append(separator+child+"}");
			separator=",";
		}
		sa.append("]");
		return sa.toString();
	}

	private String setParametersInSQL(HttpServletRequest req, String sql) {
		String regex = "([?](\\w+)[?])";
		java.util.regex.Pattern p = java.util.regex.Pattern.compile(regex);
		java.util.regex.Matcher m = p.matcher(sql);
		while (m.find()) {
			String matchedText = m.group(1);
			String par = m.group(2);
			_log.debug("setting parameter " + par + " width value"
					+ req.getParameter(par));
			matchedText = matchedText.replaceAll("\\?", "[?]");
			sql = sql.replaceAll(matchedText, req.getParameter(par));
		}
		return sql;
	}

	public void setSqlHelper(SqlHelper hlp) {
		this.hlp = hlp;
	}
}
