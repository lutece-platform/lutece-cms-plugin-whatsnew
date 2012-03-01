/*
 * Copyright (c) 2002-2012, Mairie de Paris
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
package fr.paris.lutece.plugins.whatsnew.business;

import fr.paris.lutece.plugins.whatsnew.utils.constants.WhatsNewConstants;
import fr.paris.lutece.portal.web.constants.Parameters;

import java.util.Locale;


/**
 *
 * WhatsNewTypePortlet
 *
 */
public class WhatsNewTypePortlet extends WhatsNew
{
    private static final String TEMPLATE_MODERATED_ELEMENTS_LIST = "/admin/plugins/whatsnew/portlet/moderated_portlet.html";
    private String _strRefPageName;

    /**
     * Constructor
     */
    public WhatsNewTypePortlet(  )
    {
    }

    /**
     * {@inheritDoc}
     */
    public String getTemplateModeratedElement(  )
    {
        return TEMPLATE_MODERATED_ELEMENTS_LIST;
    }

    /**
     * Set the reference page name
     * @param strRefPageName the reference page name
     */
    public void setRefPageName( String strRefPageName )
    {
        _strRefPageName = strRefPageName;
    }

    /**
     * Get the reference page name
     * @return the reference page name
     */
    public String getRefPageName(  )
    {
        return _strRefPageName;
    }

    /**
     * {@inheritDoc}
     */
    public void setWhatsNewType( Locale locale )
    {
        WhatsNewType whatsNewType = new WhatsNewType(  );
        whatsNewType.setLocale( locale );
        whatsNewType.setClassName( this.getClass(  ).getName(  ) );
        whatsNewType.setLabelType( WhatsNewConstants.PROPERTY_TYPE_PORTLET );
        setWhatsNewType( whatsNewType );
    }

    /**
     * {@inheritDoc}
     */
    public String buildUrl(  )
    {
        StringBuilder sbUrl = new StringBuilder(  );
        sbUrl.append( WhatsNewConstants.INTERROGATION_MARK );
        sbUrl.append( Parameters.PAGE_ID );
        sbUrl.append( WhatsNewConstants.EQUAL );
        sbUrl.append( getPageId(  ) );
        sbUrl.append( WhatsNewConstants.AMPERSAND );
        sbUrl.append( Parameters.PORTLET_ID );
        sbUrl.append( WhatsNewConstants.EQUAL );
        sbUrl.append( getPortletId(  ) );

        return sbUrl.toString(  );
    }
}
