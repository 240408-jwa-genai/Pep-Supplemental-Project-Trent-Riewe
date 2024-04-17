-- Use this script to set up your Planetarium database

-- needed for referential integrity enforcement
PRAGMA foreign_keys = 1;

create table if not exists users(
	id integer primary key,
	username text unique,
	password text
);

create table if not exists planets(
	id integer primary key,
	name text,
	ownerId integer references users(id)
);

create table if not exists moons(
	id integer primary key,
	name text,
	myPlanetId integer references planets(id)
);

insert into users (username, password) values ('myUser', 'myPass');

SELECT * from users;

INSERT INTO planets (name, ownerId) VALUES ('urath', 1);
SELECT * FROM planets;
DELETE FROM planets WHERE ownerId = 0;