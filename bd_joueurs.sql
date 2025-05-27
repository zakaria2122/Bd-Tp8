DROP TABLE IF EXISTS MESSAGE;
DROP TABLE IF EXISTS JOUEUR;
CREATE TABLE JOUEUR (
  numJoueur decimal(6,0) NOT NULL,
  pseudo varchar(10) NOT NULL,
  motdepasse varchar(500) NOT NULL,
  abonne char(1) DEFAULT NULL,
  main char(1) DEFAULT NULL,
  niveau decimal(1,0) DEFAULT NULL,
  PRIMARY KEY (numJoueur),
  UNIQUE KEY pseudo (pseudo)
);
CREATE TABLE MESSAGE (
  idMsg decimal(6,0) NOT NULL,
  dateMsg datetime DEFAULT NULL,
  contenuMsg text,
  luMsg char(1) DEFAULT NULL,
  idUt1 decimal(6,0) DEFAULT NULL,
  idUt2 decimal(6,0) DEFAULT NULL,
  PRIMARY KEY (idMsg),
  KEY idUt1 (idUt1),
  KEY idUt2 (idUt2),
  CONSTRAINT MESSAGE_ibfk_1 FOREIGN KEY (idUt1) REFERENCES JOUEUR (numJoueur),
  CONSTRAINT MESSAGE_ibfk_2 FOREIGN KEY (idUt2) REFERENCES JOUEUR (numJoueur)
);
INSERT INTO JOUEUR VALUES
       (1,'mario','mario','O','D',1),
       (2,'wario','wario','N','D',2),
       (3,'donkey','donkey','O','G',1),
       (4,'yoshi','zelda','N','G',3),
       (5,'peach','peach','N','D',1);


INSERT INTO MESSAGE VALUES
       (1,'2024-04-12 10:12:00','bla bla1','N',1,2),
       (2,'2024-04-12 10:12:12','bla bla2','N',1,2),
       (3,'2024-04-12 10:12:07','bla bla3','N',1,2),
       (4,'2024-04-12 10:12:18','bla bla4','N',2,1),
       (5,'2024-04-09 11:12:00','bla bla5','N',2,1),
       (6,'2024-04-09 10:24:00','bla bla6','N',3,2),
       (7,'2024-04-09 10:24:00','bla bla7','N',3,2),
       (8,'2024-04-09 10:25:05','bla bla8','N',4,1),
       (9,'2024-04-10 10:13:06','bla bla9','N',4,2),
       (10,'2024-04-10 10:12:07','bla bla10','N',5,4),
       (11,'2024-04-10 10:12:08','bla bla11','N',5,4),
       (12,'2024-04-11 12:12:10','bla bla12','N',5,1),
       (13,'2024-04-11 10:10:00','bla bla13','N',5,3),
       (14,'2024-04-11 10:12:00','bla bla14','N',1,4),
       (15,'2024-04-11 10:13:00','bla bla15','N',1,4),
       (16,'2024-04-11 10:15:00','bla bla16','N',4,2),
       (17,'2024-04-11 10:14:00','bla bla17','N',2,3);
