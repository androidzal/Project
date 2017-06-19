package com.fjnu.edu.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.fjnu.edu.database.DBHelper;
import com.fjnu.edu.message.Client;
import com.fjnu.edu.message.Json;
import com.fjnu.edu.message.MyMessage;
import com.fjnu.edu.recipe.Recipe;

public class ServerThread implements Runnable {
	private Socket socket = null;
	private DataOutputStream out = null;
	private DataInputStream in = null;
	private MyMessage msg = new MyMessage();
	private MyMessage remsg = new MyMessage();

	public ServerThread(Socket socket) {
		this.socket = socket;
	}

	public void run() {
		try {
			out = new DataOutputStream(socket.getOutputStream());
			in = new DataInputStream(socket.getInputStream());
			while (socket != null) {
				String info = null;
				info = in.readUTF();
				msg = (MyMessage) Json.JsontoObject(info, msg.getClass());
				int type = msg.gethead();
				operation(type);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void operation(int type) throws IOException, SQLException {
		switch (type) {
		case Client.FINISH:
			System.out.println(socket.getInetAddress().getHostAddress() + ":over");
			in.close();
			out.close();
			socket.close();
			socket = null;
			return;
		case Client.QUERY:
			remsg.sethead(Client.QUERY);
			remsg.setdetail(Json.RecipeListtoJson(DBHelper.Query(msg.getdetail(),msg.getRaw())));
			break;
		case Client.QUERY_DETAIL:
			remsg.sethead(Client.QUERY_DETAIL);
			remsg.setdetail(Json.ObjecttoJson(DBHelper.QueryDetail(msg.getdetail())));
			break;
		case Client.QUERY_MANY:
			remsg.sethead(Client.QUERY_MANY);
			remsg.setdetail(Json.RecipeListtoJson(DBHelper.Query(Json.JsontoArray(msg.getdetail()))));
			break;
		default:
			break;
		}
		out.writeUTF(Json.ObjecttoJson(remsg));
		out.flush();
	}
}
