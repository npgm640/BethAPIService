
#CLIENT TABLE INSERT
insert into infy_schema.CLIENT (CLIENT_NAME, TYPE, ACTIVE, CRTD_BY, LAST_MDFD_BY, CRTD_TS, LAST_MDFD_TS)
values ('Infosys', 'Internal', '1', 'admin', 'admin', '06-05-2020 10:27:27', '06-05-2020 10:27:27');


#TEMPLATE TABLE INSERTS
insert into infy_schema.TEMPLATE (CLIENT_ID, TEMPLATE_NAME, TEMPLATE_TYPE, TEMPLATE_OUTPUT_LOCATION, TEMPLATE_SOURCE_LOCATION, ACTIVE, CRTD_BY, LAST_MDFD_BY, CRTD_TS, LAST_MDFD_TS)
VALUES (1, 'Psac2022_Mt202', 'mt202', '/home/ranga/sandbox/springboot/psac009/1/output/', '/home/ranga/sandbox/springboot/psac009/1/input','1', 'admin', 'admin', '06-05-2020 10:32:35', '06-05-2020 10:32:35');

insert into infy_schema.TEMPLATE (CLIENT_ID, TEMPLATE_NAME, TEMPLATE_TYPE, TEMPLATE_OUTPUT_LOCATION, TEMPLATE_SOURCE_LOCATION, ACTIVE, CRTD_BY, LAST_MDFD_BY, CRTD_TS, LAST_MDFD_TS)
VALUES (1, 'Mt202_Psac2022', 'psac2022', '/home/ranga/sandbox/springboot/psac009/1/output/', '/home/ranga/sandbox/springboot/psac009/1/input','1', 'admin', 'admin', '06-05-2020 10:30:35', '06-05-2020 10:30:35');

insert into infy_schema.TEMPLATE_METHOD (TEMPLATE_ID, CLIENT_ID, METHOD_VALUE, DESCRIPTION, ACTIVE, CRTD_BY, LAST_MDFD_BY, CRTD_TS, LAST_MDFD_TS)
VALUES (4,1, 'classpath:methods/1/populateXmlData01.txt', 'Populate xml template', 1, 'admin', 'admin', '06-05-2020 14:57:33', '06-05-2020 14:57:33');


INSERT INTO inf_schema.