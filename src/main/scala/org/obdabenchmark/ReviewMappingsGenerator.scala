package org.obdabenchmark

import java.sql.ResultSet
import java.sql.Connection
import scala.io.Source
import org.stringtemplate.v4.ST
import java.io.File

class ReviewMappingsGenerator(conn : Connection) {
	
	
	def getDistictLanguages() : ResultSet = {
	  val sqlQuery = "SELECT DISTINCT(language) FROM review";
	  val rs = Utility.executeQuery(ReviewMappingsGenerator.this.conn, sqlQuery, -1);
	  rs;
	}
	
	def generateMappings(filename : String) {
	  val rs = ReviewMappingsGenerator.this.getDistictLanguages();
	  
	  val sourceFromString = Source.fromFile(filename);
	  val reviewMappingTemplate = sourceFromString.mkString;
	  sourceFromString.close();
	    
	  while(rs.next()) {
	    val lang = rs.getString("language")
	    val reviewMappingString = reviewMappingTemplate.replaceAll("<language>", lang);
	    val newFilename = "output/" + filename.replaceAll("template-", "").replaceAll(".ttl", "-" + lang + ".ttl");
	    
	    Utility.printToFile(new File(newFilename)) (p => p.println(reviewMappingString))
	    println("reviewMapping = " + reviewMappingString);
	  }
	}
}