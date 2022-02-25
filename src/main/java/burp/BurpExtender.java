/*
 * BurpExtender.java
 * @author: aw220
 * @date: 2022/2/18 下午11:19
 *
 *
 */

package burp;

import burp.ui.MainUI;

import javax.swing.*;
import java.io.PrintWriter;

import static burp.Utils.*;

public class BurpExtender implements IBurpExtender {

    @Override
    public void registerExtenderCallbacks(IBurpExtenderCallbacks callbacks) {
//        new Utils();
        cbs = callbacks;
        stdout = new PrintWriter(cbs.getStdout(), true);
        stderr = new PrintWriter(cbs.getStderr(), true);
        MainUI main = new MainUI();
        cbs.customizeUiComponent(main);
        cbs.addSuiteTab(main);
        callbacks.setExtensionName(Utils.EXTENSION_NAME);
    }

}