DROP TABLE IF EXISTS run;

CREATE TABLE IF NOT EXISTS Run (
    id INT NOT NULL,
    title varchar(250) NOT NULL,
    started_on timestamp NOT NULL,
    completed_on timestamp NOT NULL,
    miles INT NOT NULL,
    locationn varchar(10) NOT NULL,
    versionn INT,
    PRIMARY KEY (id)
);
