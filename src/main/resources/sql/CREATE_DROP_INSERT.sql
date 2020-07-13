use infy_schema;


delete from infy_schema.TEMPLATE_MAPPING;
delete from infy_schema.TEMPLATE_CLASS;
delete from infy_schema.TEMPLATE_EXECUTION_DETAIL;
delete from infy_schema.TEMPLATE;
delete from infy_schema.USER_SESSION;
delete from infy_schema.CLIENT;
delete from infy_schema.USER;
delete from infy_schema.MT202;


DROP TABLE IF EXISTS infy_schema.TEMPLATE_MAPPING;
DROP TABLE IF EXISTS infy_schema.TEMPLATE_CLASS;
DROP TABLE IF EXISTS infy_schema.TEMPLATE_EXECUTION_DETAIL;
DROP TABLE IF EXISTS infy_schema.TEMPLATE;
DROP TABLE IF EXISTS infy_schema.USER_SESSION;
DROP TABLE IF EXISTS infy_schema.CLIENT;
DROP TABLE IF EXISTS infy_schema.USER;
DROP TABLE IF EXISTS  infy_schema.MT202;



CREATE TABLE infy_schema.USER
(
    USER_ID         int(20) NOT NULL AUTO_INCREMENT,
    USER_NAME       varchar (200) NOT NULL,
    PASSWORD        varchar (1000) NOT NULL,
    FIRST_NAME      varchar (100) NOT NULL,
    LAST_NAME       varchar (100) NOT NULL,
    EMAIL           varchar(200) NOT NULL,
    ACTIVE          tinyint(1)  NOT NULL DEFAULT '1',
    CRTD_BY         varchar (255) DEFAULT NULL,
    CRTD_TS         varchar(50) NOT NULL DEFAULT '00-00-0000 00:00:00' ,
    LAST_MDFD_TS    varchar(50) NOT NULL DEFAULT '00-00-0000 00:00:00' ,
    LAST_MDFD_BY    varchar(255)   DEFAULT NULL,
    PRIMARY KEY (USER_ID),
    UNIQUE KEY USER_UK (USER_NAME, FIRST_NAME, LAST_NAME, EMAIL),
    KEY USER_NAME_IDX (USER_NAME)
);



CREATE TABLE infy_schema.CLIENT
(
    CLIENT_ID       int(20) NOT NULL AUTO_INCREMENT,
    CLIENT_NAME     varchar (100) NOT NULL,
    TYPE            varchar (100) NOT NULL,
    ACTIVE   tinyint(1)  NOT NULL DEFAULT '1',
    CRTD_BY     varchar (255) DEFAULT NULL,
    LAST_MDFD_BY            varchar(255)    DEFAULT NULL,
    CRTD_TS         varchar(50) NOT NULL DEFAULT '00-00-0000 00:00:00' ,
    LAST_MDFD_TS    varchar(50) NOT NULL DEFAULT '00-00-0000 00:00:00' ,
    PRIMARY KEY (CLIENT_ID)

);

CREATE TABLE infy_schema.USER_SESSION
(
    USER_SESSION_ID     int(20)     NOT NULL AUTO_INCREMENT,
    USER_ID             int(20)     NOT NULL,
    CLIENT_ID           int(20)     NOT NULL,
    TOKEN               varchar(1000) DEFAULT NULL,
    ACTIVE   tinyint(1)  NOT NULL DEFAULT '1',
    LAST_LOGIN_TIMESTAMP   varchar(50) NOT NULL DEFAULT '00-00-0000 00:00:00' ,
    LAST_ACCESS_TIMESTAMP   varchar(50) NOT NULL DEFAULT '00-00-0000 00:00:00' ,
    LAST_MDFD_BY            varchar(255)    DEFAULT NULL,
    CRTD_TS         varchar(50) NOT NULL DEFAULT '00-00-0000 00:00:00' ,
    LAST_MDFD_TS    varchar(50) NOT NULL DEFAULT '00-00-0000 00:00:00' ,
    PRIMARY KEY (USER_SESSION_ID),
    CONSTRAINT UST_CLIENT_ID_FK FOREIGN KEY(CLIENT_ID) REFERENCES infy_schema.CLIENT (CLIENT_ID) ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT UST_USER_ID_FK FOREIGN KEY (USER_ID) REFERENCES infy_schema.USER(USER_ID) ON DELETE NO ACTION on UPDATE NO ACTION
);

