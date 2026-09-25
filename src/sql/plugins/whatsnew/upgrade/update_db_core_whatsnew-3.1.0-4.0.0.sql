-- liquibase formatted sql
-- changeset whatsnew:update_db_core_whatsnew-3.1.0-4.0.0.sql
-- preconditions onFail:MARK_RAN onError:WARN
-- precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM information_schema.columns WHERE table_schema = database() AND table_name = 'core_portlet' AND column_name = 'id_template'
--
-- The XSL based rendering has been removed : every whatsnew portlet is now rendered with a FreeMarker template
-- chosen per portlet among the templates registered in the core for the portlet type (core_portlet_template,
-- core_portlet.id_template, Section Template Management feature). The old "Défaut" XSL style is mapped to the default template.
--
-- The plugin upgrade scripts run BEFORE the core upgrade script in the same liquibase run (sql/plugins/* sorts before sql/upgrade/*) :
-- the core structures are created here when they do not exist yet, with the very same statements as the core script, which is then skipped.
--
ALTER TABLE core_portlet ADD COLUMN id_template int default 0 NOT NULL;

-- changeset whatsnew:update_db_core_whatsnew-3.1.0-4.0.0.sql-rev1.sql
-- preconditions onFail:MARK_RAN onError:WARN
-- precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM information_schema.tables WHERE table_schema = database() AND table_name = 'core_portlet_template'
CREATE TABLE IF NOT EXISTS core_portlet_template (
	id_template int AUTO_INCREMENT NOT NULL,
	id_portlet_type varchar(50) default NULL,
	description varchar(255) default NULL,
	template_path varchar(255) default NULL,
	PRIMARY KEY (id_template)
);

--
-- Templates available for the whatsnew portlets
--
-- changeset whatsnew:update_db_core_whatsnew-3.1.0-4.0.0.sql-rev2.sql
-- preconditions onFail:MARK_RAN onError:WARN
-- precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM core_portlet_template WHERE id_portlet_type = 'WHATS_NEW_PORTLET'
INSERT INTO core_portlet_template (id_portlet_type, description, template_path) VALUES ('WHATS_NEW_PORTLET', 'Défaut', 'skin/plugins/whatsnew/portlet/whatsnew_portlet.html');

--
-- Template chosen for each portlet : the only XSL style ("Défaut", 900) -> default template (id_template = 0)
--
-- changeset whatsnew:update_db_core_whatsnew-3.1.0-4.0.0.sql-rev3.sql
-- preconditions onFail:MARK_RAN onError:WARN
UPDATE core_portlet SET id_style = 0 WHERE id_portlet_type = 'WHATS_NEW_PORTLET';

-- changeset whatsnew:update_db_core_whatsnew-3.1.0-4.0.0.sql-rev4.sql
-- preconditions onFail:MARK_RAN onError:WARN
-- comment Legacy XSL style tables left the core for plugin-xmltransformer and are absent from many databases: skip instead of failing the whole update
-- precondition-sql-check expectedResult:3 SELECT COUNT(1) from INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA=database() AND TABLE_NAME IN ('core_style_mode_stylesheet','core_stylesheet','core_style');
DELETE FROM core_style_mode_stylesheet WHERE id_style = 900;
DELETE FROM core_style WHERE id_style = 900;
DELETE FROM core_stylesheet WHERE id_stylesheet = 307;
