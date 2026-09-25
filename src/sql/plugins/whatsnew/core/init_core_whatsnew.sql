-- liquibase formatted sql
-- changeset whatsnew:init_core_whatsnew.sql
-- preconditions onFail:MARK_RAN onError:WARN
--
-- Dumping data for table core_portlet_type
--
INSERT INTO core_portlet_type (id_portlet_type,name,url_creation,url_update,home_class,plugin_name,url_docreate,create_script,create_specific,create_specific_form,url_domodify,modify_script,modify_specific,modify_specific_form) VALUES 
('WHATS_NEW_PORTLET','whatsnew.portlet.name','plugins/whatsnew/CreatePortletWhatsNew.jsp','plugins/whatsnew/ModifyPortletWhatsNew.jsp','fr.paris.lutece.plugins.whatsnew.business.portlet.WhatsNewPortletHome','whatsnew','plugins/whatsnew/DoCreatePortletWhatsNew.jsp','/admin/portlet/script_create_portlet.html','/admin/plugins/whatsnew/create_portlet_whatsnew.html','','plugins/whatsnew/DoModifyPortletWhatsNew.jsp','/admin/portlet/script_modify_portlet.html','/admin/plugins/whatsnew/modify_portlet_whatsnew.html','');

--
-- Dumping data for table core_admin_role
--
INSERT INTO core_admin_role (role_key,role_description) VALUES ('whatsnew_manager','WhatsNew management');

--
-- Dumping data for table core_admin_role_resource
--
INSERT INTO core_admin_role_resource (rbac_id,role_key,resource_type,resource_id,permission) VALUES (188,'whatsnew_manager','WHATSNEW','*','*');

--
-- Dumping data for table core_user_role
--
INSERT INTO core_user_role (role_key,id_user) VALUES ('whatsnew_manager',1);
INSERT INTO core_user_role (role_key,id_user) VALUES ('whatsnew_manager',2);

--
-- Init  table core_admin_dashboard
--
INSERT INTO core_admin_dashboard(dashboard_name, dashboard_column, dashboard_order) VALUES('whatsNewAdminDashboardComponent', 1, 1);

--
-- FreeMarker templates available for the whatsnew portlets (4.0.0), registered in the core (Section Template Management feature)
--
-- changeset whatsnew:init_core_whatsnew.sql-rev1.sql
-- preconditions onFail:MARK_RAN onError:WARN
-- precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM core_portlet_template WHERE id_portlet_type = 'WHATS_NEW_PORTLET'
INSERT INTO core_portlet_template (id_portlet_type, description, template_path) VALUES ('WHATS_NEW_PORTLET', 'Défaut', 'skin/plugins/whatsnew/portlet/whatsnew_portlet.html');
