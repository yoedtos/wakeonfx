package net.yoedtos.wakeonfx.control;

import javafx.stage.Stage;

public interface Control {
	void onStageDefined(Stage stage);
	void onObjectDefined(Object object) throws Exception;
}
