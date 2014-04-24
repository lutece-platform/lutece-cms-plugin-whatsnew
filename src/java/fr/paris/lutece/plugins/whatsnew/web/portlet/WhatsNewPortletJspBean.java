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
package fr.paris.lutece.plugins.whatsnew.web.portlet;

import fr.paris.lutece.plugins.whatsnew.business.ElementOrderEnum;
import fr.paris.lutece.plugins.whatsnew.business.IWhatsNew;
import fr.paris.lutece.plugins.whatsnew.business.PortletDocumentLink;
import fr.paris.lutece.plugins.whatsnew.business.portlet.WhatsNewPortlet;
import fr.paris.lutece.plugins.whatsnew.service.WhatsNewPlugin;
import fr.paris.lutece.plugins.whatsnew.service.WhatsNewService;
import fr.paris.lutece.plugins.whatsnew.service.parameter.WhatsNewParameterService;
import fr.paris.lutece.plugins.whatsnew.service.portlet.WhatsNewPortletService;
import fr.paris.lutece.plugins.whatsnew.utils.constants.WhatsNewConstants;
import fr.paris.lutece.portal.business.portlet.PortletHome;
import fr.paris.lutece.portal.service.message.AdminMessage;
import fr.paris.lutece.portal.service.message.AdminMessageService;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.web.portlet.PortletJspBean;
import fr.paris.lutece.util.html.HtmlTemplate;

import org.apache.commons.lang.StringUtils;

import java.sql.Timestamp;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


/**
 *
 * WhatsNewPortletJspBean
 *
 */
public class WhatsNewPortletJspBean extends PortletJspBean
{
    // Right
    public static final String RIGHT_MANAGE_ADMIN_SITE = "CORE_ADMIN_SITE";

    /**
     * Get the WhatsNewPlugin
     * @return a {@link Plugin}
     */
    private Plugin getPlugin(  )
    {
        return PluginService.getPlugin( WhatsNewPlugin.PLUGIN_NAME );
    }

    /**
     * Returns portlet "what's new" 's creation form
     * @param request request
     * @return Html form
     */
    public String getCreate( HttpServletRequest request )
    {
        String strPageId = request.getParameter( PARAMETER_PAGE_ID );
        String strPortletTypeId = request.getParameter( PARAMETER_PORTLET_TYPE_ID );

        Map<String, Object> model = new HashMap<String, Object>(  );
        model.put( WhatsNewConstants.MARK_COMBO_PERIOD, WhatsNewService.getInstance(  ).getComboDays(  ) );
        model.put( WhatsNewConstants.MARK_LIST_PARAM_DEFAULT_VALUES,
            WhatsNewParameterService.getInstance(  ).getParamDefaultValues( getPlugin(  ) ) );
        initializeModel( model );

        HtmlTemplate template = getCreateTemplate( strPageId, strPortletTypeId, model );

        return template.getHtml(  );
    }

    /**
     * Returns portlet "what's new" 's modification form
     * @param request request
     * @return Html form
     */
    public String getModify( HttpServletRequest request )
    {
        String strUrl = StringUtils.EMPTY;
        String strPortletId = request.getParameter( PARAMETER_PORTLET_ID );

        if ( StringUtils.isNotBlank( strPortletId ) && StringUtils.isNumeric( strPortletId ) )
        {
            int nPortletId = Integer.parseInt( strPortletId );
            WhatsNewPortlet portlet = WhatsNewPortletService.getInstance(  ).getPortlet( nPortletId );

            if ( portlet != null )
            {
                Map<String, Object> model = new HashMap<String, Object>(  );
                model.put( WhatsNewConstants.MARK_MODERATED_ELEMENTS_LIST,
                    WhatsNewService.getInstance(  ).getModeratedElementsListHtml( portlet, getLocale(  ) ) );
                model.put( WhatsNewConstants.MARK_COMBO_PERIOD, WhatsNewService.getInstance(  ).getComboDays(  ) );
                model.put( WhatsNewConstants.MARK_WHATSNEW_PORTLET, portlet );
                initializeModel( model );

                HtmlTemplate template = getModifyTemplate( portlet, model );

                strUrl = template.getHtml(  );
            }
            else
            {
                strUrl = AdminMessageService.getMessageUrl( request, WhatsNewConstants.MESSAGE_OBJECT_NOT_FOUND,
                        AdminMessage.TYPE_ERROR );
            }
        }
        else
        {
            strUrl = AdminMessageService.getMessageUrl( request, WhatsNewConstants.MESSAGE_NOT_NUMERIC,
                    AdminMessage.TYPE_ERROR );
        }

        return strUrl;
    }

