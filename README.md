# Spring-Boot-example-data-rest-projections

Spring Data REST – Projections

Spring data REST is useful to create applications with hypermedia-driven (HATEOAS) front end and JPA backend. Using projections can request for the specific field from an Object.
Below are the steps to Build sample application for Spring Date REST Projection.
Maven is used as the build tool; backend data store is hsqlDB, JAP for persistance and assecing the object from DB, Spring boot to package everything into a single executabe JAR file with embeded tomcat and HSQL.
Step 1: Create Spring stater project using STS by enabling required dependencies:
 
Step 2: Verify Project structure.
 
Step 3: Edit the POM file and add required dependencies as below:
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>

	<groupId>com.api.coe</groupId>
	<artifactId>api-coe-data-rest-projections</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<packaging>jar</packaging>

	<name>api-coe-data-rest-projections</name>
	<description>Demo project for Spring data rest with projections</description>

	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>1.2.2.RELEASE</version>
		<relativePath/> <!-- lookup parent from repository -->
	</parent>

	<properties>
		<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
		<start-class>com.api.coe.ApiCoeDataRestProjectionsApplication</start-class>
		<java.version>1.7</java.version>
	</properties>

	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jpa</artifactId>
		</dependency>
		
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
		
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-rest</artifactId>
		</dependency>
		
		<dependency> 
			<groupId>org.hsqldb</groupId> 
			<artifactId>hsqldb</artifactId> 
			<scope>runtime</scope> 
		</dependency>
		
	</dependencies>

	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
		</plugins>
	</build>

</project>
Step 4: Creating the domain object to present employee object:
Note:  The class is annoted with @Entity to represent it’s a entity class, id attribute is annotated with @Id for primary key and @GeneratedValue for auto increment.
@Entity
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private long employeeId;
	private String firstName;
	private String lastName;
	private String sex;
	private Date dob;
	private String designation;
	private String businessUnit;
	private String project;
	private Date doj;
	private String mobileNumber;
	private String extensionNumber;
      private String location;

//Add getters and setters for all the fields.

}

Step 5:  Create the intreface for Projection:
1.	 Interface is annotated with @Projection  which is from org.springframework.data.rest.core.config package.
2.	@Projection should b parameterized with name and its types. For e.g @Projection(name = "employee", types = Employee.class)
3.	Define the fields from the employee object to be projected instead of entire object.

@Projection(name = "employee", types = Employee.class)
public interface EmployeeExcerpt {

	long getEmployeeId();

	String getFirstName();

	Date getDoj();

}

Step 6: Create an Employee Repository:

1.	The repository is an interface to allow various operations i.e., GET, PUT, POST & DELETE on employee object.
2.	The repository interface is annotated with @RepositoryRestResource from the package org.springframework.data.rest.core.annotation.RepositoryRestResource. whichh is not mandatory for the repository to b exported. Its only used to change the configurations. i.e.,  path or collectionResourceRel.
3.	Also, @RepositoryRestResource is paramaterized with excerptProjection to have the implementation of projections. i.e., @RepositoryRestResource(excerptProjection = EmployeeExcerpt.class, path = "employee")
4.	Note the repository is extended with PagingAndSortingRepository. It can be any based on the implementions. Like., CrudRepository or JPARepository.
a.	If the query method has pagination capabilities (indicated in the URI template pointing to the resource) the resource takes the following parameters:
b.	page - the page number to access (0 indexed, defaults to 0).
c.	size - the page size requested (defaults to 20).
d.	sort - a collection of sort directives.
5.	Inside the Repository implementation we can also have the custom implementations (methods) to work on the Employee object.

@RepositoryRestResource(excerptProjection = EmployeeExcerpt.class, path = "employee")
public interface EmployeeRepository extends
		PagingAndSortingRepository<Employee, Long> {

}

Step 7: Repository Configuration:

1.	The @Configuration for the repository is from org. springframework.context.annotation.Configuration package.
2.	Used to get the spring boot configurations at run time.
3.	We can override the default behavior of the SpringBootRepositoryRestMvcConfiguration by extending it to any of the custom class.
4.	In the below, have override the configuration to expose the ids of the employee object. Which is default hidden for the outside world.


@Configuration
public class EmployeeRepositoryConfig extends
		SpringBootRepositoryRestMvcConfiguration {

	@Override
	protected void configureRepositoryRestConfiguration(
			RepositoryRestConfiguration config) {
		config.exposeIdsFor(Employee.class);
	}

}

Step 8: Making the application executable:
Spring boot to package everything into a single executabe JAR file with embeded tomcat and HSQL.
1.	@Configuration tags the class as a source of bean definitions for the application context.
2.	@EnableAutoConfiguration tells Spring Boot to start adding beans based on classpath settings, other beans, and various property settings.
3.	Normally you would add @EnableWebMvc for a Spring MVC app, but Spring Boot adds it automatically when it sees spring-webmvc on the classpath. This flags the application as a web application and activates key behaviors such as setting up a DispatcherServlet.
4.	@ComponentScan tells Spring to look for other components, configurations, and services in the the hello package, allowing it to find the HelloController.

@SpringBootApplication
public class ApiCoeDataRestProjectionsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiCoeDataRestProjectionsApplication.class, args);
	}
}

The main() method uses Spring Boot’s SpringApplication.run() method to launch an application. 
Spring Boot automatically spins up Spring Data JPA to create a concrete implementation of the EmployeeRepository and configure it to talk to a back end in-memory database using JPA.
Spring Data REST is a Spring MVC application. The@Import(RepositoryRestMvcConfiguration.class) annotation imports a collection of Spring MVC controllers, JSON converters, and other beans needed to provide a RESTful front end. These components link up to the Spring Data JPA backend.

Step 9: Test data:
{
  "employeeId" : "111111",  	
"firstName" : "Vignesh",
"lastName" : "shanmugam",
"sex" : "MALE",
"dob" : "2015-01-01T00:00:00.000+0000",
"designation" : "Application Developer",
"businessUnit" : "XYZ",
"project" : "ABC",
"doj" : "2015-01-01T00:00:00.000+0000",
"mobileNumber" : "+91 81232xxxx",
"extensionNumber" : "123456789",
"location" : "Bangalore"
}

Step 10: Output:
{
    "_links": {
        "self": {
            "href": "http://localhost:8080/employee{?page,size,sort}",
            "templated": true
        }
    },
    "_embedded": {
        "employees": [
            {
                "doj": "2015-01-01T00:00:00.000+0000",
                "employeeId": 111111,
                "firstName": "Vignesh",
                "_links": {
                    "self": {
                        "href": "http://localhost:8080/employee/1"
                    }
                }
            }
        ]
    },
    "page": {
        "size": 20,
        "totalElements": 1,
        "totalPages": 1,
        "number": 0
    }
}







