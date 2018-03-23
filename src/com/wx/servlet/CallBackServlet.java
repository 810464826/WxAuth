package com.wx.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wx.util.AuthUtil;

import net.sf.json.JSONObject;

/**
 * @author ChenXb
 *
 * 2018年3月21日
 */
@SuppressWarnings("serial")
//@WebServlet("/callBack")   因为在web.xml中已经配置了  所以这里就不需要了 
public class CallBackServlet extends HttpServlet{
	private String driverName;
	private String dbUrl;
	private String userName;
	private String passWord;
	//后面3个全是sql的包里面 不是jdbc的
	private Connection conn=null;
	private PreparedStatement ps=null;
	private ResultSet rs=null;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		//加载驱动
		try {
			this.driverName=config.getInitParameter("driverName");
			this.dbUrl=config.getInitParameter("dbUrl");
			this.userName=config.getInitParameter("userName");
			this.passWord=config.getInitParameter("passWord");
			Class.forName(driverName);
			System.out.println("userName>"+userName+"****passWord>"+passWord);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//以下是服务器测试  本地测试就不需要这些了
		String code=req.getParameter("code");
		String url="https://api.weixin.qq.com/sns/oauth2/access_token?appid="+AuthUtil.APPID
				+ "&secret="+AuthUtil.APPSECRET
				+ "&code="+code
				+ "&grant_type=authorization_code";
		JSONObject jsonObject = AuthUtil.doGetJson(url);
		String openid = jsonObject.getString("openid");
		System.out.println("openid***>"+openid);
		String accessToken = jsonObject.getString("access_token");
		String getUserInfoUrl="https://api.weixin.qq.com/sns/userinfo?access_token="+accessToken
				+ "&openid="+openid
				+ "&lang=zh_CN";
		JSONObject userInfo = AuthUtil.doGetJson(getUserInfoUrl);
		System.out.println(userInfo);
		
		//使用微信用户信息直接登录，无需注册和绑定  
		req.setAttribute("info", userInfo);
		//直接跳转到新的页面  显示微信用户信息
		req.getRequestDispatcher("/index1.jsp").forward(req, resp);
		//将微信与当前系统的账号进行绑定
//		String openId="";
////	String nickName="";
		try {
			String nickName=getNickName(openid);
			if(!"".equals(nickName)){
				//绑定成功
				req.setAttribute("nickName", nickName);
				req.getRequestDispatcher("/index2.jsp").forward(req, resp);
			}else{
				//未绑定
				req.setAttribute("openid", openid);
				req.getRequestDispatcher("/login.jsp").forward(req, resp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//数据库的查询
	public String getNickName(String openid) throws SQLException{
		String nickName="";
		conn=DriverManager.getConnection(dbUrl,userName,passWord);
		//根据openid查询当前账号
		String sql="select nickname from user where openid = ? ";
		ps = conn.prepareStatement(sql);
		ps.setString(1, openid);
		rs=ps.executeQuery();
		while(rs.next()){
			nickName=rs.getString("NICKNAME");
		}
		rs.close();
		ps.close();
		conn.close();
		return nickName;
	}
	
	//数据库的修改
	public int updateUser(String openid,String account,String passWord) throws SQLException{
		conn=DriverManager.getConnection(dbUrl,userName,passWord);
		//根据openid查询当前账号
		String sql="update user set openid = ? where account =? and password=?";
		ps = conn.prepareStatement(sql);
		ps.setString(1, openid);
		ps.setString(2, account);
		ps.setString(3, passWord);
		int temp=ps.executeUpdate();
		rs.close();
		ps.close();
		conn.close();
		return temp;
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//登录请求
		String account = req.getParameter("account");
		String password = req.getParameter("password");
		String openid = req.getParameter("openid");
		try {
			int temp = updateUser(openid, account, password);
			if(temp>0){
				System.out.println("账号绑定成功！");
			}else{
				System.out.println("账号绑定失败！");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