    /**
     * Process whatsNewPortlet's creation
     * @param request request
     * @return Portlet's modification url
     */
    public String doCreate( HttpServletRequest request )
    {
        String strUrl = StringUtils.EMPTY;
        String strIdPage = request.getParameter( PARAMETER_PAGE_ID );

        if ( StringUtils.isNotBlank( strIdPage ) && StringUtils.isNumeric( strIdPage ) )
        {
            WhatsNewPortlet portlet = new WhatsNewPortlet(  );
            String strError = getPortletData( portlet, request );

            if ( StringUtils.isBlank( strError ) )
            {
                int nIdPage = Integer.parseInt( strIdPage );
                portlet.setPageId( nIdPage );

                // Creating portlet
                WhatsNewPortletService.getInstance(  ).create( portlet );

                //Displays the page with the new Portlet
                strUrl = getPageUrl( portlet.getPageId(  ) );
            }
            else
            {
                strUrl = strError;
            }
        }
        else
        {
            strUrl = AdminMessageService.getMessageUrl( request, WhatsNewConstants.MESSAGE_NOT_NUMERIC,
                    AdminMessage.TYPE_ERROR );
        }

        return strUrl;
    }

    /**
     * Process portlet's modification
     * @param request request
     * @return The Jsp management URL of the process result
     */
    public String doModify( HttpServletRequest request )
    {
        String strUrl = StringUtils.EMPTY;
        String strPortletId = request.getParameter( PARAMETER_PORTLET_ID );

        if ( StringUtils.isNotBlank( strPortletId ) && StringUtils.isNumeric( strPortletId ) )
        {
            int nPortletId = Integer.parseInt( strPortletId );
            WhatsNewPortlet portlet = (WhatsNewPortlet) PortletHome.findByPrimaryKey( nPortletId );

            if ( portlet != null )
            {
                String strError = getPortletData( portlet, request );

                if ( StringUtils.isBlank( strError ) )
                {
                    WhatsNewPortletService.getInstance(  ).update( portlet );
                    setModeratedElements( portlet, request );
                    strUrl = getPageUrl( portlet.getPageId(  ) );
                }
                else
                {
                    strUrl = strError;
                }
            }
            else
            {
                strUrl = AdminMessageService.getMessageUrl( request, WhatsNewConstants.MESSAGE_OBJECT_NOT_FOUND,
                        AdminMessage.TYPE_ERROR );
            }
        }
        else
        {
            strUrl = AdminMessageService.getMessageUrl( request, WhatsNewConstants.MESSAGE_NOT_NUMERIC,
                    AdminMessage.TYPE_ERROR );
        }

        return strUrl;
    }

    /**
     * Initialize the model
     * @param model the model
     */
    private void initializeModel( Map<String, Object> model )
    {
        model.put( WhatsNewConstants.MARK_PLUGIN_DOCUMENT_ACTIVATED,
            WhatsNewService.getInstance(  ).isPluginDocumentActivated(  ) );
        model.put( WhatsNewConstants.MARK_DISPLAY_ORDER_DATE, ElementOrderEnum.DATE.getId(  ) );
        model.put( WhatsNewConstants.MARK_DISPLAY_ORDER_ALPHA, ElementOrderEnum.ALPHA.getId(  ) );
        model.put( WhatsNewConstants.MARK_DISPLAY_ORDER_ASC, WhatsNewConstants.DISPLAY_ASC );
        model.put( WhatsNewConstants.MARK_DISPLAY_ORDER_DESC, WhatsNewConstants.DISPLAY_DESC );
    }

