package org.obdabenchmark

import java.sql.Connection
import java.util.Properties
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.Statement

class Utility {
  
}

object Utility {
 
	def openConnection(username : String, databaseName : String
			, password : String, driverString : String, url : String
			, referrer : String) : Connection = {
			val prop = new Properties;
			prop.put("ResultSetMetaDataOptions", "1");
			prop.put("user", username);
			prop.put("database", databaseName);
			prop.put("password", password);
			prop.put("autoReconnect", "true");
			Class.forName(driverString);
			val conn = DriverManager.getConnection(url, prop);
			conn;
	}

	def executeQuery(conn : Connection , query : String , timeout : Integer) : ResultSet = {
			val stmt : Statement = conn.createStatement(
					java.sql.ResultSet.TYPE_FORWARD_ONLY,java.sql.ResultSet.CONCUR_READ_ONLY);

		if(timeout > 0) {
			stmt.setQueryTimeout(timeout);
		}
		val result : ResultSet = stmt.executeQuery(query);
		result;
	}	
	
	def closeConnection(conn : Connection, referrer : String) {
		if(conn != null) {
			conn.close();
		}
	}

	def printToFile(f: java.io.File) (op: (java.io.PrintWriter => Unit)) {
		val p = new java.io.PrintWriter(f)
		try { op(p) } finally { p.close() }
	}
}

