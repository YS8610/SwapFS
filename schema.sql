CREATE TABLE schlesson (
    id_schlesson INT AUTO_INCREMENT,
    sch VARCHAR(10) NOT NULL,
    classtype VARCHAR(3) NOT NULL,
    lessontype VARCHAR(10) NOT NULL,
    PRIMARY KEY(id_schlesson)
    );

CREATE TABLE swaprequests(
    id INT AUTO_INCREMENT,
    chatid INT NOT NULL,
    usercomment VARCHAR(600) NOT NULL,
    lesson VARCHAR(20) NOT NULL,
    lessondate DATE NOT NULL,
    lessontime VARCHAR(30) NOT NULL,
    id_schlesson INT NOT NULL,
    PRIMARY KEY(id),
    CONSTRAINT fk_id_schlesson
        FOREIGN KEY(id_schlesson)
        REFERENCES schlesson(id_schlesson)
);


INSERT INTO schlesson VALUES
(1, cdc, 3a, prac ),
(2, cdc, 3, prac ),
(3, cdc, 2b, prac ),
(4, cdc, 2a, prac ),
(5, cdc, 2, prac ),
(6, cdc, 3a, sim ),
(7, cdc, 3, sim ),
(8, cdc, 2b, sim ),
(9, cdc, 2a, sim ),
(10, cdc, 2, sim );