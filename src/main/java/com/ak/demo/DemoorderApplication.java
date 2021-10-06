package com.ak.demo;

import java.sql.SQLException;
import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@SpringBootApplication
public class DemoorderApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoorderApplication.class, args);
	}
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	/**
	 * Comment for Oracle DB
	 */
	@PostConstruct
	private void initDb() {
		System.out.println(String.format("****** Creating table: %s, and Inserting test data ******", "Order"));
		
		String encryptedPwd = passwordEncoder.encode("test123");

		String sqlStatements[] = { "drop table purchaseorder if exists;",
				"drop table purchase_orderline if exists;",
				"drop table USER_MST if exists;",
				
				"create table purchaseorder(ORDER_ID serial,VERSION SMALLINT,TOTAL_ORDER SMALLINT,NAME varchar(300),ADDRESS varchar(300),"
						+ "CREATION_DATE timestamp(6));",
				"create table purchase_orderline(ORDERLINE_ID serial,VERSION SMALLINT,QUANTITY SMALLINT,BOOK_ID INT,ORDER_ID INT,"
								+ "CREATION_DATE timestamp(6),foreign key (ORDER_ID) references purchaseorder(ORDER_ID));",
				"create table user_mst(USER_ID serial,USER_NAME varchar(35),PASSWORD varchar(100),ACTIVE varchar(1),ROLES varchar(500),"
										+ "CREATION_DATE timestamp(6));",
				"drop sequence SEQ_TKSK_SURVEY_ID if exists;",
				"create sequence SEQ_TKSK_SURVEY_ID start with 6 increment by 1;",
				
				"insert into purchaseorder(ORDER_ID, VERSION, TOTAL_ORDER,NAME,ADDRESS,CREATION_DATE) values('1','10','20','QA Tower1 Books','QA Tower1, Doha',CURRENT_TIMESTAMP);",
				"insert into purchaseorder(ORDER_ID, VERSION, TOTAL_ORDER,NAME,ADDRESS,CREATION_DATE) values('2','11','21','QA Tower2 Books','QA Tower2, Al hilal',CURRENT_TIMESTAMP);",
				"insert into purchaseorder(ORDER_ID, VERSION, TOTAL_ORDER,NAME,ADDRESS,CREATION_DATE) values('3','12','22','QA Tower3 Books','QA Tower3, AL wakra',CURRENT_TIMESTAMP);",
				"insert into purchaseorder(ORDER_ID, VERSION, TOTAL_ORDER,NAME,ADDRESS,CREATION_DATE) values('4','13','23','QA Tower4 Books','QA Tower4, Salwa road',CURRENT_TIMESTAMP);",
				"insert into purchaseorder(ORDER_ID, VERSION, TOTAL_ORDER,NAME,ADDRESS,CREATION_DATE) values('5','14','24','QA Tower5 Books','QA Tower5, Al wukhair',CURRENT_TIMESTAMP);",
				
				"insert into purchase_orderline(ORDERLINE_ID, VERSION, QUANTITY,BOOK_ID,ORDER_ID,CREATION_DATE) values('1','1','20','1','1',CURRENT_TIMESTAMP);",
				"insert into purchase_orderline(ORDERLINE_ID, VERSION, QUANTITY,BOOK_ID,ORDER_ID,CREATION_DATE) values('2','2','11','2','2',CURRENT_TIMESTAMP);",
				"insert into purchase_orderline(ORDERLINE_ID, VERSION, QUANTITY,BOOK_ID,ORDER_ID,CREATION_DATE) values('3','3','22','3','3',CURRENT_TIMESTAMP);",
				"insert into purchase_orderline(ORDERLINE_ID, VERSION, QUANTITY,BOOK_ID,ORDER_ID,CREATION_DATE) values('4','4','33','4','4',CURRENT_TIMESTAMP);",
				"insert into purchase_orderline(ORDERLINE_ID, VERSION, QUANTITY,BOOK_ID,ORDER_ID,CREATION_DATE) values('5','5','14','5','5',CURRENT_TIMESTAMP);",
				
				"insert into user_mst(USER_ID, USER_NAME, PASSWORD,ACTIVE,ROLES,CREATION_DATE) values('1','AHMED','test123','Y','ROLE_ADMIN',CURRENT_TIMESTAMP);",
				"insert into user_mst(USER_ID, USER_NAME, PASSWORD,ACTIVE,ROLES,CREATION_DATE) values('2','MARK','test123','Y','ROLE_ADMIN,ROLE_MODERATOR',CURRENT_TIMESTAMP);",
				"insert into user_mst(USER_ID, USER_NAME, PASSWORD,ACTIVE,ROLES,CREATION_DATE) values('3','MICHAEL','test123','Y','ROLE_USER',CURRENT_TIMESTAMP);"};

		Arrays.asList(sqlStatements).stream().forEach(sql -> {
			
			if(sql.contains("test123")) {
				sql = sql.replace("test123", encryptedPwd);
				System.out.println("encryptedPwd-------------->"+encryptedPwd);
			}
			System.out.println(sql);
			jdbcTemplate.execute(sql);
		});

	}

	
	/**
	 * Comment for Oracle DB
	 */
	@Bean(initMethod = "start", destroyMethod = "stop")
	public Server inMemoryH2DatabaseServer() throws SQLException {
		return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9092");
	}

}
