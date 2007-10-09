/*
 * File   : $Source: /alkacon/cvs/alkacon/com.alkacon.opencms.newsletter/src/com/alkacon/opencms/newsletter/admin/CmsMailinglistsList.java,v $
 * Date   : $Date: 2007/10/09 08:33:57 $
 * Version: $Revision: 1.2 $
 *
 * This library is part of OpenCms -
 * the Open Source Content Mananagement System
 *
 * Copyright (c) 2005 Alkacon Software GmbH (http://www.alkacon.com)
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * For further information about Alkacon Software GmbH, please see the
 * company website: http://www.alkacon.com
 *
 * For further information about OpenCms, please see the
 * project website: http://www.opencms.org
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package com.alkacon.opencms.newsletter.admin;

import org.opencms.jsp.CmsJspActionElement;
import org.opencms.main.CmsException;
import org.opencms.main.OpenCms;
import org.opencms.security.CmsPrincipal;
import org.opencms.workplace.list.CmsListColumnDefinition;
import org.opencms.workplace.list.CmsListDirectAction;
import org.opencms.workplace.list.CmsListMetadata;
import org.opencms.workplace.list.CmsListMultiAction;
import org.opencms.workplace.tools.accounts.A_CmsGroupsList;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

/**
 * Mailing list management view.<p>
 * 
 * @author Michael Moossen  
 * 
 * @version $Revision: 1.2 $ 
 * 
 * @since 6.0.0 
 */
public class CmsMailinglistsList extends A_CmsGroupsList {

    /** list id constant. */
    public static final String LIST_ID = "lgl";

    /** Path to the list buttons. */
    public static final String PATH_BUTTONS = "tools/newsletter/buttons/";

    /**
     * Public constructor.<p>
     * 
     * @param jsp an initialized JSP action element
     */
    public CmsMailinglistsList(CmsJspActionElement jsp) {

        super(jsp, LIST_ID, Messages.get().container(Messages.GUI_MAILINGLISTS_LIST_NAME_0));
    }

    /**
     * Public constructor with JSP variables.<p>
     * 
     * @param context the JSP page context
     * @param req the JSP request
     * @param res the JSP response
     */
    public CmsMailinglistsList(PageContext context, HttpServletRequest req, HttpServletResponse res) {

        this(new CmsJspActionElement(context, req, res));
    }

    /**
     * @see org.opencms.workplace.tools.accounts.A_CmsGroupsList#getGroups()
     */
    protected List getGroups() throws CmsException {

        return CmsPrincipal.filterCore(OpenCms.getOrgUnitManager().getGroups(getCms(), getParamOufqn(), false));
    }

    /**
     * @see org.opencms.workplace.tools.accounts.A_CmsGroupsList#setColumns(org.opencms.workplace.list.CmsListMetadata)
     */
    protected void setColumns(CmsListMetadata metadata) {

        super.setColumns(metadata);

        CmsListColumnDefinition editCol = metadata.getColumnDefinition(LIST_COLUMN_EDIT);
        editCol.setName(Messages.get().container(Messages.GUI_MAILINGLISTS_LIST_COLS_EDIT_0));
        editCol.setHelpText(Messages.get().container(Messages.GUI_MAILINGLISTS_LIST_COLS_EDIT_HELP_0));

        CmsListColumnDefinition usersCol = metadata.getColumnDefinition(LIST_COLUMN_USERS);
        usersCol.setName(Messages.get().container(Messages.GUI_MAILINGLISTS_LIST_COLS_SUBSCRIBERS_0));
        usersCol.setHelpText(Messages.get().container(Messages.GUI_MAILINGLISTS_LIST_COLS_SUBSCRIBERS_HELP_0));
        CmsListDirectAction usersAction = (CmsListDirectAction)usersCol.getDirectAction(LIST_ACTION_USERS);
        usersAction.setName(Messages.get().container(Messages.GUI_MAILINGLISTS_LIST_ACTION_SUBSCRIBERS_NAME_0));
        usersAction.setHelpText(Messages.get().container(Messages.GUI_MAILINGLISTS_LIST_ACTION_SUBSCRIBERS_HELP_0));
        usersAction.setIconPath(PATH_BUTTONS + "subscriber.png");

        metadata.getColumnDefinition(LIST_COLUMN_ACTIVATE).setVisible(false);

        CmsListColumnDefinition deleteCol = metadata.getColumnDefinition(LIST_COLUMN_DELETE);
        deleteCol.setName(Messages.get().container(Messages.GUI_MAILINGLISTS_LIST_COLS_DELETE_0));
        deleteCol.setHelpText(Messages.get().container(Messages.GUI_MAILINGLISTS_LIST_COLS_DELETE_HELP_0));
    }

    /**
     * @see org.opencms.workplace.tools.accounts.A_CmsGroupsList#setDeleteAction(org.opencms.workplace.list.CmsListColumnDefinition)
     */
    protected void setDeleteAction(CmsListColumnDefinition deleteCol) {

        CmsListDirectAction deleteAction = new CmsListDirectAction(LIST_ACTION_DELETE);
        deleteAction.setName(Messages.get().container(Messages.GUI_MAILINGLISTS_LIST_ACTION_DELETE_NAME_0));
        deleteAction.setHelpText(Messages.get().container(Messages.GUI_MAILINGLISTS_LIST_ACTION_DELETE_HELP_0));
        deleteAction.setIconPath(ICON_DELETE);
        deleteCol.addDirectAction(deleteAction);
    }

    /**
     * @see org.opencms.workplace.tools.accounts.A_CmsGroupsList#setEditAction(org.opencms.workplace.list.CmsListColumnDefinition)
     */
    protected void setEditAction(CmsListColumnDefinition editCol) {

        CmsListDirectAction editAction = new CmsListDirectAction(LIST_ACTION_EDIT);
        editAction.setName(Messages.get().container(Messages.GUI_MAILINGLISTS_LIST_ACTION_EDIT_NAME_0));
        editAction.setHelpText(Messages.get().container(Messages.GUI_MAILINGLISTS_LIST_ACTION_EDIT_HELP_0));
        editAction.setIconPath(PATH_BUTTONS + "mailinglist.png");
        editCol.addDirectAction(editAction);
    }

    /**
     * @see org.opencms.workplace.tools.accounts.A_CmsGroupsList#setMultiActions(org.opencms.workplace.list.CmsListMetadata)
     */
    protected void setMultiActions(CmsListMetadata metadata) {

        super.setMultiActions(metadata);
        CmsListMultiAction activateUser = metadata.getMultiAction(LIST_MACTION_ACTIVATE);
        activateUser.setVisible(false);
        CmsListMultiAction deactivateUser = metadata.getMultiAction(LIST_MACTION_DEACTIVATE);
        deactivateUser.setVisible(false);
        
    }
}
