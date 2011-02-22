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
package fr.paris.lutece.plugins.whatsnew.utils.constants;


/**
 *
 * WhatsNewConstants
 *
 */
public final class WhatsNewConstants
{
    // CONSTANTS
    public static final String EQUAL = "=";
    public static final String AMPERSAND = "&";
    public static final String INTERROGATION_MARK = "?";
    public static final String DELIMITER_SEMI_COLON = ";";
    public static final String DELIMITER_COMA = ",";
    public static final String UNDERSCORE = "_";
    public static final String DOCUMENT_PLUGIN_NAME = "document";
    public static final String ZERO = "0";
    public static final int DISPLAY_ASC = 1;
    public static final int DISPLAY_DESC = 0;

    // MARKS 
    public static final String MARK_COMBO_PERIOD = "combo_period";
    public static final String MARK_DEFAULT_PERIOD = "default_period";
    public static final String MARK_WHATSNEW_PORTLET = "whatsnew_portlet";
    public static final String MARK_WHATSNEW_PORTLET_ID = "whatsnew_portlet_id";
    public static final String MARK_DISPLAY_ORDER_DATE = "display_order_date";
    public static final String MARK_DISPLAY_ORDER_ALPHA = "display_order_alpha";
    public static final String MARK_DISPLAY_ORDER_ASC = "display_order_asc";
    public static final String MARK_DISPLAY_ORDER_DESC = "display_order_desc";
    public static final String MARK_PLUGIN_DOCUMENT_ACTIVATED = "plugin_document_activated";
    public static final String MARK_MODERATED_ELEMENTS_LIST = "moderated_elements_list";
    public static final String MARK_ELEMENT = "element";
    public static final String MARK_ELEMENTS_LIST = "elements_list";
    public static final String MARK_WHATSNEW_TYPE = "whatsnew_type";
    public static final String MARK_LIST_PARAM_DEFAULT_VALUES = "list_param_default_values";

    // PARAMETERS
    public static final String PARAMETER_DOCUMENT_ID = "document_id";
    public static final String PARAMETER_MIN_DISPLAY = "min_display";
    public static final String PARAMETER_SHOW_PAGES = "show_pages";
    public static final String PARAMETER_SHOW_PORTLETS = "show_portlets";
    public static final String PARAMETER_SHOW_DOCUMENTS = "show_documents";
    public static final String PARAMETER_PERIOD = "text_period";
    public static final String PARAMETER_NB_ELEMENTS_MAX = "text_nbElements";
    public static final String PARAMETER_DISPLAY_ORDER = "display_order";
    public static final String PARAMETER_DISPLAY_ORDER_ASC_DESC = "display_order_asc_desc";
    public static final String PARAMETER_IS_DYNAMIC = "is_dynamic";
    public static final String PARAMETER_MODERATED_PAGE = "moderated_page";
    public static final String PARAMETER_MODERATED_PORTLET = "moderated_portlet";
    public static final String PARAMETER_MODERATED_DOCUMENT = "moderated_document";
    public static final String PARAMETER_WHATSNEW_PORTLET_ID = "whatsnew_portlet_id";

    // PROPERTIES
    public static final String PROPERTY_FRAGMENT_DAYS_COMBO_LIST = "portlet.whatsnew.days.combo.list";
    public static final String PROPERTY_TYPE_DOCUMENT = "whatsnew.type.document";
    public static final String PROPERTY_TYPE_PORTLET = "whatsnew.type.portlet";
    public static final String PROPERTY_TYPE_PAGE = "whatsnew.type.page";
    public static final String PROPERTY_DAEMON_MODERATED_ELEMENTS_CLEANER_LANGUAGE = "daemon.whatsNewModeratedElementsCleaner.language";

    // MESSAGES
    public static final String MESSAGE_MANDATORY_PORTLET_NB_ELEMENTS_MAX = "whatsnew.message.portlet.nbelementsmax.mandatory";
    public static final String MESSAGE_NOT_VALID_PORTLET_NB_ELEMENTS_MAX = "whatsnew.message.portlet.nbelementsmax.not.valid";
    public static final String MESSAGE_NEGATIVE_PORTLET_NB_ELEMENTS_MAX = "whatsnew.message.portlet.nbelementsmax.negative";
    public static final String MESSAGE_NOT_NUMERIC = "whatsnew.message.portlet.not.numeric";
    public static final String MESSAGE_OBJECT_NOT_FOUND = "whatsnew.message.object_not_found";

    // TAGS
    public static final String TAG_WHATS_NEW_ELEMENT = "whatsnew-element";
    public static final String TAG_WHATS_NEW_TITLE = "whatsnew-title";
    public static final String TAG_WHATS_NEW_TYPE = "whatsnew-type";
    public static final String TAG_WHATS_NEW_DESCRIPTION = "whatsnew-description";
    public static final String TAG_WHATS_NEW_DATE_UPDATE = "whatsnew-date-update";
    public static final String TAG_WHATS_NEW_URL = "whatsnew-url";
    public static final String TAG_WHATS_NEW_PORTLET = "whatsnew-list-portlet";
    public static final String TAG_WHATS_NEW_MIN_DISPLAY = "whatsnew-min-display";
    public static final String TAG_WHATS_NEW_NUMBER_DISPLAY = "whatsnew-number-display";

    // SPRING
    public static final String BEAN_WHATSNEW_TYPE_PAGE = "whatsnew.whatsNewTypePage";
    public static final String BEAN_WHATSNEW_TYPE_PORTLET = "whatsnew.whatsNewTypePortlet";
    public static final String BEAN_WHATSNEW_TYPE_DOCUMENT = "whatsnew.whatsNewTypeDocument";

    /**
     * public constructor
     */
    private WhatsNewConstants(  )
    {
    }
}
