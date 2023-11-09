package net.yoedtos.wakeonfx.control;

import static net.yoedtos.wakeonfx.control.View.Text.APP_NAME;
import static net.yoedtos.wakeonfx.control.View.Text.DROP_MSG;
import static net.yoedtos.wakeonfx.util.Constants.HOSTS_FILE;
import static net.yoedtos.wakeonfx.util.TestConstants.*;
import static net.yoedtos.wakeonfx.util.TestDataSet.createTxtDataOne;
import static org.assertj.core.api.Assertions.assertThat;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.api.FxToolkit.*;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.util.NodeQueryUtils.hasText;

import javafx.scene.control.Button;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import org.junit.jupiter.api.*;
import org.testfx.util.WaitForAsyncUtils;

import java.io.IOException;
import java.nio.file.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MainControlIT extends UIBaseTest {

    private static Path hostsFile;

    @BeforeAll
    static void create() throws IOException {
        var testJson = MainControlIT.class.getClassLoader().getResource(HOSTS_FILE).getFile();
        Path testDir = FileSystems.getDefault().getPath(System.getProperty("user.dir"));
        hostsFile = testDir.resolve(HOSTS_FILE);
        Files.createFile(hostsFile);
        Files.copy(Paths.get(testJson), hostsFile, StandardCopyOption.REPLACE_EXISTING);
    }

    @AfterAll
    static void destroy() throws IOException {
        Files.delete(hostsFile);
    }

    @BeforeEach
    public void startApp() throws Exception {
        registerPrimaryStage();
        setupApplication(MainGUI::new);
        showStage();
    }

    @AfterEach
    public void stopApp() throws TimeoutException {
        cleanupStages();
    }

    @Override
    public void start(Stage primaryStage){
        primaryStage.toFront();
    }

    @Test
    @Order(1)
    void whenDisplayMainGuiShouldHaveEditAndDropButtonDisabled() {
        assertButtonsDisabled();
    }

    @Test
    @Order(2)
    void whenClickDropButtonAndWaitFiveSecondsShouldDisableButtons() {
        clickOn(SECURE_HOST);
        WaitForAsyncUtils.sleep(5, TimeUnit.SECONDS);
        assertButtonsDisabled();
    }

    @Test
    @Order(3)
    void whenClickAddButtonShouldShowHostWindowAndCancel() {
        clickOn("#btnAdd");
        verifyThat("Host", isVisible());
        clickOn("#btnCancel");
    }

    @Test
    @Order(4)
    void whenClickEditButtonShouldShowHostWindowAndCancel() {
        clickOn(SECURE_HOST);
        clickOn("#btnEdit");
        assertThat(getNode("#txtName", LimitedTextField.class).isDisabled()).isTrue();
        verifyThat("Host", isVisible());
        clickOn("#btnCancel");
    }

    @Test
    @Order(5)
    void whenClickDropButtonShouldShowAlertAndCancel() {
        clickOn(SECURE_HOST);
        clickOn("#btnDrop");
        verifyThat(DROP_MSG + SECURE_HOST + "?", isVisible());
        clickOn("Cancel");
    }

    @Test
    @Order(6)
    void whenClickInfoButtonShouldShowAboutDialogThenClose() {
        clickOn("#btnAbout");
        verifyThat(APP_NAME, isVisible());
        clickOn(".button");
    }

    @Test
    @Order(7)
    void whenStarMainGuiShouldHaveOneHost() {
        var tpHosts = (TilePane)getNode("#tpHosts", TilePane.class);
        assertThat(tpHosts.getChildren()).hasSize(1);
        verifyThat(SECURE_HOST, isVisible());
    }

    @Test
    @Order(8)
    void whenClickAddButtonAndOkShouldHaveNewHost() {
        var txtDataOne = createTxtDataOne();
        clickOn("#btnAdd");
        for (int i = 0; i < txtComIds.length; i++) {
            clickOn(txtComIds[i]).write(txtDataOne[i]);
        }

        clickOn("#btnSave");
        var tpHosts = (TilePane)getNode("#tpHosts", TilePane.class);
        assertThat(tpHosts.getChildren()).hasSize(2);
    }

    @Test
    @Order(9)
    void whenClickEditButtonAndOkShouldHaveEditedHost() {
        clickOn(SIMPLE_HOST);
        clickOn("#btnEdit");
        var portField = (LimitedTextField)getNode("#txtPort", LimitedTextField.class);
        portField.clear();
        clickOn(portField).write(String.valueOf(PORT_NUM_MOD));
        clickOn("#btnSave");
        clickOn(SIMPLE_HOST);
        clickOn("#btnEdit");
        verifyThat(portField.getText(), hasText(String.valueOf(PORT_NUM_MOD)));
    }

    @Test
    @Order(10)
    void whenClickDropButtonAndOkShouldHaveOneHost() {
        clickOn(SECURE_HOST);
        clickOn("#btnDrop");
        clickOn("OK");
        var tpHosts = (TilePane)getNode("#tpHosts", TilePane.class);
        assertThat(tpHosts.getChildren()).hasSize(1);
        verifyThat(SIMPLE_HOST, isVisible());
    }

    private void assertButtonsDisabled() {
        assertThat(getNode("#btnEdit", Button.class).isDisabled()).isTrue();
        assertThat(getNode("#btnDrop", Button.class).isDisabled()).isTrue();
    }
}
