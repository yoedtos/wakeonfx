package net.yoedtos.wakeonfx.control;

class View {
    protected static final String HOST_DATA = "host_data.fxml";
    protected static final String ABOUT_DLG = "about_dlg.fxml";
    protected static final String MAIN_VIEW = "main_view.fxml";

    class Error {
        public static final String SAVE = "Failed to save!";
        public static final String WAKE = "Failed to wake!";
        public static final String DROP = "Failed to drop!";
        public static final String LOAD_RSC = "Failed to load resource";
    }

    class Icons {
        public static final String HOST = "icon/host.png";
        public static final String APP = "icon/wake_on.png";
    }

    class Text {
        public static final String APP_NAME = "WakeOn FX!";
        public static final String ADD_HOST = "Add Host";
        public static final String EDIT_HOST = "Edit Host";
        public static final String DROP_HOST = "Drop host";
        public static final String DROP_MSG = "Are you sure to remove ";
    }
}
