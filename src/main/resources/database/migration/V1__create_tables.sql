CREATE TABLE app_user(
    id UUID NOT NULL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ
);

CREATE TABLE project(
    id UUID NOT NULL PRIMARY KEY,
    app_user_id UUID REFERENCES app_user(id) NOT NULL,
    name VARCHAR(50) NOT NULL UNIQUE,
    description TEXT,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ
);

CREATE TABLE task(
    id UUID NOT NULL PRIMARY KEY,
    project_id UUID REFERENCES project(id) NOT NULL,
    name VARCHAR(50) NOT NULL,
    status VARCHAR(8) NOT NULL,
    app_user_id UUID REFERENCES app_user(id),
    description TEXT,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ
);

CREATE TABLE task_comment(
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
