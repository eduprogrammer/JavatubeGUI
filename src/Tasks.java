/*
 *   Copyright 2022. Eduardo Programador
 *   www.eduardoprogramador.com
 *   consultoria@eduardoprogramador.com
 *
 *   All Rights Reserved
 * */

import com.eduardoprogramador.JavaTube;
import com.eduardoprogramador.TubeException;
import com.eduardoprogramador.http.DownloadHandler;
import com.eduardoprogramador.http.Http;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;

public class Tasks implements DownloadHandler {
    private CastOperations castOperations;
    private String link;
    private String job;

    public void processRequest() {

        JavaTube javaTube = JavaTube.init();
        try {
            JavaTube.setOnDownloadListener(new DownloadHandler() {
                @Override
                public void onDownload(String s) {
                    if(castOperations != null) {
                        Http.openInBrowser(s);
                        MakeParams.make(link);
                        castOperations.onDone();
                    }
                }
            });
            int operation = (job.equalsIgnoreCase("mp4")) ? JavaTube.FORMAT_VIDEO : JavaTube.FORMAT_AUDIO;
            javaTube.startDownload(link,operation);
        } catch (TubeException ex) {
            if(castOperations != null)
                castOperations.onFailed();
        }

    }

    public String pasteDate() {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable transferable = clipboard.getContents(clipboard);
        if(transferable != null) {
            try {
                String pasted = (String) transferable.getTransferData(DataFlavor.stringFlavor);
                return pasted;
            } catch (Exception ex) {
                return null;
            }
        } else {
            return null;
        }
    }

    public void setDefaults(String link, String job) {
        this.link = link;
        this.job = job;
    }

    public void setOnCastListener(CastOperations castOperations) {
        this.castOperations = castOperations;
    }

    @Override
    public void onDownload(String s) {
        Http.openInBrowser(s);
    }
}
