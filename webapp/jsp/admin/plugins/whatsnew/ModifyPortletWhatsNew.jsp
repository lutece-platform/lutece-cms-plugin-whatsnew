<%@ page errorPage="../../ErrorPage.jsp" %>

<%@page import="fr.paris.lutece.plugins.whatsnew.web.portlet.WhatsNewPortletJspBean"%>

${ whatsNewPortletJspBean.init( pageContext.request, WhatsNewPortletJspBean.RIGHT_MANAGE_ADMIN_SITE ) }
${ pageContext.setAttribute( 'strErrorUrl', whatsNewPortletJspBean.getModifyErrorUrl( pageContext.request ) ) }
${ empty strErrorUrl ? '' : pageContext.response.sendRedirect( strErrorUrl ) }
<jsp:include page="../../PortletAdminHeader.jsp" />

${ empty strErrorUrl ? whatsNewPortletJspBean.getModify( pageContext.request ) : '' }

<%@ include file="../../AdminFooter.jsp" %>
