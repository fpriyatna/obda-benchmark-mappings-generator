package org.obdabenchmark

import java.sql.Connection
import scala.io.Source
import java.sql.ResultSet
import java.io.File

class ProductMappingsGenerator(conn : Connection) {
	
	
	def getAvailableProductTypes() : ResultSet = {
	  val sqlQuery = "SELECT nr FROM producttype";
	  val rs = Utility.executeQuery(this.conn, sqlQuery, -1);
	  rs;	  
	}

	def generateMappings(mappingTemplateFilename : String, outputDirectory : String, outputFilename : String) {
	  val rs = this.getAvailableProductTypes();
	  
	  val sourceFromString = Source.fromFile(mappingTemplateFilename);
	  val productMappingTemplate = sourceFromString.mkString;
	  sourceFromString.close();
	    
	  while(rs.next()) {
	    val productTypeNr = rs.getString("nr")
	    val productMappingString = productMappingTemplate.replaceAll(
	        "<producttype>", productTypeNr);
	    val outputDirectoryFile = new File(outputDirectory);
	    if(!outputDirectoryFile.exists()) {
	      outputDirectoryFile.mkdir();
	    }
	    val newFilename = outputDirectoryFile + "/" + outputFilename.replaceAll("<producttype>", productTypeNr);;
	    
	    Utility.printToFile(new File(newFilename)) (p => p.println(productMappingString))
	    println("reviewMapping = " + productMappingString);
	  }
	}	
}