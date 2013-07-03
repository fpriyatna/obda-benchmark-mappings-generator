package org.obdabenchmark

import com.typesafe.config.ConfigFactory
import com.typesafe.config.Config

object MappingsGenerator extends App {
	val conf = ConfigFactory.load();
	
	val username = conf.getString("db.username");
	val databaseName = conf.getString("db.dbname");
	val password = conf.getString("db.password");
	val driverString = conf.getString("db.driverstring");
	val url = conf.getString("db.url");

	val productMappingTemplate = conf.getString("product.mapping.input.templatefile");
	val productMappingOutputDirectory = conf.getString("product.mapping.output.directory");
	val productMappingOutputFile= conf.getString("product.mapping.output.filename");
	
	val reviewMappingTemplate = conf.getString("review.mapping.input.templatefile");
	val reviewMappingOutputDirectory = conf.getString("review.mapping.output.directory");
	val reviewMappingOutputFile = conf.getString("review.mapping.output.filename");
	

	val conn = Utility.openConnection(username, databaseName
	    , password, driverString, url, "MappingsGenerator.scala");
	
	val reviewMappingsGenerator = new ReviewMappingsGenerator(conn);
	println("Generating Review Mappings, please wait....");
	reviewMappingsGenerator.generateMappings(reviewMappingTemplate, reviewMappingOutputDirectory, reviewMappingOutputFile);

	val productMappingsGenerator = new ProductMappingsGenerator(conn);
	println("Generating Product Mappings, please wait....");
	productMappingsGenerator.generateMappings(productMappingTemplate, productMappingOutputDirectory, productMappingOutputFile);

	conn.close();
	println("Mapping generated. Bye.");
	
}