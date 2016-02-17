create table USERS(
  USERNAME   VARCHAR2(50) not null,
  PASSWORD   VARCHAR2(50) not null,
  ENABLED    NUMBER(1) not null,
  USERNAMECN VARCHAR2(50),
  primary key( username )
)

create table AUTHORITIES(
  USERNAME  VARCHAR2(50) not null,
  AUTHORITY VARCHAR2(50) not null
)

Create/Recreate primary, unique and foreign key constraints
alter table AUTHORITIES
add constraint FK_AUTHORITIES_USERS foreign key (USERNAME)
references USERS (USERNAME);