    /**
     * Process the validation of the portlet
     * @param portlet the portlet to modify and validate
     * @param request the request containing the informations to store in the portlet
     * @return WhatsNewPortlet the new portlet
     */
    private String getPortletData( WhatsNewPortlet portlet, HttpServletRequest request )
    {
        // Getting portlet's common attibuts
        String strError = setPortletCommonData( request, portlet );

        String strNbElementsMax = request.getParameter( WhatsNewConstants.PARAMETER_NB_ELEMENTS_MAX );

        if ( StringUtils.isNotBlank( strNbElementsMax ) )
        {
            if ( StringUtils.isNumeric( strNbElementsMax ) )
            {
                int nNbElementsMax = Integer.parseInt( strNbElementsMax );

                if ( nNbElementsMax > 0 )
                {
                    String strIsAscSort = request.getParameter( WhatsNewConstants.PARAMETER_DISPLAY_ORDER_ASC_DESC );
                    boolean bIsAscSort = false;

                    if ( StringUtils.isNotBlank( strIsAscSort ) && StringUtils.isNumeric( strIsAscSort ) &&
                            ( Integer.parseInt( strIsAscSort ) == WhatsNewConstants.DISPLAY_ASC ) )
                    {
                        bIsAscSort = true;
                    }

                    String strPeriod = request.getParameter( WhatsNewConstants.PARAMETER_PERIOD );
                    String strElementOrder = request.getParameter( WhatsNewConstants.PARAMETER_DISPLAY_ORDER );

                    if ( StringUtils.isNotBlank( strPeriod ) && StringUtils.isNumeric( strPeriod ) &&
                            StringUtils.isNotBlank( strElementOrder ) && StringUtils.isNumeric( strElementOrder ) )
                    {
                        int nPeriod = Integer.parseInt( strPeriod );
                        int nElementOrder = Integer.parseInt( strElementOrder );

                        String strShowDocuments = request.getParameter( WhatsNewConstants.PARAMETER_SHOW_DOCUMENTS );
                        String strShowPortlets = request.getParameter( WhatsNewConstants.PARAMETER_SHOW_PORTLETS );
                        String strShowPages = request.getParameter( WhatsNewConstants.PARAMETER_SHOW_PAGES );
                        String strIsDynamic = request.getParameter( WhatsNewConstants.PARAMETER_IS_DYNAMIC );

                        portlet.setShowDocuments( strShowDocuments != null );
                        portlet.setShowPortlets( strShowPortlets != null );
                        portlet.setShowPages( strShowPages != null );
                        portlet.setDynamic( strIsDynamic != null );
                        portlet.setAscSort( bIsAscSort );

                        portlet.setPeriod( nPeriod );
                        portlet.setNbElementsMax( nNbElementsMax );
                        portlet.setElementsOrder( nElementOrder );
                    }
                    else
                    {
                        strError = AdminMessageService.getMessageUrl( request, WhatsNewConstants.MESSAGE_NOT_NUMERIC,
                                AdminMessage.TYPE_ERROR );
                    }
                }
                else
                {
                    strError = AdminMessageService.getMessageUrl( request,
                            WhatsNewConstants.MESSAGE_NEGATIVE_PORTLET_NB_ELEMENTS_MAX, AdminMessage.TYPE_ERROR );
                }
            }
            else
            {
                strError = AdminMessageService.getMessageUrl( request,
                        WhatsNewConstants.MESSAGE_NOT_VALID_PORTLET_NB_ELEMENTS_MAX, AdminMessage.TYPE_ERROR );
            }
        }
        else
        {
            strError = AdminMessageService.getMessageUrl( request,
                    WhatsNewConstants.MESSAGE_MANDATORY_PORTLET_NB_ELEMENTS_MAX, AdminMessage.TYPE_ERROR );
        }

        return strError;
    }

