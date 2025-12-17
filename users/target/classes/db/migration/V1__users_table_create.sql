-- stylist.st_users definition

-- Drop table

-- DROP TABLE stylist.st_users;

CREATE TABLE stylist.st_users (
	user_id uuid NOT NULL,
	"name" varchar(255) NULL,
	"password" varchar(255) NULL,
	surnames varchar(255) NULL,
	createdat timestamp(6) NULL,
	"role" varchar(255) NULL,
	email varchar(255) NULL,
	CONSTRAINT st_users_pkey PRIMARY KEY (user_id)
);