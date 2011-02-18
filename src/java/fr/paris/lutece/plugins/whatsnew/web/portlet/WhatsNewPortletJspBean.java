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
package fr.paris.lutece.plugins.whatsnew.web.portlet;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import fr.paris.lutece.plugins.whatsnew.business.ElementOrderEnum;
import fr.paris.lutece.plugins.whatsnew.business.portlet.WhatsNewPortlet;
import fr.paris.lutece.plugins.whatsnew.service.WhatsNewService;
import fr.paris.lutece.plugins.whatsnew.service.portlet.WhatsNewPortletService;
import fr.paris.lutece.plugins.whatsnew.utils.constants.WhatsNewConstants;
import fr.paris.lutece.portal.business.portlet.PortletHome;
import fr.paris.lutece.portal.service.message.AdminMessage;
import fr.paris.lutece.portal.service.message.AdminMessageService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.portal.web.portlet.PortletJspBean;
import fr.paris.lutece.util.ReferenceList;
import fr.paris.lutece.util.html.HtmlTemplate;


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
     * Returns portlet "what's new" 's creation form
     * @param request request
     * @return Html form
     */
    public String getCreate( HttpServletRequest request )
    {
        String strPageId = request.getParameter( PARAMETER_PAGE_ID );
        String strPortletTypeId = request.getParameter( PARAMETER_PORTLET_TYPE_ID );
        int nPeriodByDefault = AppPropertiesService.getPropertyInt( WhatsNewConstants.PROPERTY_FRAGMENT_DAYS_COMBO_DEFAULT_VALUE,
                7 );

        Map<String, Object> model = new HashMap<String, Object>(  );
        model.put( WhatsNewConstants.MARK_COMBO_PERIOD, getComboDays(  ) );
        model.put( WhatsNewConstants.MARK_DEFAULT_PERIOD, nPeriodByDefault );
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
                model.put( WhatsNewConstants.MARK_COMBO_PERIOD, getComboDays(  ) );
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
     * Initialize the number of days' combo
     * @return the html code of the combo
     */
    private ReferenceList getComboDays(  )
    {
        // Returns the list stored in the property file and the default value
        String strListe = AppPropertiesService.getProperty( WhatsNewConstants.PROPERTY_FRAGMENT_DAYS_COMBO_LIST );

        ReferenceList comboDaysList = new ReferenceList(  );

        StringTokenizer strTokSemiColon = new StringTokenizer( strListe, WhatsNewConstants.DELIMITER_SEMI_COLON );

        while ( strTokSemiColon.hasMoreTokens(  ) )
        {
            StringTokenizer strTokComa = new StringTokenizer( strTokSemiColon.nextToken(  ),
                    WhatsNewConstants.DELIMITER_COMA );

            while ( strTokComa.hasMoreTokens(  ) )
            {
                comboDaysList.addItem( Integer.parseInt( strTokComa.nextToken(  ) ), strTokComa.nextToken(  ) );
            }
        }

        return comboDaysList;
    }

    /**
     * Initialize the model
     * @param model the model
     */
    private void initializeModel( Map<String, Object> model )
    {
    	model.put( WhatsNewConstants.MARK_PLUGIN_DOCUMENT_ACTIVATED, WhatsNewService.getInstance(  ).isPluginDocumentActivated(  ) );
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
                    		Integer.parseInt( strIsAscSort ) == WhatsNewConstants.DISPLAY_ASC )
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

                        portlet.setShowDocuments( strShowDocuments != null );
                        portlet.setShowPortlets( strShowPortlets != null );
                        portlet.setShowPages( strShowPages != null );
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
}