    /**
     * Set the moderated elements
     * @param portlet the portlet
     * @param request {@link HttpServletRequest}
     */
    private void setModeratedElements( WhatsNewPortlet portlet, HttpServletRequest request )
    {
        Timestamp limitTimestamp = WhatsNewService.getInstance(  )
                                                  .getTimestampFromPeriodAndCurrentDate( portlet.getPeriod(  ),
                request.getLocale(  ) );
        WhatsNewPortletService.getInstance(  ).removeModeratedElements( portlet, getPlugin(  ) );
        setModeratedPortlets( portlet, request, limitTimestamp );
        setModeratedPages( portlet, request, limitTimestamp );
        setModeratedDocuments( portlet, request, limitTimestamp );
    }

    /**
     * Set the moderated portlets
     * @param portlet the portlet
     * @param request {@link HttpServletRequest}
     * @param limitTimestamp the date limit
     */
    private void setModeratedPortlets( WhatsNewPortlet portlet, HttpServletRequest request, Timestamp limitTimestamp )
    {
        if ( !portlet.getDynamic(  ) && portlet.getShowPortlets(  ) )
        {
            for ( IWhatsNew whatsNew : WhatsNewService.getInstance(  )
                                                      .getPortletsByCriterias( limitTimestamp, request.getLocale(  ) ) )
            {
                String strModeratedElement = request.getParameter( WhatsNewConstants.PARAMETER_MODERATED_PORTLET +
                        WhatsNewConstants.UNDERSCORE + whatsNew.getPortletId(  ) );

                if ( StringUtils.isNotBlank( strModeratedElement ) )
                {
                    WhatsNewPortletService.getInstance(  )
                                          .createModeratedPortlet( portlet.getId(  ), whatsNew.getPortletId(  ),
                        getPlugin(  ) );
                }
            }
        }
    }

    /**
     * Set the moderated pages
     * @param portlet the portlet
     * @param request {@link HttpServletRequest}
     * @param limitTimestamp the date limit
     */
    private void setModeratedPages( WhatsNewPortlet portlet, HttpServletRequest request, Timestamp limitTimestamp )
    {
        if ( !portlet.getDynamic(  ) && portlet.getShowPages(  ) )
        {
            for ( IWhatsNew whatsNew : WhatsNewService.getInstance(  )
                                                      .getPagesByCriterias( limitTimestamp, request.getLocale(  ) ) )
            {
                String strModeratedElement = request.getParameter( WhatsNewConstants.PARAMETER_MODERATED_PAGE +
                        WhatsNewConstants.UNDERSCORE + whatsNew.getPageId(  ) );

                if ( StringUtils.isNotBlank( strModeratedElement ) )
                {
                    WhatsNewPortletService.getInstance(  )
                                          .createModeratedPage( portlet.getId(  ), whatsNew.getPageId(  ), getPlugin(  ) );
                }
            }
        }
    }

    /**
     * Set the moderated documents
     * @param portlet the document
     * @param request {@link HttpServletRequest}
     * @param limitTimestamp the date limit
     */
    private void setModeratedDocuments( WhatsNewPortlet portlet, HttpServletRequest request, Timestamp limitTimestamp )
    {
        if ( !portlet.getDynamic(  ) && portlet.getShowDocuments(  ) )
        {
            for ( IWhatsNew whatsNew : WhatsNewService.getInstance(  )
                                                      .getDocumentsByCriterias( limitTimestamp, request.getLocale(  ) ) )
            {
                String strModeratedElement = request.getParameter( WhatsNewConstants.PARAMETER_MODERATED_DOCUMENT +
                        WhatsNewConstants.UNDERSCORE + whatsNew.getPortletId(  ) + WhatsNewConstants.UNDERSCORE +
                        whatsNew.getDocumentId(  ) );

                if ( StringUtils.isNotBlank( strModeratedElement ) )
                {
                    PortletDocumentLink pdLink = new PortletDocumentLink( whatsNew.getPortletId(  ),
                            whatsNew.getDocumentId(  ) );
                    WhatsNewPortletService.getInstance(  )
                                          .createModeratedDocument( portlet.getId(  ), pdLink, getPlugin(  ) );
                }
            }
        }
    }
}
