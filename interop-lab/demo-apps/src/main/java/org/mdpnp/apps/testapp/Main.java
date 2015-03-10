/*******************************************************************************
 * Copyright (c) 2014, MD PnP Program
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/
package org.mdpnp.apps.testapp;

import java.awt.Image;
import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;

import javax.imageio.ImageIO;

import javafx.application.Platform;
import javafx.stage.Stage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Jeff Plourde
 * 
 */
public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(final String[] args) throws Exception {
        System.err.println("This should be the entry point");
        URL u = Main.class.getResource("/ice.system.properties");
        if(u != null) {
            log.info("Loading System configuration from " + u.toExternalForm());
            InputStream is = u.openStream();
            System.getProperties().load(is);
            is.close();
        }
        
        Configuration runConf;
        if(args.length > 0) {
            System.err.println("Arguments specified, no UI");
            runConf = Configuration.read(args);

            if(null == runConf) {
                return;
            } else {
                Configuration.searchAndSaveSettings(runConf, searchPath);
            }
            Configuration.Command cmd = runConf.getCommand();
            int retCode = cmd.execute(runConf);
            log.info("This is the end, exit code=" + retCode);
            System.exit(retCode);
            
        } else {
            System.err.println("Arguments NOT specified, UI is coming");
            javafx.application.Application.launch(MainApplication.class, args);
        }
    }

    private final static File[] searchPath = new File [] {
        new File(".JumpStartSettings"),
        new File(System.getProperty("user.home"), ".JumpStartSettings")
    };

}
