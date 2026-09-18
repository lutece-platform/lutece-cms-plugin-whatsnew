-- liquibase formatted sql
-- changeset whatsnew:update_db_core_whatsnew-2.0.11-2.0.12.sql
-- preconditions onFail:MARK_RAN onError:WARN
INSERT INTO core_portlet_type(id_portlet_type,name,url_creation,url_update,home_class,plugin_name,url_docreate,create_script,create_specific,create_specific_form,url_domodify,modify_script,modify_specific,modify_specific_form) VALUES
('WHATS_NEW_PORTLET','whatsnew.portlet.name','plugins/whatsnew/CreatePortletWhatsNew.jsp','plugins/whatsnew/ModifyPortletWhatsNew.jsp','fr.paris.lutece.plugins.whatsnew.business.portlet.WhatsNewPortletHome','whatsnew','plugins/whatsnew/DoCreatePortletWhatsNew.jsp','/admin/portlet/script_create_portlet.html','/admin/plugins/whatsnew/create_portlet_whatsnew.html','','plugins/whatsnew/DoModifyPortletWhatsNew.jsp','/admin/portlet/script_modify_portlet.html','/admin/plugins/whatsnew/modify_portlet_whatsnew.html','');
