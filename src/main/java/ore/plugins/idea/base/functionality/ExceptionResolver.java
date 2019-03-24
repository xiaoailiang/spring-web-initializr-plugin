package ore.plugins.idea.base.functionality;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;
import ore.plugins.idea.exception.CancelException;
import ore.plugins.idea.exception.validation.InvalidFileException;
import ore.plugins.idea.exception.validation.ValidationException;
import org.slf4j.Logger;

public interface ExceptionResolver extends MessageRenderer {

    default void safeExecute(Runnable runnable, AnActionEvent anActionEvent, Logger logger) {
        try {
            runnable.run();
        } catch (InvalidFileException invalidFileException) {
            anActionEvent.getPresentation().setEnabled(false);
        } catch (ValidationException validationException) {
            Messages.showMessageDialog(validationException.getPsiClass().getProject(), validationException.getMessage(), "Error", Messages.getErrorIcon());
        } catch (CancelException ignored) {
        } catch (Exception exception) {
            logger.error(exception.getMessage());
        }
    }

}