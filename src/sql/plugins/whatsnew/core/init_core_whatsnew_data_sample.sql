-- liquibase formatted sql
-- changeset whatsnew:init_core_whatsnew_data_sample.sql
-- preconditions onFail:MARK_RAN onError:WARN
--
-- Dumping data for table whatsnew_portlet
--
INSERT INTO whatsnew_portlet (id_portlet,show_documents,show_portlets,show_pages,period,nb_elements_max,elements_order) VALUES 
 (84,1,1,1,30,10,0);