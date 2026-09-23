/*
 * Copyright (c) 2002-2014, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.whatsnew.web;

import fr.paris.lutece.api.user.User;
import fr.paris.lutece.plugins.whatsnew.business.WhatsNew;
import fr.paris.lutece.plugins.whatsnew.service.WhatsNewPlugin;
import fr.paris.lutece.plugins.whatsnew.service.WhatsNewResourceIdService;
import fr.paris.lutece.plugins.whatsnew.service.parameter.WhatsNewParameterService;
import fr.paris.lutece.plugins.whatsnew.utils.constants.WhatsNewConstants;
import fr.paris.lutece.portal.business.rbac.RBAC;
import fr.paris.lutece.portal.service.admin.AccessDeniedException;
import fr.paris.lutece.portal.service.message.AdminMessage;
import fr.paris.lutece.portal.service.message.AdminMessageService;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.rbac.RBACService;
import fr.paris.lutece.portal.util.mvc.admin.MVCAdminJspBean;
import fr.paris.lutece.portal.util.mvc.admin.annotations.Controller;
import fr.paris.lutece.portal.util.mvc.commons.annotations.Action;
import fr.paris.lutece.portal.util.mvc.commons.annotations.View;
import fr.paris.lutece.portal.web.cdi.mvc.Models;
import fr.paris.lutece.util.ReferenceItem;
import fr.paris.lutece.util.ReferenceList;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import jakarta.servlet.http.HttpServletRequest;


/**
 *
 * WhatsNewJspBean
 *
 */
@RequestScoped
@Named
@Controller( controllerJsp = WhatsNewJspBean.CONTROLLER_JSP, controllerPath = WhatsNewJspBean.CONTROLLER_PATH, right = WhatsNewJspBean.RIGHT_MANAGE_ADMIN_SITE, securityTokenEnabled = true )
public class WhatsNewJspBean extends MVCAdminJspBean
{
    public static final String CONTROLLER_JSP = "ManageAdvancedParameters.jsp";
    public static final String CONTROLLER_PATH = "jsp/admin/plugins/whatsnew/";
    public static final String RIGHT_MANAGE_ADMIN_SITE = "CORE_ADMIN_SITE";
    public static final String ACTION_MODIFY_PARAMETER_DEFAULT_VALUES = "modifyWhatsNewParameterDefaultValues";

    private static final long serialVersionUID = 1L;

    private static final String VIEW_MANAGE_ADVANCED_PARAMETERS = "manageAdvancedParameters";
    private static final String TEMPLATE_MANAGE_ADVANCED_PARAMETERS = "admin/plugins/whatsnew/manage_advanced_parameters.html";
    private static final String PROPERTY_PAGE_TITLE = "whatsnew.manage_advanced_parameters.pageTitle";
    private static final String MESSAGE_PARAMETERS_SAVED = "whatsnew.manage_advanced_parameters.messageSaved";
    private static final String MESSAGE_UNAUTHORIZED = "User not authorized to manage the advanced parameters";

    @Inject
    private WhatsNewParameterService _parameterService;

    /**
     * Displays the advanced parameters form
     * @param request HttpServletRequest
     * @param model the model
     * @return the page
     * @throws AccessDeniedException when the user does not have the permission
     */
    @View( value = VIEW_MANAGE_ADVANCED_PARAMETERS, defaultView = true )
    public String getManageAdvancedParameters( HttpServletRequest request, Models model ) throws AccessDeniedException
    {
        checkPermission( );
        WhatsNewAdminDashboardComponent.getAdvancedParametersModel( request ).forEach( model::put );

        return getPage( PROPERTY_PAGE_TITLE, TEMPLATE_MANAGE_ADVANCED_PARAMETERS, model );
    }

    /**
     * Modify whatsnew parameter default values
     * @param request HttpServletRequest
     * @return the redirection
     * @throws AccessDeniedException when the user does not have the permission
     */
    @Action( ACTION_MODIFY_PARAMETER_DEFAULT_VALUES )
    public String doModifyWhatsNewParameterDefaultValues( HttpServletRequest request ) throws AccessDeniedException
    {
        checkPermission( );

        String strError = getNbElementsMaxError( request.getParameter( WhatsNewConstants.PARAMETER_NB_ELEMENTS_MAX ) );

        if ( strError != null )
        {
            return redirect( request, AdminMessageService.getMessageUrl( request, strError, AdminMessage.TYPE_STOP ) );
        }

        Plugin plugin = PluginService.getPlugin( WhatsNewPlugin.PLUGIN_NAME );

        ReferenceList listParams = _parameterService.getParamDefaultValues( plugin );

        for ( ReferenceItem param : listParams )
        {
            String strParamValue = request.getParameter( param.getCode(  ) );

            if ( StringUtils.isBlank( strParamValue ) )
            {
                strParamValue = WhatsNewConstants.ZERO;
            }

            param.setName( strParamValue );
            _parameterService.update( param, plugin );
        }

        addInfo( MESSAGE_PARAMETERS_SAVED, getLocale( ) );

        return redirectView( request, VIEW_MANAGE_ADVANCED_PARAMETERS );
    }

    /**
     * Checks that the user may manage the advanced parameters
     * @throws AccessDeniedException when the user does not have the permission
     */
    private void checkPermission( ) throws AccessDeniedException
    {
        if ( !RBACService.isAuthorized( WhatsNew.RESOURCE_TYPE, RBAC.WILDCARD_RESOURCES_ID,
                    WhatsNewResourceIdService.PERMISSION_MANAGE_ADVANCED_PARAMETERS, (User) getUser(  ) ) )
        {
            throw new AccessDeniedException( MESSAGE_UNAUTHORIZED );
        }
    }

    /**
     * Checks the default maximum number of elements
     * @param strNbElementsMax the submitted value
     * @return the key of the error message, or null when the value is a strictly positive number
     */
    private static String getNbElementsMaxError( String strNbElementsMax )
    {
        if ( StringUtils.isBlank( strNbElementsMax ) )
        {
            return WhatsNewConstants.MESSAGE_MANDATORY_PORTLET_NB_ELEMENTS_MAX;
        }

        if ( !StringUtils.isNumeric( strNbElementsMax ) )
        {
            return WhatsNewConstants.MESSAGE_NOT_VALID_PORTLET_NB_ELEMENTS_MAX;
        }

        if ( NumberUtils.toInt( strNbElementsMax, 0 ) <= 0 )
        {
            return WhatsNewConstants.MESSAGE_NEGATIVE_PORTLET_NB_ELEMENTS_MAX;
        }

        return null;
    }
}
