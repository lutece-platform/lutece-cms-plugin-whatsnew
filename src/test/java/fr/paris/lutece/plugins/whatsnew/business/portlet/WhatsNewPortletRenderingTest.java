/*
 * Copyright (c) 2002-2026, City of Paris
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
package fr.paris.lutece.plugins.whatsnew.business.portlet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.security.SecureRandom;
import java.util.List;

import jakarta.inject.Inject;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.paris.lutece.plugins.whatsnew.service.WhatsNewPlugin;
import fr.paris.lutece.plugins.whatsnew.service.portlet.WhatsNewPortletService;
import fr.paris.lutece.portal.business.page.Page;
import fr.paris.lutece.portal.business.portlet.Portlet;
import fr.paris.lutece.portal.business.portlet.PortletHome;
import fr.paris.lutece.portal.business.portlet.PortletTemplate;
import fr.paris.lutece.portal.business.portlet.PortletTemplateHome;
import fr.paris.lutece.portal.service.page.IPageService;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.portal.PortalService;
import fr.paris.lutece.portal.web.LocalVariables;
import fr.paris.lutece.test.LuteceTestCase;
import fr.paris.lutece.test.mocks.MockHttpServletRequest;
import fr.paris.lutece.test.mocks.MockHttpServletResponse;

/**
 * Renders a whatsnew portlet with every shipped FreeMarker template
 */
public class WhatsNewPortletRenderingTest extends LuteceTestCase
{
    private static final String PORTLET_NAME = "WhatsNewPortletRenderingTest title";
    private static final int TEMPLATE_ONE_COLUMN = 2;
    private static final String MARKER_PORTLET = "portlet-whatsnew";
    private static final int UNKNOWN_TEMPLATE_ID = 99999;
    private static final int NB_SHIPPED_TEMPLATES = 1;

    private int _nPageId;
    private String _strPageName;
    private WhatsNewPortlet _portlet;

    @Inject
    private IPageService _pageService;

    @Inject
    private WhatsNewPortletService _portletService;

    @BeforeEach
    @Override
    protected void setUp( ) throws Exception
    {
        super.setUp( );
        _strPageName = "page" + new SecureRandom( ).nextLong( );
        Page page = new Page( );
        page.setParentPageId( PortalService.getRootPageId( ) );
        page.setPageTemplateId( TEMPLATE_ONE_COLUMN );
        page.setName( _strPageName );
        page.setDescription( _strPageName + "_desc" );
        _pageService.createPage( page );
        _nPageId = page.getId( );

        // Moderated portlet showing only the page created above
        _portlet = new WhatsNewPortlet( );
        _portlet.setShowPages( true );
        _portlet.setDynamic( false );
        _portlet.setPeriod( 30 );
        _portlet.setNbElementsMax( 10 );
        _portlet.setPageId( PortalService.getRootPageId( ) );
        _portlet.setStyleId( 0 );
        _portlet.setColumn( 1 );
        _portlet.setOrder( 1 );
        _portlet.setName( PORTLET_NAME );
        _portlet.setStatus( Portlet.STATUS_PUBLISHED );
        _portlet.setDisplayPortletTitle( 0 );
        _portlet.setDeviceDisplayFlags( Portlet.FLAG_DISPLAY_ON_NORMAL_DEVICE | Portlet.FLAG_DISPLAY_ON_LARGE_DEVICE | Portlet.FLAG_DISPLAY_ON_XLARGE_DEVICE );
        _portletService.create( _portlet );
        _portletService.createModeratedPage( _portlet.getId( ), _nPageId, PluginService.getPlugin( WhatsNewPlugin.PLUGIN_NAME ) );
    }

    @AfterEach
    @Override
    protected void tearDown( ) throws Exception
    {
        if ( _portlet != null )
        {
            _portletService.remove( _portlet );
        }
        if ( _nPageId != 0 )
        {
            _pageService.removePage( _nPageId );
        }
        LocalVariables.remove( );
        super.tearDown( );
    }

    /**
     * The shipped templates are registered in the core for the portlet type
     */
    @Test
    public void testShippedTemplatesRegistered( )
    {
        List<PortletTemplate> listTemplates = PortletTemplateHome.findByPortletType( _portletService.getPortletTypeId( ) );
        assertEquals( NB_SHIPPED_TEMPLATES, listTemplates.size( ), "the shipped templates should be registered in the core for the whatsnew portlet type" );
    }

    /**
     * The template chosen for the portlet is stored by the core
     */
    @Test
    public void testTemplateStoredWithThePortlet( )
    {
        PortletTemplate template = PortletTemplateHome.findByPortletType( _portletService.getPortletTypeId( ) ).get( 0 );
        _portlet.setIdTemplate( template.getId( ) );
        _portlet.update( );

        assertEquals( template.getId( ), PortletHome.findByPrimaryKey( _portlet.getId( ) ).getIdTemplate( ) );
        assertTrue( PortletTemplateHome.isTemplateUsed( template.getId( ) ) );
    }

    /**
     * Every shipped template renders the portlet title, the moderated page and the device display classes
     */
    @Test
    public void testRenderEveryShippedTemplate( )
    {
        MockHttpServletRequest request = new MockHttpServletRequest( );
        LocalVariables.setLocal( null, request, new MockHttpServletResponse( ) );

        for ( PortletTemplate template : PortletTemplateHome.findByPortletType( _portletService.getPortletTypeId( ) ) )
        {
            _portlet.setIdTemplate( template.getId( ) );
            String strContent = _portlet.getHtmlContent( request );

            assertTrue( strContent.contains( MARKER_PORTLET ), "template " + template.getTemplatePath( ) + " should render the portlet wrapper" );
            assertTrue( strContent.contains( "portlet_" + _portlet.getId( ) ), "template " + template.getTemplatePath( ) + " should render the portlet anchor" );
            assertTrue( strContent.contains( PORTLET_NAME ), "template " + template.getTemplatePath( ) + " should render the portlet title" );
            assertTrue( strContent.contains( _strPageName ), "template " + template.getTemplatePath( ) + " should render the page name" );
            assertTrue( strContent.contains( _strPageName + "_desc" ), "template " + template.getTemplatePath( ) + " should render the page description" );
            assertTrue( strContent.contains( "page_id=" + _nPageId ), "template " + template.getTemplatePath( ) + " should link to the page" );
            assertTrue( strContent.contains( "[1 / 1]" ), "template " + template.getTemplatePath( ) + " should render the pagination counter" );
            assertTrue( strContent.contains( "d-none d-md-block" ), "template " + template.getTemplatePath( ) + " should hide the portlet on small devices" );
        }
    }

    /**
     * An unknown template falls back to the default one and a hidden title is not rendered
     */
    @Test
    public void testFallbackToDefaultTemplate( )
    {
        MockHttpServletRequest request = new MockHttpServletRequest( );
        LocalVariables.setLocal( null, request, new MockHttpServletResponse( ) );

        _portlet.setIdTemplate( UNKNOWN_TEMPLATE_ID );
        _portlet.setDisplayPortletTitle( 1 );
        String strContent = _portlet.getHtmlContent( request );

        assertTrue( strContent.contains( MARKER_PORTLET ), "the default template should render the portlet wrapper" );
        assertTrue( strContent.contains( _strPageName ), "the default template should render the page name" );
        assertFalse( strContent.contains( PORTLET_NAME ), "a hidden portlet title should not be rendered" );
    }
}
