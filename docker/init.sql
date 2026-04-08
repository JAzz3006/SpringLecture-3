-- этот файл будет выполняться при старте нашего контейнера
CREATE SCHEMA IF NOT EXISTS tasks_schema;
CREATE TABLE IF NOT EXISTS tasks_schema.task
(
id BIGSERIAL PRIMARY KEY,
title VARCHAR(255) NOT NULL,
description VARCHAR(255) NOT NULL,
priority INTEGER NOT NULL
)