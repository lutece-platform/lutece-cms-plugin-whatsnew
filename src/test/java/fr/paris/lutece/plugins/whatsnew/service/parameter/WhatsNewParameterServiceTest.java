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
package fr.paris.lutece.plugins.whatsnew.service.parameter;

import fr.paris.lutece.plugins.whatsnew.service.WhatsNewPlugin;
import fr.paris.lutece.plugins.whatsnew.utils.constants.WhatsNewConstants;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.test.LuteceTestCase;
import fr.paris.lutece.util.ReferenceItem;
import jakarta.inject.Inject;

import org.junit.jupiter.api.Test;

/**
 * Tests the default values of the What's New portlet creation form
 */
public class WhatsNewParameterServiceTest extends LuteceTestCase
{
    private static final String NEW_VALUE = "7";

    @Inject
    private WhatsNewParameterService _parameterService;

    /**
     * Updates the default maximum number of elements, reads it back and restores it
     */
    @Test
    public void testUpdateDefaultValue( )
    {
        Plugin plugin = PluginService.getPlugin( WhatsNewPlugin.PLUGIN_NAME );
        ReferenceItem param = findParam( plugin );
        assertNotNull( param );
        String strInitialValue = param.getName( );

        param.setName( NEW_VALUE );
        _parameterService.update( param, plugin );
        assertEquals( NEW_VALUE, findParam( plugin ).getName( ) );

        param.setName( strInitialValue );
        _parameterService.update( param, plugin );
        assertEquals( strInitialValue, findParam( plugin ).getName( ) );
    }

    /**
     * Finds the default maximum number of elements
     * @param plugin the plugin
     * @return the parameter, or null when it is missing
     */
    private ReferenceItem findParam( Plugin plugin )
    {
        return _parameterService.getParamDefaultValues( plugin ).stream( )
                .filter( item -> WhatsNewConstants.PARAMETER_NB_ELEMENTS_MAX.equals( item.getCode( ) ) ).findFirst( ).orElse( null );
    }
}
