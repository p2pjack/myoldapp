package com.hacker.eaun.cigmanotes.adapters;

import android.app.Activity;
import android.app.Dialog;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;

import com.hacker.eaun.cigmanotes.R;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;

/**
 * Created by Eaun-Ballinger on 09/09/2016.
 *
 *
 */


public class FileChooser {
    private static final String PARENT_DIR = "..";

    private final Activity activity;
    private ListView list;
    private Dialog dialog;
    private File currentPath;
    private String[] fileList;

    // filter on file extension
    private String extension = null;
    public void setExtension(String extension) {
        this.extension = (extension == null) ? null :
                extension.toLowerCase();
    }

    // file selection event handling
    public interface FileSelectedListener {
        void fileSelected(File file);
    }
    public FileChooser setFileListener(FileSelectedListener fileListener) {
        this.fileListener = fileListener;
        return this;
    }
    private FileSelectedListener fileListener;

    @SuppressWarnings("ConstantConditions")
    public FileChooser(Activity activity) {
        this.activity = activity;
        dialog = new Dialog(activity);
        list = new ListView(activity);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override public void onItemClick(AdapterView<?> parent, View view, int which, long id) {
                String fileChosen = (String) list.getItemAtPosition(which);
                File chosenFile = getChosenFile(fileChosen);
                if (chosenFile.isDirectory()) {
                    refresh(chosenFile);
                } else {
                    if (fileListener != null) {
                        fileListener.fileSelected(chosenFile);
                    }
                    dialog.dismiss();
                }
            }
        });
        dialog.setContentView(list);
        dialog.getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        refresh(Environment.getExternalStorageDirectory());
    }

    public void showDialog() {
        dialog.show();
    }
    /**
     * Sort, filter and display the files for the given path.
     */
    private void refresh(File path) {
        this.currentPath = path;
        if (path.exists()) {
            File[] dirs = path.listFiles(new FileFilter() {
                @Override public boolean accept(File file) {
                    return (file.isDirectory() && file.canRead());
                }
            });
            File[] files = path.listFiles(new FileFilter() {
                @Override public boolean accept(File file) {
                    return !file.isDirectory() && file.canRead() &&
                            (extension == null || file.getName().toLowerCase().endsWith(extension));
                }
            });
            try {
                // convert to an array
                int i = 0;
                if (path.getParentFile() == null) {
                    fileList = new String[dirs.length + files.length];
                } else {
                    fileList = new String[dirs.length + files.length + 1];
                    fileList[i++] = PARENT_DIR;
                }
                Arrays.sort(dirs);
                Arrays.sort(files);
                for (File dir : dirs) { fileList[i++] = dir.getName(); }
                for (File file : files ) { fileList[i++] = file.getName(); }
            }catch (NullPointerException e){

                e.printStackTrace();
            }
            // refresh the user interface
                dialog.setTitle(currentPath.getPath());
                list.setAdapter(new ArrayAdapter<String>(activity,
                     R.layout.simple_list1, fileList) {
                    @NonNull
                    @Override public View getView(int pos, View view, @NonNull ViewGroup parent) {
                        view = super.getView(pos, view, parent);
                        ((TextView) view).setSingleLine(true);
                        return view;
                    }
                });
        }
    }
    /**
     * Convert a relative filename into an actual File object.
     */
    private File getChosenFile(String fileChosen) {
        if (fileChosen.equals(PARENT_DIR)) {
            return currentPath.getParentFile();
        } else {
            return new File(currentPath, fileChosen);
        }
    }
}
