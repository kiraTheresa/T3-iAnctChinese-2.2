-- 用户表
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(20) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    is_active TINYINT(1) DEFAULT 1 NOT NULL,
    last_login_at DATETIME,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL
);

-- 项目表
CREATE TABLE IF NOT EXISTS projects (
    id VARCHAR(64) PRIMARY KEY,
    user_id INT NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- 文档表
CREATE TABLE IF NOT EXISTS documents (
    id VARCHAR(64) PRIMARY KEY,
    user_id INT NOT NULL,
    project_id VARCHAR(64),
    name VARCHAR(255) NOT NULL,
    description TEXT,
    content LONGTEXT,
    author VARCHAR(255),
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (project_id) REFERENCES projects(id),
    INDEX idx_user_project (user_id, project_id)
);

-- 实体标注表
CREATE TABLE IF NOT EXISTS entity_annotations (
    id INT AUTO_INCREMENT PRIMARY KEY,
    document_id VARCHAR(64) NOT NULL,
    start_index INT NOT NULL,
    end_index INT NOT NULL,
    label VARCHAR(50) NOT NULL,
    text_content TEXT,
    created_at DATETIME NOT NULL,
    FOREIGN KEY (document_id) REFERENCES documents(id),
    INDEX idx_document_label (document_id, label, start_index)
);

-- 地名坐标缓存表
CREATE TABLE IF NOT EXISTS location_geocodes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    lng DECIMAL(10,6),
    lat DECIMAL(10,6),
    matched_name VARCHAR(255),
    confidence VARCHAR(10),
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    CONSTRAINT unique_name UNIQUE (name)
);