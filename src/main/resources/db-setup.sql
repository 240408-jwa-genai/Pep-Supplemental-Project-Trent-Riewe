-- Use this script to set up your Planetarium database

-- needed for referential integrity enforcement
PRAGMA foreign_keys = 1;
DROP TABLE IF EXISTS users;

create table if not exists users(
	id integer primary key,
	username text unique,
	password text
);

insert into users (username, password) values 
	('myUser', 'myPass'),
	('myUser2', 'myPass');

SELECT * from users;

DROP TABLE IF EXISTS planets;
create table if not exists planets(
	id integer primary key,
	name text,
	ownerId integer references users(id)
);

INSERT INTO planets (name, ownerId) VALUES ('urath', 1);
SELECT * FROM planets;
DELETE FROM planets WHERE ownerId = 0;



create table if not exists moons(
	id integer primary key,
	name text,
	myPlanetId integer references planets(id)
);

INSERT INTO moons (name, myPlanetId) values('Luna', 1);
INSERT INTO moons (name, myPlanetId) values('Ceres', 2);
SELECT * FROM moons;

SELECT m.id, m.name, m.myPlanetId FROM moons m 
JOIN planets p ON p.id = m.myPlanetId 
WHERE p.ownerId = 2;

