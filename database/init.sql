CREATE TABLE IF NOT EXISTS app_user(
	id UUID NOT NULL PRIMARY KEY,
	name VARCHAR(50) NOT NULL UNIQUE,
	created_at TIMESTAMPTZ NOT NULL,
	updated_at TIMESTAMPTZ
);

CREATE TABLE IF NOT EXISTS project(
	id UUID NOT NULL PRIMARY KEY,
	user_id UUID REFERENCES app_user(id) NOT NULL,
	name VARCHAR(50) NOT NULL UNIQUE,
	description TEXT,
	created_at TIMESTAMPTZ NOT NULL,
	updated_at TIMESTAMPTZ
);

CREATE TABLE IF NOT EXISTS task(
	id UUID NOT NULL PRIMARY KEY,
	project_id UUID REFERENCES project(id) NOT NULL,
	name VARCHAR(50) NOT NULL,
	status VARCHAR(8) NOT NULL,
	assignee UUID REFERENCES app_user(id),
	description TEXT,
	created_at TIMESTAMPTZ NOT NULL,
	updated_at TIMESTAMPTZ
);

CREATE TABLE IF NOT EXISTS task_comment(
	id UUID NOT NULL PRIMARY KEY,
	app_user_id UUID REFERENCES app_user(id) NOT NULL,
	task_id UUID REFERENCES task(id) NOT NULL,
	title VARCHAR(50) NOT NULL,
	content VARCHAR(500),
	created_at TIMESTAMPTZ NOT NULL,
	updated_at TIMESTAMPTZ
);

CREATE INDEX idx_task_project_id ON task(project_id);
CREATE INDEX idx_task_status ON task(status);
CREATE INDEX idx_task_comment_task_id ON task_comment(task_id);

INSERT INTO app_user(id, name, created_at, updated_at)
VALUES ('21bae819-bbf8-4e89-93c2-1f6d2ed95921', 'admin', NOW(), null);

INSERT INTO project(id, user_id, name, description, created_at, updated_at)
VALUES ('6183ce4c-2894-40f2-a20c-971759902ee3', '21bae819-bbf8-4e89-93c2-1f6d2ed95921', 'Tasktracker', 'Testprojekt zur Verwaltung von Aufgaben', NOW(), null);

INSERT INTO task(id, project_id, name, status, assignee, description, created_at, updated_at)
VALUES ('c0f2b16a-5179-4b27-90e5-530913416175', '6183ce4c-2894-40f2-a20c-971759902ee3', '0.1 - Projektgrundlage schaffen', 'done', '21bae819-bbf8-4e89-93c2-1f6d2ed95921', 'Ein lauffähiges Spring-Boot-4-Projekt mit PostgreSQL-Verbindung.', NOW(), null);

INSERT INTO task(id, project_id, name, status, assignee, description, created_at, updated_at)
VALUES ('1755aef0-7fc6-472e-95b2-7f0c5e8cb838', '6183ce4c-2894-40f2-a20c-971759902ee3', '0.2 – Datenbank bewusst kennenlernen', 'active', '21bae819-bbf8-4e89-93c2-1f6d2ed95921', 'Modellierung aus Datenbanksicht', NOW(), null);

INSERT INTO task_comment(id, app_user_id, task_id, title, content, created_at, updated_at)
VALUES ('343a8041-f80c-486e-85a3-213163203344', '21bae819-bbf8-4e89-93c2-1f6d2ed95921', 'c0f2b16a-5179-4b27-90e5-530913416175', 'Erster Kommentar', 'Meine erste fertige Aufgabe in diesem Projekt', NOW(), null);
