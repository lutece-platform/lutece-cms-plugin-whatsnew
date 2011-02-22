/*
 * Copyright (c) 2002-2010, Mairie de Paris
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

import fr.paris.lutece.plugins.whatsnew.business.ElementOrderEnum;
import fr.paris.lutece.plugins.whatsnew.business.WhatsNew;
import fr.paris.lutece.plugins.whatsnew.service.WhatsNewPlugin;
import fr.paris.lutece.plugins.whatsnew.service.WhatsNewResourceIdService;
import fr.paris.lutece.plugins.whatsnew.service.WhatsNewService;
import fr.paris.lutece.plugins.whatsnew.service.parameter.WhatsNewParameterService;
import fr.paris.lutece.plugins.whatsnew.utils.constants.WhatsNewConstants;
import fr.paris.lutece.portal.business.rbac.RBAC;
import fr.paris.lutece.portal.business.user.AdminUser;
import fr.paris.lutece.portal.service.dashboard.admin.AdminDashboardComponent;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.rbac.RBACService;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.util.html.HtmlTemplate;

import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


/**
 *
 * WhatsNewAdminDashboardComponent
 *
 */
public class WhatsNewAdminDashboardComponent extends AdminDashboardComponent
{
    // TEMPLATES
    private static final String TEMPLATE_ADMIN_DASHBOARD = "admin/plugins/whatsnew/whatsnew_admindashboard.html";

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDashboardData( AdminUser user, HttpServletRequest request )
    {
        String strHtml = StringUtils.EMPTY;

        if ( RBACService.isAuthorized( WhatsNew.RESOURCE_TYPE, RBAC.WILDCARD_RESOURCES_ID,
                    WhatsNewResourceIdService.PERMISSION_MANAGE_ADVANCED_PARAMETERS, user ) )
        {
            Plugin plugin = PluginService.getPlugin( WhatsNewPlugin.PLUGIN_NAME );
            Map<String, Object> model = new HashMap<String, Object>(  );
            model.put( WhatsNewConstants.MARK_COMBO_PERIOD, WhatsNewService.getInstance(  ).getComboDays(  ) );
            model.put( WhatsNewConstants.MARK_LIST_PARAM_DEFAULT_VALUES,
                WhatsNewParameterService.getInstance(  ).getParamDefaultValues( plugin ) );
            model.put( WhatsNewConstants.MARK_DISPLAY_ORDER_DATE, ElementOrderEnum.DATE.getId(  ) );
            model.put( WhatsNewConstants.MARK_DISPLAY_ORDER_ALPHA, ElementOrderEnum.ALPHA.getId(  ) );
            model.put( WhatsNewConstants.MARK_DISPLAY_ORDER_ASC, WhatsNewConstants.DISPLAY_ASC );
            model.put( WhatsNewConstants.MARK_DISPLAY_ORDER_DESC, WhatsNewConstants.DISPLAY_DESC );

            HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_ADMIN_DASHBOARD, user.getLocale(  ), model );

            strHtml = template.getHtml(  );
        }

        return strHtml;
    }
}
