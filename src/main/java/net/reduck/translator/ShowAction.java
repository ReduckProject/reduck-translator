package net.reduck.translator;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import org.jetbrains.annotations.NotNull;

/**
 * @author Gin
 * @since 2023/8/8 18:05
 */
public class ShowAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Editor editor = getEditor(e);
        if (editor != null) {
            String text = editor.getSelectionModel().getSelectedText();

            if(text != null) {
                System.out.println(TranslateDemo.translate(text));
            }
        }
    }

    private Editor getEditor(AnActionEvent e) {
        return CommonDataKeys.EDITOR.getData(e.getDataContext());
    }
}
