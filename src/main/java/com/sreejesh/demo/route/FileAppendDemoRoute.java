package com.sreejesh.demo.route;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Component
@ConfigurationProperties(prefix="camel-demo-route")
@Data
@EqualsAndHashCode(callSuper=true)

public class FileAppendDemoRoute extends RouteBuilder {

	// The value of this property is injected from application.properties based on the profile chosen.
	private String injectedName;
	
	@Override
	public void configure() throws Exception {

		// @formatter:off
		


		from("file://{{inputFolder}}?delay=10s&noop=true&doneFileName=done")
		.routeId("InputFolderToTestSedaRoute")
		.setProperty("myFileConsumedBody", simple("${body}"))
		.setBody(constant("<RootTag>"))
//		.to("file://{{outputFolder}}")
		.to("file://{{outputFolder}}?fileName=myFile.txt&fileExist=Append")
		.setBody(constant("<RootTag>"))
//		.to("file://{{outputFolder}}")
		.to("file://{{outputFolder}}?fileName=myFile.txt&fileExist=Append")
		.setBody(simple("${exchangeProperty.myFileConsumedBody}"))
		.log("*** STEP 100: ${headers} :***")
//		.delay(10000)
//		.to("seda://tempFileSeda")
//		;

//		from("seda://tempFileSeda")
		//.setHeader("")
		.to("file://{{outputFolder}}?fileExist=Append&fileName=myFile.txt")
//		.to("file://{{outputFolder}}?fileExist=Append&fileName=${header.CamelFileName}")
		.log("*** STEP 200: ${headers} :***")
		.setBody(constant("</RootTag>"))
		.to("file://{{outputFolder}}?fileExist=Append&fileName=myFile.txt")
		.log("*** STEP 300 DONE!! ***")
		;

		
		
		// @formatter:on

	}



}
