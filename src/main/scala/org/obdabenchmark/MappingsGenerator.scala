package org.obdabenchmark

object MappingsGenerator extends App {
	val username = "fpriyatna";
	val databaseName = "bsbm100m-original";
	val password = "password";
	val driverString = "org.postgresql.Driver";
	val url = "jdbc:postgresql://localhost:5432/bsbm250k-original";
	val productMappingTemplate = "template-product-mapping.r2rml.ttl";
	val reviewMappingTemplate = "template-review-mapping.r2rml.ttl";
	
	val conn = Utility.openConnection(username, databaseName
	    , password, driverString, url, "Generator.scala");
	
//	val mappingsReviewGenerator = new ReviewMappingsGenerator(conn);
//	mappingsReviewGenerator.generateReviewMappings(templateReviewMapping);

	val productMappingsGenerator = new ProductMappingsGenerator(conn);
	productMappingsGenerator.generateMappings(productMappingTemplate);

	println("Bye");
	
	
}