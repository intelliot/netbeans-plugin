package org.netbeans.modules.php.nette.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.StyledDocument;
import org.netbeans.api.lexer.InputAttributes;
import org.netbeans.api.lexer.Token;
import org.netbeans.api.lexer.TokenHierarchy;
import org.netbeans.api.lexer.TokenSequence;
import org.netbeans.modules.parsing.api.Snapshot;
import org.netbeans.modules.parsing.api.Source;
import org.netbeans.modules.php.api.phpmodule.PhpModule;
import org.netbeans.modules.php.nette.editor.ControlCompletionItem;
import org.netbeans.modules.php.nette.editor.LatteParseData;
import org.netbeans.modules.php.nette.editor.PresenterCompletionItem;
import org.netbeans.modules.php.nette.editor.VariableCompletionItem;
import org.netbeans.modules.php.nette.lexer.LatteTokenId;
import org.netbeans.modules.php.nette.lexer.LatteTopTokenId;
import org.netbeans.spi.editor.completion.CompletionItem;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;

/**
 *
 * @author cawe
 */
public class EditorUtils {

    /**
     * Get index of white character in the line.
     * @param line which line should be scanned
     * @return index of first white characted
     */
    public static int indexOfWhite(char[] line) {
        int i = line.length;
        while (--i > -1) {
            final char c = line[i];
            if (Character.isWhitespace(c)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Get first non whitespace in file from some offset.
     * @param doc which document should be scanned
     * @param offset where the scanning starts
     * @return index of first non-white character
     * @throws BadLocationException entered offset is outside of the document
     */
    public static int getRowFirstNonWhite(StyledDocument doc, int offset) throws BadLocationException {
        TokenHierarchy<?> th = TokenHierarchy.get(doc);
        TokenSequence<LatteTopTokenId> ts = th.tokenSequence(LatteTopTokenId.language());
        int diffStart = ts.move(offset);
        Token t = null;
        int lenght = 0;
        if (ts.moveNext() || ts.movePrevious()) {
            t = ts.token();
            lenght = t.length();
        }
        int start = offset - diffStart;
        while (start + 1 < start + lenght) {
            try {
                if (doc.getText(start, 1).charAt(0) != ' ') {
                    break;
                }
            } catch (BadLocationException ex) {
                throw (BadLocationException) new BadLocationException(
                        "calling getText(" + start + ", " + (start + 1)
                        + ") on doc of length: " + doc.getLength(), start).initCause(ex);
            }
            start++;
        }
        return start;
    }

    public static List<CompletionItem> parseLink(Document doc, String link, int startOffset, int length) {
        FileObject fo = Source.create(doc).getFileObject();

        List<CompletionItem> list = new ArrayList<CompletionItem>();
        
        if (link.contains(":")) {
            String[] parts = link.split(":");
            for(String s : getAllPresenters(fo)) {
                String[] pPath = s.split(":");
                boolean ok = false;
                if(pPath.length >= parts.length) {
                    for(int i = 0; i < parts.length; i++) {
                        if(( i != parts.length - 1 && pPath[i].equals(parts[i]) )
                                || pPath[i].startsWith(parts[i])) {
                            ok = true;
                        }
                        else {
                            ok = false;
                            break;
                        }
                    }
                }
                if(ok || (parts.length == 0 && s.startsWith(":")) ) {
                    list.add(new PresenterCompletionItem(s, startOffset, startOffset + length));
                }
            }
        } else {
            for(String s : getAllPresenters(fo)) {
                if(s.startsWith(link)) {
                    list.add(new PresenterCompletionItem(s, startOffset, startOffset + length));
                }
            }
        }
        return list;
    }

    public static List<CompletionItem> parseControl(Document doc, String written, int startOffset, int length) {
        List<CompletionItem> list = new ArrayList<CompletionItem>();
        
        try {
            FileObject fo = Source.create(doc).getFileObject();

            String presenter = getPresenter(fo);

            Pattern pattern = Pattern.compile("createComponent([A-Za-z_][A-Za-z_]*)\\(");

            presenter += "Presenter.php";
            byte ps = 0;
            while (true) {
                fo = fo.getParent();
                for (FileObject f : fo.getChildren()) {
                    if (f.isFolder() && f.getName().equals("presenters")) {
                        File p = new File(f.getPath(), presenter);
                        if (p.exists()) {
                            try {
                                BufferedReader bis = new BufferedReader(new FileReader(p));
                                String line;
                                while ((line = bis.readLine()) != null) {
                                    if (line.contains("createComponent")) {
                                        Matcher m = pattern.matcher(line);
                                        String control = null;
                                        if (m.find()) {
                                            control = m.group(1);
                                            control = control.substring(0, 1).toLowerCase()
                                                    + control.substring(1);
                                        }
                                        if(control != null)
                                            if(control.startsWith(written)) {
                                                list.add(new ControlCompletionItem(control, startOffset, startOffset + length));
                                            }
                                    }
                                }
                            } catch (IOException ioe) {
                                //Logger.getLogger("TmplCompletionQuery").warning("scanning of unnexisting file " + p.getAbsolutePath());
                            }
                        }
                        break;
                    }
                }
                // just 5 levels up
                if (ps > 5) {
                    break;
                }
                ps++;
            }
        } catch (Exception e) {
            // intentionaly
        }

        return list;
    }

    public static List<CompletionItem> parseLayout(Document doc, String written, int startOffset, int length) {
        List<CompletionItem> list = new ArrayList<CompletionItem>();

        FileObject fo = Source.create(doc).getFileObject();
        List<String> layouts = getLayouts(fo);

        for(String path : layouts) {
            if(path.startsWith(written)) {
                list.add(new PresenterCompletionItem(path, startOffset, startOffset + length));
            }
        }

        return list;
    }

    public static List<CompletionItem> parseVariable(Document doc, String var, int caretOffset, 
            List<String> dynamicVars)
    {
        List<CompletionItem> list = new ArrayList<CompletionItem>();
        
        for(String s : dynamicVars) {
            if(s.startsWith(var)) {
                list.add(new VariableCompletionItem(s, caretOffset-var.length(), caretOffset));
            }
        }

        List<String> vars = getKeywordsForView(doc);
        for(String s : vars) {
            if(s.startsWith(var) && !dynamicVars.contains(s)) {
                list.add(new VariableCompletionItem(s, caretOffset-var.length(), caretOffset));
            }
        }
        return list;
    }

    public static List<CompletionItem> parseDynamic(List<String> vars, String var, int caretOffset) {
        List<CompletionItem> list = new ArrayList<CompletionItem>();
        
        for(String s : vars) {
            if(s.startsWith(var)) {
                list.add(new VariableCompletionItem(s, caretOffset-var.length(), caretOffset));
            }
        }
        return list;
    }

    /**
     * Get context depending variables for Latte template.
     * @param doc for whcih template
     * @return list with variables which were sent from presenter
     */
    public static ArrayList<String> getKeywordsForView(Document doc) {
        ArrayList<String> results = new ArrayList<String>();
        try {
            Source file = Source.create(doc);
            FileObject fo = file.getFileObject();

            String presenter = getPresenter(fo);

            presenter += "Presenter.php";
            byte level = 0;
            while (true) {
                level++;
                fo = fo.getParent();
                if(fo.getName().equals("sessions")
                        || fo.getName().equals("temp") || fo.getName().equals("logs"))
                    continue;
                for (FileObject f : fo.getChildren()) {
                    if (f.isFolder() && f.getName().equals("presenters")) {
                        File p = new File(f.getPath(), presenter);
                        if (p.exists()) {
                            try {
                                BufferedReader bis = new BufferedReader(new FileReader(p));
                                String line;
                                while ((line = bis.readLine()) != null) {
                                    String res = parseLineForVars(line);
                                    if (res != null && !results.contains(res)) {
                                        results.add(res);
                                    }
                                }
                            } catch (IOException ioe) {
                                //Logger.getLogger("TmplCompletionQuery").warning("scanning of unnexisting file " + p.getAbsolutePath());
                            }
                        }
                        break;
                    }
                }
                if(fo.getName().equals("app")) {
                    break;
                }
                if(level > 6) {   // just 5 levels up
                    break;
                }
            }
        } catch (Exception e) {
            // intentionaly
        }
        results.addAll(getGeneralVariables());
        return results;
    }

    public static String getPresenter(FileObject fo) {
        String[] fn = fo.getName().split("\\.");

        if (fn.length == 2) {
            return fn[0];                         //Presenter.view.phtml
        } else {
            return fo.getParent().getName();        //Presenter/view.phtml
        }
    }

    public static List<String> getAllPresenters(FileObject fo) {
        File appDir = new File(PhpModule.forFileObject(fo).getSourceDirectory().getPath() + "/app");

        Enumeration<? extends FileObject> files = FileUtil.toFileObject(appDir).getChildren(true);

        List<String> list = new ArrayList<String>();
        while (files.hasMoreElements()) {
            FileObject pfo = files.nextElement();
            File file = FileUtil.toFile(pfo);
            if (pfo.getNameExt().endsWith("Presenter.php") && file.exists()) {
                try {
                    BufferedReader bis = new BufferedReader(new FileReader(file));
                    String line;
                    while ((line = bis.readLine()) != null) {
                        if(line.contains("class ") && !line.contains("abstract")) {
                            Pattern p = Pattern.compile("class +([A-Za-z_][A-Za-z0-9_]*)Presenter");
                            Matcher m = p.matcher(line);
                            String presenterPath = "";
                            if(m.find()) {
                                presenterPath = m.group(1);     //presenter name
                            }
                            if(presenterPath.contains("_")) {
                                //creates path with modules
                                presenterPath = ":"+presenterPath.replaceAll("_", ":");
                            }
                            presenterPath += ":";
                            list.add(presenterPath);
                            break;
                        }
                    }
                } catch (IOException ioe) {
                    
                }
            }
        }
        return list;
    }


    private static Pattern varPattern = Pattern.compile("template->([A-Za-z_][A-Za-z0-9_]*)");

    /**
     * Get information if the line contains parameter sent into template.
     * @param line scanned line
     * @return whole line if there is sent variable or nothing
     */
    public static String parseLineForVars(String line) {
        try {
            if (line.contains("template")) {
                Matcher m = varPattern.matcher(line);
                String var = null;
                if (m.find()) {
                    var = "$" + m.group(1);
                }
                return var;
            }
        } catch (IllegalStateException e) {
            //Exceptions.printStackTrace(e);
        }
        return null;
    }

    private static ArrayList<String> getGeneralVariables() {
        ArrayList<String> generalVars = new ArrayList<String>();
        generalVars.add("$baseUri");
        generalVars.add("$basePath");
        generalVars.add("$control");
        generalVars.add("$presenter");
        return generalVars;
    }

    public static List<String> getLayouts(FileObject fo) {
        ArrayList<String> layouts = new ArrayList<String>();

        FileObject fp = fo;
        while(true) {
            fp = fp.getParent();
            if(fp.isFolder() && fp.getName().equals("app"))
                break;
        }
        List<FileObject> fos = getFilesRecursive(fp, new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.startsWith("@") && name.endsWith(".phtml");
            }
        });
        for(FileObject f : fos) {
            String rel = getRelativePath(fo, f);
            if(rel != null)
                layouts.add(rel);
        }

        return layouts;
    }

    public static String getRelativePath(FileObject from, FileObject to) {
        ArrayList<String> fPath = new ArrayList<String>(Arrays.asList(from.getPath().split("/")));
        ArrayList<String> tPath = new ArrayList<String>(Arrays.asList(to.getPath().split("/")));

        String relPath = "";
        try {
            while(true) {
                if(fPath.get(0).equals(tPath.get(0))) {
                    fPath.remove(0);
                    tPath.remove(0);
                }
                else
                    break;
            }
            if(fPath.size() > 1) {
                for(int i = 0; i < fPath.size()-1; i++) {
                    relPath += "../";
                }
            }
            for(int i = 0; i < tPath.size(); i++) {
                relPath += tPath.get(i);
                if(i != tPath.size()-1)
                    relPath += "/";
            }
        } catch (Exception e) {
            // intentionally
            // when relativizing two same files -> returning null
        }
        return relPath.equals("") ? null : relPath;
    }

    private static List<FileObject> getFilesRecursive(FileObject fp, FilenameFilter filter) {
        List<FileObject> list = new ArrayList<FileObject>();
        
        for(FileObject child : fp.getChildren()) {
            if(child.getName().equals("temp") || child.getName().equals("sessions") ||
                    child.getName().equals("logs"))
                continue;
            File f = FileUtil.toFile(child);
            if(f.isDirectory()) {
                File[] folders = f.listFiles(new FileFilter() {
                    public boolean accept(File file) {
                        return file.isDirectory();
                    }
                });
                if(folders != null && folders.length > 0) {
                    for(File folder : folders) {
                        list.addAll(getFilesRecursive(FileUtil.toFileObject(folder), filter));
                    }
                }
                File[] files = f.listFiles(filter);
                for(File file : files) {
                    list.add(FileUtil.toFileObject(file));
                }
            } else {
                if(filter.accept(f.getParentFile(), f.getName()))
                    list.add(FileUtil.toFileObject(f));
            }
        }
        return list;
    }

    public static TokenHierarchy<CharSequence> createTmplTokenHierarchy(CharSequence inputText, Snapshot tmplSnapshot) {
        InputAttributes inputAttributes = new InputAttributes();

        FileObject fo = tmplSnapshot.getSource().getFileObject();
        if (fo != null) {
            //try to obtain tmpl coloring info for file based snapshots
            final Document doc = tmplSnapshot.getSource().getDocument(true);

            LatteParseData tmplParseData = new LatteParseData(doc);
            inputAttributes.setValue(LatteTokenId.language(), LatteParseData.class, tmplParseData, true);

        }

        TokenHierarchy<CharSequence> th = TokenHierarchy.create(
                inputText,
                true,
                LatteTokenId.language(),
                new HashSet<LatteTokenId>(),
                inputAttributes);

        return th;
    }

	public static String capitalize(String s) {
        if (s.length() == 0) {
			return s;
		}

        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }
}
