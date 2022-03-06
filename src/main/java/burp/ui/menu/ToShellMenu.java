/*
 * ToShellMenu.java
 * @author: aw220
 * @date: 2022/3/4 下午8:25
 *
 *
 */

package burp.ui.menu;

import burp.IBurpExtenderCallbacks;
import burp.IContextMenuFactory;
import burp.IContextMenuInvocation;
import burp.IHttpRequestResponse;
import burp.action.TerminalExec;
import burp.ui.entry.CustomLineEntry;
import net.dongliu.commons.Sys;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static burp.Utils.*;

public class ToShellMenu implements IContextMenuFactory {


    @Override
    public List<JMenuItem> createMenuItems(IContextMenuInvocation iContextMenuInvocation) {
        if (iContextMenuInvocation.getToolFlag() == IBurpExtenderCallbacks.TOOL_REPEATER || iContextMenuInvocation.getToolFlag() == IBurpExtenderCallbacks.TOOL_PROXY || iContextMenuInvocation.getToolFlag() == IBurpExtenderCallbacks.TOOL_EXTENDER) {
            List<JMenuItem> menu = new ArrayList<>();
            for (CustomLineEntry c : loadConn.getCustom())
                if (!c.getName().equals("") && !c.getTemplate().equals("")) {
                    menu.add(new JMenuItem(new AbstractAction(c.getName()) {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String sh = c.getTemplate().replace("{tar}", getSelectedText(iContextMenuInvocation));
                            String batFile = TerminalExec.genBatchFile(sh, "SendToXXX.bat");
                            TerminalExec.runBatchFile(batFile);
                        }
                    }));
                }
            return menu;
        }
        return null;
    }

    public static void main(String[] args) throws IOException, InterruptedException {

    }
}