CREATE TABLE infy_schema.TEMPLATE
(
    TEMPLATE_ID     int(20)     NOT NULL AUTO_INCREMENT,
    CLIENT_ID       int(20)     NOT NULL,
    TEMPLATE_NAME   varchar(100) NOT NULL,
    TEMPLATE_TYPE   varchar(100) NOT NULL,
    TEMPLATE_MAPPING_LOCATION       varchar(100) NOT NULL,
    ACTIVE   tinyint(1)  NOT NULL DEFAULT '1',
    CRTD_BY     varchar (255) DEFAULT NULL,
    LAST_MDFD_BY            varchar(255)    DEFAULT NULL,
    CRTD_TS         varchar(50) NOT NULL DEFAULT '00-00-0000 00:00:00' ,
    LAST_MDFD_TS    varchar(50) NOT NULL DEFAULT '00-00-0000 00:00:00' ,
    PRIMARY KEY (TEMPLATE_ID,CLIENT_ID),
    CONSTRAINT UST_TEMP_CLIENT_ID_FK FOREIGN KEY(CLIENT_ID) REFERENCES infy_schema.CLIENT (CLIENT_ID) ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE TABLE infy_schema.TEMPLATE_MAPPING
(
    TEMPLATE_MAPPING_ID   int(20)     NOT NULL AUTO_INCREMENT,
    TEMPLATE_ID           int(20)       NOT NULL,
    MAPPING_DATA        varchar(2000) NOT NULL,
    ACTIVE   tinyint(1)  NOT NULL DEFAULT '1',
    CRTD_BY     varchar (255) DEFAULT NULL,
    LAST_MDFD_BY            varchar(255)    DEFAULT NULL,
    CRTD_TS         varchar(50) NOT NULL DEFAULT '00-00-0000 00:00:00' ,
    LAST_MDFD_TS    varchar(50) NOT NULL DEFAULT '00-00-0000 00:00:00' ,
    PRIMARY KEY (TEMPLATE_MAPPING_ID,TEMPLATE_ID),
    CONSTRAINT UST_TEMPLATE_ID_FK FOREIGN KEY(TEMPLATE_ID) REFERENCES infy_schema.TEMPLATE (TEMPLATE_ID) ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE TABLE infy_schema.TEMPLATE_EXECUTION_DETAIL
(
    TEMPLATE_EXECUTION_DETAIL_ID   int(20)     NOT NULL AUTO_INCREMENT,
    TEMPLATE_ID           int(20)       NOT NULL,
    CLIENT_ID  int(20)       NOT NULL,
    ACTIVE   tinyint(1)  NOT NULL DEFAULT 1,
    CRTD_BY     varchar (255) DEFAULT NULL,
    LAST_MDFD_BY            varchar(255)    DEFAULT NULL,
    CRTD_TS         varchar(50) NOT NULL DEFAULT '00-00-0000 00:00:00' ,
    LAST_MDFD_TS    varchar(50) NOT NULL DEFAULT '00-00-0000 00:00:00' ,
    PRIMARY KEY (TEMPLATE_EXECUTION_DETAIL_ID,TEMPLATE_ID, CLIENT_ID),
    CONSTRAINT UST_TEMPLATE_DTL_ID1_FK FOREIGN KEY(TEMPLATE_ID) REFERENCES infy_schema.TEMPLATE (TEMPLATE_ID) ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT UST_TEMP_DTL_CLIENT_ID_FK FOREIGN KEY(CLIENT_ID) REFERENCES infy_schema.CLIENT (CLIENT_ID) ON DELETE NO ACTION ON UPDATE NO ACTION
);

create table infy_schema.TEMPLATE_CLASS
(
    TEMPLATE_CLASS_ID int(20)  not null auto_increment,
    TEMPLATE_ID int(20) not null,
    CLIENT_ID int(20) not null,
    CLAZZ_NAME varchar(25) not null,
    SUPER_CLAZZ varchar(100) null,
    PACKAGE_NAME varchar(100) null,
    CLAZZ_OUTPUT_LOCATION varchar(100) not null,
    METHOD_URIS varchar(5000) not null,
    DESCRIPTION varchar(1000) null,
    ACTIVE tinyint(1) default 1 not null,
    CRTD_BY varchar(255) null,
    LAST_MDFD_BY varchar(255) null,
    CRTD_TS varchar(50) default '00-00-0000 00:00:00' not null,
    LAST_MDFD_TS varchar(50) default '00-00-0000 00:00:00' not null,
    primary key (TEMPLATE_CLASS_ID, TEMPLATE_ID, CLIENT_ID),
    CONSTRAINT UST_TEMPLATE_CLASS_TEMPLATE_ID_FK FOREIGN KEY(TEMPLATE_ID) REFERENCES infy_schema.TEMPLATE (TEMPLATE_ID) ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT UST_TEMPLATE_CLASS_CLIENT_ID_FK FOREIGN KEY(CLIENT_ID) REFERENCES infy_schema.CLIENT (CLIENT_ID) ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE TABLE infy_schema.MT202
(
    MT202_ID        int(20)         NOT NULL AUTO_INCREMENT,
    TAG             varchar(100)    NOT NULL,
    FIELD_NAME      varchar(100)    NOT NULL,
    STATUS          varchar(100)    NOT NULL,
    ACTIVE          tinyint(1)      NOT NULL DEFAULT '1',
    CRTD_BY         varchar (255)   DEFAULT NULL,
    LAST_MDFD_BY    varchar(255)    DEFAULT NULL,
    CRTD_TS         varchar(50)     NOT NULL DEFAULT '00-00-0000 00:00:00' ,
    LAST_MDFD_TS    varchar(50)     NOT NULL DEFAULT '00-00-0000 00:00:00' ,
    PRIMARY KEY (MT202_ID)
);


#CLIENT TABLE INSERT
insert into infy_schema.CLIENT (CLIENT_NAME, TYPE, ACTIVE, CRTD_BY, LAST_MDFD_BY, CRTD_TS, LAST_MDFD_TS)
values ('Infosys', 'Internal', '1', 'admin', 'admin', DATE_FORMAT(NOW(), '%m-%d-%Y %T'), DATE_FORMAT(NOW(), '%m-%d-%Y %T'));

#TEMPLATE TABLE INSERTS
insert into infy_schema.TEMPLATE (CLIENT_ID, TEMPLATE_NAME, TEMPLATE_TYPE, TEMPLATE_MAPPING_LOCATION, ACTIVE, CRTD_BY, LAST_MDFD_BY, CRTD_TS, LAST_MDFD_TS)
VALUES (1, 'Psac2022_Mt202', 'mt202', '/home/ranga/sandbox/springboot/psac009/','1', 'admin', 'admin', DATE_FORMAT(NOW(), '%m-%d-%Y %T'), DATE_FORMAT(NOW(), '%m-%d-%Y %T'));
insert into infy_schema.TEMPLATE (CLIENT_ID, TEMPLATE_NAME, TEMPLATE_TYPE, TEMPLATE_MAPPING_LOCATION , ACTIVE, CRTD_BY, LAST_MDFD_BY, CRTD_TS, LAST_MDFD_TS)
VALUES (1, 'Mt202_Psac2022', 'psac2022', '/home/ranga/sandbox/springboot/psac009/','1', 'admin', 'admin', DATE_FORMAT(NOW(), '%m-%d-%Y %T'), DATE_FORMAT(NOW(), '%m-%d-%Y %T'));

#INSERT TEMPLATE_CLASS
INSERT INTO infy_schema.TEMPLATE_CLASS (TEMPLATE_ID, CLIENT_ID, CLAZZ_NAME, SUPER_CLAZZ, PACKAGE_NAME, CLAZZ_OUTPUT_LOCATION, METHOD_URIS, DESCRIPTION, ACTIVE, CRTD_BY, LAST_MDFD_BY, CRTD_TS, LAST_MDFD_TS) VALUES (2, 1, 'Mt202_Psac2022', 'com.beth.infy.templates.MappingTemplate', 'com.beth.infy.domain.ConvertToXmlRequest', '/home/ranga/sandbox/springboot/BethAPIService/templates-code-gen/', 'classpath:methods/1/createXml01.txt;classpath:methods/1/modifyXml01.txt;classpath:methods/1/populateXmlData01.txt', 'Auto Gen Class details ', 1, 'admin', 'admin', DATE_FORMAT(NOW(), '%m-%d-%Y %T'), DATE_FORMAT(NOW(), '%m-%d-%Y %T'));

#INSERT TEMPLATE_USER
INSERT INTO infy_schema.USER (USER_NAME, PASSWORD, FIRST_NAME, LAST_NAME, EMAIL, ACTIVE, CRTD_BY, CRTD_TS, LAST_MDFD_TS, LAST_MDFD_BY) VALUES ('bethapudi', '$2a$10$mYyPhdabF6ZxkJ5ca/NjdOfDi8AfLKMZE8ymkhJfE/zz2U0ZG4LzW', 'Rnaga', 'Bethapudi', 'vbethapudi@yahoo.com', 1, 'bethapudi',  DATE_FORMAT(NOW(), '%m-%d-%Y %T'), DATE_FORMAT(NOW(), '%m-%d-%Y %T'), 'bethapudi');
INSERT INTO infy_schema.USER (USER_NAME, PASSWORD, FIRST_NAME, LAST_NAME, EMAIL, ACTIVE, CRTD_BY, CRTD_TS, LAST_MDFD_TS, LAST_MDFD_BY) VALUES ('admin', '$2a$10$mYyPhdabF6ZxkJ5ca/NjdOfDi8AfLKMZE8ymkhJfE/zz2U0ZG4LzW', 'Rnaga', 'Bethapudi', 'vbethapudi@yahoo.com', 1, 'admin',  DATE_FORMAT(NOW(), '%m-%d-%Y %T'), DATE_FORMAT(NOW(), '%m-%d-%Y %T'), 'admin');

#INSERT MT202 fields
INSERT INTO infy_schema.MT202 (TAG, FIELD_NAME, STATUS, ACTIVE, CRTD_BY, LAST_MDFD_BY, CRTD_TS, LAST_MDFD_TS)
VALUES ('20','Transaction reference number', 'M',1,'admin', 'admin', DATE_FORMAT(NOW(), '%m-%d-%Y %T'), DATE_FORMAT(NOW(), '%m-%d-%Y %T'));
INSERT INTO infy_schema.MT202 (TAG, FIELD_NAME, STATUS, ACTIVE, CRTD_BY, LAST_MDFD_BY, CRTD_TS, LAST_MDFD_TS)
VALUES ('21','Related reference', 'M',1,'admin', 'admin', DATE_FORMAT(NOW(), '%m-%d-%Y %T'), DATE_FORMAT(NOW(), '%m-%d-%Y %T'));
INSERT INTO infy_schema.MT202 (TAG, FIELD_NAME, STATUS, ACTIVE, CRTD_BY, LAST_MDFD_BY, CRTD_TS, LAST_MDFD_TS)
VALUES ('32A','Value date, payment code, inter-bank settled amount', 'M',1,'admin', 'admin', DATE_FORMAT(NOW(), '%m-%d-%Y %T'), DATE_FORMAT(NOW(), '%m-%d-%Y %T'));
INSERT INTO infy_schema.MT202 (TAG, FIELD_NAME, STATUS, ACTIVE, CRTD_BY, LAST_MDFD_BY, CRTD_TS, LAST_MDFD_TS)
VALUES ('52A','Ordering Institution', 'M',1,'admin', 'admin', DATE_FORMAT(NOW(), '%m-%d-%Y %T'), DATE_FORMAT(NOW(), '%m-%d-%Y %T'));
INSERT INTO infy_schema.MT202 (TAG, FIELD_NAME, STATUS, ACTIVE, CRTD_BY, LAST_MDFD_BY, CRTD_TS, LAST_MDFD_TS)
VALUES ('52A','Ordering Institution', 'O',1,'admin', 'admin', DATE_FORMAT(NOW(), '%m-%d-%Y %T'), DATE_FORMAT(NOW(), '%m-%d-%Y %T'));
INSERT INTO infy_schema.MT202 (TAG, FIELD_NAME, STATUS, ACTIVE, CRTD_BY, LAST_MDFD_BY, CRTD_TS, LAST_MDFD_TS)
VALUES ('53A','Sender’s correspondent', 'O',1,'admin', 'admin', DATE_FORMAT(NOW(), '%m-%d-%Y %T'), DATE_FORMAT(NOW(), '%m-%d-%Y %T'));
INSERT INTO infy_schema.MT202 (TAG, FIELD_NAME, STATUS, ACTIVE, CRTD_BY, LAST_MDFD_BY, CRTD_TS, LAST_MDFD_TS)
VALUES ('56A','Intermediary bank institution', 'O',1,'admin', 'admin', DATE_FORMAT(NOW(), '%m-%d-%Y %T'), DATE_FORMAT(NOW(), '%m-%d-%Y %T'));
INSERT INTO infy_schema.MT202 (TAG, FIELD_NAME, STATUS, ACTIVE, CRTD_BY, LAST_MDFD_BY, CRTD_TS, LAST_MDFD_TS)
VALUES ('57A','Account with institution', 'O',1,'admin', 'admin', DATE_FORMAT(NOW(), '%m-%d-%Y %T'), DATE_FORMAT(NOW(), '%m-%d-%Y %T'));
INSERT INTO infy_schema.MT202 (TAG, FIELD_NAME, STATUS, ACTIVE, CRTD_BY, LAST_MDFD_BY, CRTD_TS, LAST_MDFD_TS)
VALUES ('58A','Beneficiary institution', 'M',1,'admin', 'admin', DATE_FORMAT(NOW(), '%m-%d-%Y %T'), DATE_FORMAT(NOW(), '%m-%d-%Y %T'));
INSERT INTO infy_schema.MT202 (TAG, FIELD_NAME, STATUS, ACTIVE, CRTD_BY, LAST_MDFD_BY, CRTD_TS, LAST_MDFD_TS)
VALUES ('72','Bank to bank Information', 'O',1,'admin', 'admin', DATE_FORMAT(NOW(), '%m-%d-%Y %T'), DATE_FORMAT(NOW(), '%m-%d-%Y %T'));
