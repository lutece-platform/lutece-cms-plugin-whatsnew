<%@ page errorPage="../../ErrorPage.jsp" %>

<%@page import="fr.paris.lutece.plugins.whatsnew.web.portlet.WhatsNewPortletJspBean"%>

${ whatsNewJspBean.init( pageContext.request, WhatsNewPortletJspBean.RIGHT_MANAGE_ADMIN_SITE ) }
${ pageContext.response.sendRedirect( whatsNewJspBean.doModifyWhatsNewParameterDefaultValues( pageContext.request ) ) }
