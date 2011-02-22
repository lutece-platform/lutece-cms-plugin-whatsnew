--
-- Table structure for table whatsnew_portlet_whatsnew
--
DROP TABLE IF EXISTS whatsnew_portlet_whatsnew;
CREATE TABLE whatsnew_portlet_whatsnew (
	id_whatsnew_portlet int default 0 NOT NULL,
	id_portlet int default 0 NOT NULL,
	PRIMARY KEY (id_whatsnew_portlet,id_portlet)
);

--
-- Table structure for table whatsnew_page_whatsnew
--
DROP TABLE IF EXISTS whatsnew_page_whatsnew;
CREATE TABLE whatsnew_page_whatsnew (
	id_whatsnew_portlet int default 0 NOT NULL,
	id_page int default 0 NOT NULL,
	PRIMARY KEY (id_whatsnew_portlet,id_page)
);

--
-- Table structure for table whatsnew_page_whatsnew
--
DROP TABLE IF EXISTS whatsnew_page_whatsnew;
CREATE TABLE whatsnew_page_whatsnew (
	id_whatsnew_portlet int default 0 NOT NULL,
	id_portlet int default 0 NOT NULL,
	id_document int default 0 NOT NULL,
	PRIMARY KEY (id_whatsnew_portlet,id_portlet,id_document)
);

--
-- Table structure for table whatsnew_whatsnew_parameter
--
CREATE TABLE whatsnew_whatsnew_parameter (
	parameter_key varchar(100) NOT NULL,
	parameter_value varchar(100) NOT NULL,
	PRIMARY KEY (parameter_key)
);

--
-- Init table whatsnew_whatsnew_parameter
--
INSERT INTO whatsnew_whatsnew_parameter (parameter_key, parameter_value) VALUES ('show_portlets', '1');
INSERT INTO whatsnew_whatsnew_parameter (parameter_key, parameter_value) VALUES ('show_pages', '1');
INSERT INTO whatsnew_whatsnew_parameter (parameter_key, parameter_value) VALUES ('show_documents', '1');
INSERT INTO whatsnew_whatsnew_parameter (parameter_key, parameter_value) VALUES ('text_period', '7');
INSERT INTO whatsnew_whatsnew_parameter (parameter_key, parameter_value) VALUES ('text_nbElements', '10');
INSERT INTO whatsnew_whatsnew_parameter (parameter_key, parameter_value) VALUES ('display_order', '0');
INSERT INTO whatsnew_whatsnew_parameter (parameter_key, parameter_value) VALUES ('display_order_asc_desc', '0');
INSERT INTO whatsnew_whatsnew_parameter (parameter_key, parameter_value) VALUES ('is_dynamic', '1');
