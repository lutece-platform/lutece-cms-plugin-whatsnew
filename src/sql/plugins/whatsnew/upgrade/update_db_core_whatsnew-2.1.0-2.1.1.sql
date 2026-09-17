-- liquibase formatted sql
-- changeset whatsnew:update_db_core_whatsnew-2.1.0-2.1.1.sql
-- preconditions onFail:MARK_RAN onError:WARN
-- update table whatsnew_portlet
ALTER TABLE whatsnew_portlet ADD COLUMN is_asc_sort SMALLINT DEFAULT 0 NOT NULL;
ALTER TABLE whatsnew_portlet ADD COLUMN is_dynamic SMALLINT DEFAULT 1 NOT NULL;