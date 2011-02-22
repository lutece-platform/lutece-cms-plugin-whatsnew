<%@ page errorPage="../../ErrorPage.jsp" %>
<jsp:useBean id="whatsNew" scope="session" class="fr.paris.lutece.plugins.whatsnew.web.WhatsNewJspBean" />
<% 
	whatsNew.init( request, fr.paris.lutece.plugins.whatsnew.web.portlet.WhatsNewPortletJspBean.RIGHT_MANAGE_ADMIN_SITE );
    response.sendRedirect( whatsNew.doModifyWhatsNewParameterDefaultValues( request ));

%>
