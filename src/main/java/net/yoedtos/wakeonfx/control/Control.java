package net.yoedtos.wakeonfx.control;

import javafx.stage.Stage;
import net.yoedtos.wakeonfx.exceptions.ServiceException;

public interface Control {
	void onStageDefined(Stage stage);
	void onObjectDefined(Object object) throws Exception;
}
