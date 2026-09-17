<%@ page errorPage="../../ErrorPage.jsp" %>
<jsp:include page="../../PortletAdminHeader.jsp" />

<%@page import="fr.paris.lutece.plugins.whatsnew.web.portlet.WhatsNewPortletJspBean"%>

${ whatsNewPortletJspBean.init( pageContext.request, WhatsNewPortletJspBean.RIGHT_MANAGE_ADMIN_SITE ) }
${ whatsNewPortletJspBean.getModify( pageContext.request ) }

<%@ include file="../../AdminFooter.jsp" %>
