/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  stwal
 * Created: Jul 11, 2022
 */
SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS;
SET FOREIGN_KEY_CHECKS = 0;
DROP DATABASE IF EXISTS superherosightertest;
CREATE DATABASE superherosightertest;

USE superherosightertest;

CREATE TABLE supe(
    supeId INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    power VARCHAR(25) NOT NULL,
    name VARCHAR(25) NOT NULL,
    fk_org_Id int,
    fk_sighting_Id int,
    description VARCHAR(50) NOT NULL,
    FOREIGN KEY (fk_org_Id)
		REFERENCES supe_org(fk_org_Id),
    FOREIGN KEY  (fk_sighting_Id)
		REFERENCES sighting(sightingId)	
);

CREATE TABLE org(
    orgId INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(25) NOT NULL,
    description VARCHAR(50) NOT NULL,
    fk_supe_Id int,
    address VARCHAR(100) NOT NULL,
    FOREIGN KEY fk_supe_Id (fk_supe_Id)
		REFERENCES supe_org (fk_supe_Id)
);

CREATE TABLE supe_org (
	fk_supe_Id int,
	fk_org_Id int, 
	FOREIGN KEY (fk_supe_Id)
		REFERENCES supe(supeId),
	FOREIGN KEY (fk_org_Id)
		REFERENCES org(orgId)
);

CREATE TABLE sighting(
    sightingId INT PRIMARY KEY AUTO_INCREMENT,
    description VARCHAR(50) NOT NULL,
    address VARCHAR(100) NOT NULL,
    fk_supe_Id int,
    FOREIGN KEY fk_supe_Id (fk_supe_Id)
		REFERENCES supe(supeId)
);

CREATE TABLE location (
	fk_sighting_Id int,
    FOREIGN KEY fk_sighting_id ( fk_sighting_id)
        REFERENCES sighting(sightingId),
    city VARCHAR(25) NOT NULL,
    state VARCHAR(25) NOT NULL,
    lat FLOAT,
    lon FLOAT    
);
SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS;